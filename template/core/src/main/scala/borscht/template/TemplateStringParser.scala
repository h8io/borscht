package borscht.template

import borscht.{IterableNode, ObjectNode, Parser, ScalarNode}

final class TemplateStringParser(tp: TemplateParser) extends Parser[String]:
  override def apply(node: ScalarNode): String = node.asString

  override def apply(node: IterableNode): String = tp(node).render

  override def apply(node: ObjectNode): String = tp(node).render
