package borscht.template

import borscht.{ObjectNode, Parser, ScalarNode}

trait TemplateParser extends Parser[Template]:
  override def apply(node: ObjectNode): Template = ???
    // this(node.get[ScalarNode]("template").asString).set(node.map[AnyRef]("parameters"))
