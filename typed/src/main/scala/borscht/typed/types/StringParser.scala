package borscht.typed.types

trait StringParser[T]:
  def parse(value: String): T
