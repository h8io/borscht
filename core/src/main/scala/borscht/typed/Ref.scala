package borscht.typed

import scala.annotation.switch
import scala.reflect.ClassTag

sealed trait Ref[T]:
  def classTag: ClassTag[? <: T]

  def value: T

  def cast[R: ClassTag]: Ref[R]

  def map[R](f: T => R)(using tag: ClassTag[R]): Ref[R] = Ref(f(value))

  def isCastableTo(`class`: Class[?]): Boolean

  override def equals(obj: Any): Boolean = obj match
    case that: Ref[?] => classTag == that.classTag && value == that.value
    case _ => false

  override def toString: String = s"${getClass.getName}(classTag = $classTag, value = $value (${value.getClass}))"


object Ref:
  def apply[T](value: => T)(using tag: ClassTag[T]): Ref[T] = tag match
    case ClassTag.Boolean => RefBoolean(value)
    case ClassTag.Byte => RefByte(value)
    case ClassTag.Char => RefChar(value)
    case ClassTag.Double => RefDouble(value)
    case ClassTag.Float => RefFloat(value)
    case ClassTag.Int => RefInt(value)
    case ClassTag.Long => RefLong(value)
    case ClassTag.Short => RefShort(value)
    case anyRefTag: ClassTag[? <: AnyRef] => RefObj(value)(using tag)


class RefAnyRef[T <: AnyRef](_value: => T)(using val classTag: ClassTag[T]) extends Ref[T] :
  lazy val value: T = classTag.unapply(_value) getOrElse (throw IllegalStateException(
    s"Value $_value has wrong type (${_value.getClass.getName} instead $classTag)"))

  def cast[R](using tag: ClassTag[R]): Ref[R] =
    val r = tag.runtimeClass
    val t = classTag.runtimeClass
    if (r.isAssignableFrom(t)) asInstanceOf[Ref[R]]
    else RefAnyRef.UnboxMap.get(t) collect {
      case (p, f) if r.isAssignableFrom(p) => Ref(f.asInstanceOf[T => R](value))
    } getOrElse {
      throw ClassCastException(s"${t.getName} could not be transformed to ${r.getName}")
    }

  override def isCastableTo(`class`: Class[?]): Boolean = `class`.isAssignableFrom(classTag.runtimeClass) ||
    RefAnyRef.UnboxMap.get(classTag.runtimeClass).exists(_._1 == `class`)


object RefAnyRef:
  private def lift[B <: AnyRef, P <: Primitive](f: B => P)
                                               (using b: ClassTag[B], p: ClassTag[P]): (Class[B], (Class[P], B => P)) =
    (b.runtimeClass.asInstanceOf[Class[B]], (p.runtimeClass.asInstanceOf[Class[P]], f))

  private val UnboxMap: Map[Class[? <: AnyRef], (Class[? <: Primitive], ? => ? <: Primitive)] = Map(
    lift((_: java.lang.Boolean).booleanValue),
    lift((_: java.lang.Byte).byteValue),
    lift((_: java.lang.Character).charValue),
    lift((_: java.lang.Double).doubleValue),
    lift((_: java.lang.Float).floatValue),
    lift((_: java.lang.Integer).intValue),
    lift((_: java.lang.Long).longValue),
    lift((_: java.lang.Short).shortValue))


final class RefObj[T <: AnyRef : ClassTag](value: => T) extends RefAnyRef(value)


final class RefComponent[T <: AnyRef : ClassTag](value: => T) extends RefAnyRef(value):
  def assign[R <: AnyRef](using tag: ClassTag[R]): RefComponent[R] =
    val r = tag.runtimeClass
    val t = classTag.runtimeClass
    if (r.isAssignableFrom(t)) asInstanceOf[RefComponent[R]]
    else throw ClassCastException(s"${r.getName} is not assignable from ${t.getName}")


sealed abstract class RefPrimitive[P <: Primitive](_value: => P)(using val classTag: ClassTag[P]) extends Ref[P] :
  override def value: P = _value

  protected def boxedClass: Class[? <: AnyRef]

  override def cast[R](using tag: ClassTag[R]): Ref[R] = tag match
    case ClassTag.Any | ClassTag.AnyVal | `classTag` => asInstanceOf[Ref[R]]
    case tag: ClassTag[? <: AnyRef] if (tag.runtimeClass.isAssignableFrom(boxedClass)) =>
      RefObj(value.asInstanceOf[AnyRef])(using ClassTag(boxedClass)).asInstanceOf[Ref[R]]
    case _ => throw ClassCastException(s"$tag is not assignable from $classTag")

  override def isCastableTo(`class`: Class[?]): Boolean =
    `class`.isAssignableFrom(classTag.runtimeClass) || `class`.isAssignableFrom(boxedClass)


final class RefBoolean(_value: => Boolean) extends RefPrimitive(_value) :
  override protected def boxedClass = classOf[java.lang.Boolean]


final class RefByte(_value: => Byte) extends RefPrimitive(_value) :
  override protected def boxedClass = classOf[java.lang.Byte]


final class RefChar(_value: => Char) extends RefPrimitive(_value) :
  override protected def boxedClass = classOf[java.lang.Character]


final class RefDouble(_value: => Double) extends RefPrimitive(_value) :
  override protected def boxedClass = classOf[java.lang.Double]


final class RefFloat(_value: => Float) extends RefPrimitive(_value) :
  override protected def boxedClass = classOf[java.lang.Float]


final class RefInt(_value: => Int) extends RefPrimitive(_value) :
  override protected def boxedClass = classOf[java.lang.Integer]


final class RefLong(_value: => Long) extends RefPrimitive(_value) :
  override protected def boxedClass = classOf[java.lang.Long]


final class RefShort(_value: => Short) extends RefPrimitive(_value) :
  override protected def boxedClass = classOf[java.lang.Short]
