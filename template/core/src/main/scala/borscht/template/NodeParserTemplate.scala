package borscht.template

import borscht._
import borscht.parsers.given

type TemplateParser = String => Template
type ParameterParser[T] = String => T

def NodeParserTemplate(templateParser: TemplateParser,
                       parameterParser: TemplateParameterValueParser): NodeParser[Template] =
  def toMap(cfg: ConfigNode): Map[String, AnyRef] = (cfg.iterator map (_ -> parameter(_))).toMap

  def parameter(node: Node): AnyRef = node match
    case scalar: ScalarNode => scalar.unwrapped match
      case value: String => parameterParser.lift(value) getOrElse value
      case value => value
    case iterable: IterableNode => (iterable.iterator map parameter).toArray
    case cfg: ConfigNode => toMap(cfg)

  NodeParserScalarString andThen templateParser orElse
    (NodeParserConfigNode andThen { cfg =>
      templateParser(NodeParserScalarString(cfg[ScalarNode]("template")))
        .set(toMap(cfg[ConfigNode]("parameters")))
    })
