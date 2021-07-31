package borscht.typed.events

import borscht.typed.{ValueParser, ValueTypeConstructor}

private[typed] final class RootParser(types: PartialFunction[String, ValueTypeConstructor])
  extends UpdatableParser[ValueParser] :
  
  private var optResult: Option[ValueParser] = None

  override def apply(event: Event): Parser = event match
    case event: Event.TypeName => TypeParser(this, types)(event)
    case _: Event.None => this
    case event: Event.End => this
    case unexpected => throw UnexpectedEvent(unexpected)

  override def update(value: ValueParser): Unit = optResult = Some(value)

  def result = optResult getOrElse {
    throw IllegalStateException("No result (should not happen)")
  }