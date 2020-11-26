package borscht.template

import borscht._
import borscht.parsers.given

type TemplateParser = String => Template
type ParameterParser[T] = String => T

def NodeParserTemplate(templateParser: TemplateParser,
                       parameterParser: TemplateParameterValueParser): NodeParser[Template] =
  def toMap(node: ObjectNode): Map[String, AnyRef] = (node.iterator map (_ -> parameter(_))).toMap

  def parameter(node: Node): AnyRef = node match
    case scalar: ScalarNode => scalar.unwrapped match
      case value: String => parameterParser.lift(value) getOrElse value
      case value => value
    case iterable: IterableNode => (iterable.iterator map parameter).toArray
    case obj: ObjectNode => toMap(obj)

  NodeParserScalarString andThen templateParser orElse
    (NodeParserObjectNode andThen { node =>
      templateParser(NodeParserScalarString(node.get[ScalarNode]("template")))
        .set(toMap(node.get[ObjectNode]("parameters")))
    })
