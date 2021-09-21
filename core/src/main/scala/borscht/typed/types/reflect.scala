package borscht.typed.types

import borscht.*
import borscht.parsers.{NodeParserClass, NodeParserRef, NodeParserString}
import borscht.typed.{Ref, RefComponent, RefObj}

import java.lang.reflect.{Constructor, Executable, Method}
import scala.reflect.ClassTag

object RefTypeComponent extends RefTypeParameterless:
  private val ComponentTypes = Map("class" -> cls, "object" -> obj)

  override def apply(node: Node): RefComponent[?] = node match
    case scalar: ScalarNode => cls(scalar)(None)
    case cfg: CfgNode => cfg.oneOf(ComponentTypes)(cfg.child("parameters"))
    case _ => throw WrongNodeTypeException(node)

  private def cls(classNode: Node): Option[Node] => RefComponent[?] = parametersNode =>
    val `class` = classNode.as[Class[?]].asSubclass(classOf[AnyRef])
    val constructor = parametersNode map {
      case named: CfgNode => executable(constructors(`class`, named.size), named)
      case unnamed: SeqNode => executable(constructors(`class`, unnamed.size), unnamed)
      case unnamed: ScalarNode => executable(constructors(`class`, 1), List(unnamed))
    } map {
      case Right((constructor, parameters)) =>
        () => constructor.newInstance(parameters: _*)
      case Left(expected) => throw NoSuchMethodException(
        s"A suitable constructor with parameters ($expected) not found for ${`class`.getName}")
    } getOrElse (() => `class`.getConstructor().newInstance())
    RefComponent(constructor())(using ClassTag(`class`))

  private def obj(node: Node): Option[Node] => RefComponent[?] = _ =>
    val className = node.as[String]
    try {
      val objectClass = Class.forName(className + "$")
      RefComponent(objectClass.getField("MODULE$").get(objectClass))(using ClassTag(objectClass))
    } catch {
      case e: ClassNotFoundException =>
        throw NodeParserException(s"Object $className not found", node.position, e)
      case e: NoSuchFieldException =>
        throw NodeParserException(s"Class $className do not have a companion object", node.position, e)
    }

private def executable[T <: Executable](executables: Iterator[T], named: CfgNode): Either[String, (T, Seq[Any])] =
  val parameters = (named.iterator map { (name, node) => name -> node.as[Ref[AnyRef]] }).toMap
  executables find (_.getParameters.iterator forall { parameter =>
    parameters.get(parameter.getName) exists (_ isCastableTo parameter.getType)
  }) map { executable =>
    (executable, (executable.getParameters.iterator map { p => parameters(p.getName).value }).toSeq)
  } toRight {
    parameters.iterator.map(_ -> _.classTag).mkString(", ")
  }

private def executable[T <: Executable](executables: Iterator[T],
                                        unnamed: Iterable[Node]): Either[String, (T, Seq[Any])] =
  val parameters = (unnamed.iterator map (_.as[Ref[AnyRef]])).toList
  executables find { executable =>
    parameters.iterator zip executable.getParameterTypes forall (_ isCastableTo _)
  } map { executable =>
    (executable, parameters map (_.value))
  } toRight {
    parameters.iterator.map(_.classTag).mkString(", ")
  }

private def constructors[T](`class`: Class[? <: T], n: Int): Iterator[Constructor[T]] =
  `class`.getConstructors.iterator filter (_.getParameterCount == n) map (_.asInstanceOf[Constructor[T]])

private def getMethods[T](`class`: Class[? <: T], name: String, n: Int): Iterator[Method] =
  `class`.getMethods.iterator filter { m => m.getName == name && m.getParameterCount == n }
