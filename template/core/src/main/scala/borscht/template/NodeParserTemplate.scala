package borscht.template

import borscht._
import borscht.parsers.given

type TemplateParser = String => Template

def NodeParserTemplate(templateParser: TemplateParser): NodeParser[Template] =
  def toMap(node: ObjectNode): Map[String, AnyRef] = (node.iterator map (_ -> parameter(_))).toMap

  def parse(value: String): AnyRef = ???

  def parameter(node: Node): AnyRef = node match
    case scalar: ScalarNode => scalar.unwrapped match
      case value: String => parse(value)
      case value => value
    case iterable: IterableNode => (iterable.iterator map parameter).toArray
    case obj: ObjectNode => toMap(obj)

  NodeParserScalarString andThen templateParser orElse
    (NodeParserObjectNode andThen { node =>
      templateParser(NodeParserScalarString(node.get[ScalarNode]("template")))
        .set(toMap(node.get[ObjectNode]("parameters")))
    })
