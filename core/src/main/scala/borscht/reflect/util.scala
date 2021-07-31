package borscht.reflect

import borscht.*
import borscht.parsers.given

import java.lang.reflect.Constructor
import scala.reflect.ClassTag

def cast[T](`class`: Class[?])(using tag: ClassTag[T]): Class[? <: T] =
  `class`.asSubclass(tag.runtimeClass.asInstanceOf[Class[T]])

def creator[T](`class`: Class[? <: T], optParameters: Option[Node]): () => T = optParameters match
  case None => creator(`class`)
  case Some(parameters) => parameters match
    case named: CfgNode => creator(`class`, named)
    case unnamed: SeqNode => creator(`class`, unnamed)
    case unnamed: ScalarNode => creator(`class`, Iterable[Node](unnamed))

def creator[T](`class`: Class[? <: T]): () => T =
  val constructor = `class`.getConstructor()
  () => constructor.newInstance()

def creator[T](`class`: Class[? <: T], named: CfgNode): () => T =
  val parameters = (named.iterator map { (key, node) => key -> NodeParserTypedValue(node).value }).toMap
  val types = parameters map (_ -> _.getClass)
  getConstructors(`class`, types.size) find { constructor =>
    constructor.getParameters.iterator forall { parameter =>
      types.get(parameter.getName) exists (parameter.getType isAssignableFrom _)
    }
  } map { constructor =>
    () => constructor.newInstance(constructor.getParameters map { p => parameters(p.getName) }: _*)
  } getOrElse {
    throw NoSuchMethodException(s"A suitable constructor with parameters (${types.mkString(", ")}) not found")
  }

def creator[T](`class`: Class[? <: T], unnamed: Iterable[Node]): () => T =
  val parameters = (unnamed.iterator map { node => NodeParserTypedValue(node).value }).toArray
  val types = parameters map (_.getClass)
  getConstructors(`class`, types.size) find { constructor =>
    constructor.getParameterTypes.iterator zip types forall (_ isAssignableFrom _)
  } map (constructor => () => constructor.newInstance(parameters: _*)) getOrElse {
    throw NoSuchMethodException(s"A suitable constructor with parameter types (${types.mkString(", ")}) not found")
  }

private def getConstructors[T](`class`: Class[? <: T], n: Int): Iterator[Constructor[T]] =
  `class`.getConstructors.iterator filter (_.getParameterCount == n) map (_.asInstanceOf[Constructor[T]])