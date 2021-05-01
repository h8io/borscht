package borscht.typed.parsers

import borscht.typed.{ValueType, ValueTypeConstructor}

private[typed] final class RootValueTypeParser(types: Map[String, ValueTypeConstructor])
  extends UpdatableParser[ValueType] :

  private var optResult: Option[ValueType] = None

  override def apply(event: Event): Parser = event match
    case event: Event.TypeName => ValueTypeParser(this, types)(event)
    case _: Event.None => this
    case event: Event.End => this
    case unexpected => throw UnexpectedEventException(unexpected)

  override def update(value: ValueType): Unit = optResult = Some(value)

  def result = optResult getOrElse {
    throw IllegalStateException("No result (should not happen)")
  }