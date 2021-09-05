package borscht.typed

import java.lang.{Boolean as jBoolean, Byte as jByte, Character as jChar, Double as jDouble, Float as jFloat, Integer as jInt, Long as jLong, Short as jShort}
import scala.reflect.ClassTag

private def lift[T, A](f: T => A)(using t: ClassTag[T], a: ClassTag[A]): (Class[T], (Class[A], T => A)) =
  (t.runtimeClass.asInstanceOf[Class[T]], (a.runtimeClass.asInstanceOf[Class[A]], f))

private[typed] val BoxMap: Map[Class[?], (Class[?], ? => ?)] = Map(
  lift(jBoolean.valueOf(_: Boolean)),
  lift((_: jBoolean).booleanValue),

  lift(jByte.valueOf(_: Byte)),
  lift((_: jByte).byteValue),

  lift(jChar.valueOf(_: Char)),
  lift((_: jChar).charValue),

  lift(jDouble.valueOf(_: Double)),
  lift((_: jDouble).doubleValue),

  lift(jFloat.valueOf(_: Float)),
  lift((_: jFloat).floatValue),

  lift(jInt.valueOf(_: Int)),
  lift((_: jInt).intValue),

  lift(jLong.valueOf(_: Long)),
  lift((_: jLong).longValue),

  lift(jShort.valueOf(_: Short)),
  lift((_: jShort).shortValue))
