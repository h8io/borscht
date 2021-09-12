package borscht.typed.types

import borscht.*
import borscht.parsers.{NodeParserClass, NodeParserRef}
import borscht.typed.Ref

import java.lang.reflect.Constructor
import scala.reflect.ClassTag

object RefTypeComponent extends RefTypeParameterless:
  override def apply(node: Node): Ref[?] =
    val (refClass, constructor) = node match
      case scalar: ScalarNode =>
        val `class` = NodeParserClass(scalar)
        (`class`, creator(`class`))
      case cfg: CfgNode =>
        val `class` = cfg[Class[?]]("class")
        (`class`, cfg.child("parameters") map {
          case named: CfgNode => creator(`class`, named)
          case unnamed: SeqNode => creator(`class`, unnamed.iterator)
          case unnamed: ScalarNode => creator(`class`, Iterator(unnamed))
        } getOrElse creator(`class`))
      case _ => throw NodeParserException(s"Wrong component node type ${node.`type`}", node.position)
    Ref(constructor())(using ClassTag(refClass))

  private def creator(`class`: Class[?]): () => ? = () => `class`.getConstructor().newInstance()

  private def creator(`class`: Class[?], named: CfgNode): () => ? =
    val parameters = (named.iterator map { (name, node) => name -> node.as[Ref[AnyRef]] }).toMap
    getConstructors(`class`, parameters.size) find { constructor =>
      constructor.getParameters.iterator forall { parameter =>
        parameters.get(parameter.getName) exists (_.isAssignableTo(parameter.getType))
      }
    } map { constructor =>
      val args = constructor.getParameters map { p => parameters(p.getName) }
      () => constructor.newInstance(args map (_.value): _*)
    } getOrElse {
      val expected = parameters.iterator.map(_ -> _.classTag).mkString(", ")
      throw NoSuchMethodException(
        s"A suitable constructor with parameters ($expected) not found for ${`class`.getName}")
    }

  private def creator(`class`: Class[?], unnamed: Iterator[Node]): () => ? =
    val parameters = (unnamed.iterator map (_.as[Ref[AnyRef]])).toList
    getConstructors(`class`, parameters.size) find { constructor =>
      parameters.iterator zip constructor.getParameterTypes forall (_ isAssignableTo _)
    } map { constructor => () => constructor.newInstance(parameters map (_.value): _*) } getOrElse {
      val expected = parameters.iterator.map(_.classTag).mkString(", ")
      throw NoSuchMethodException(
        s"A suitable constructor with parameter types ($expected) not found for ${`class`.getName}")
    }

  private def getConstructors[T](`class`: Class[? <: T], n: Int): Iterator[Constructor[T]] =
    `class`.getConstructors.iterator filter (_.getParameterCount == n) map (_.asInstanceOf[Constructor[T]])
