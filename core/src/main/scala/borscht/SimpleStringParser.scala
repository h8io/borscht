package borscht

object SimpleStringParser extends Parser[String] :
  override def apply(node: ScalarNode): String = node.asString
