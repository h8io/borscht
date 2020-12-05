package io.h8.cfg.template

import io.h8.cfg.parsers.given
import io.h8.cfg._

type TemplateParser = String => Template
type ParameterParser[T] = String => T

def NodeParserTemplate(templateParser: TemplateParser,
                       parameterParser: TemplateParameterValueParser): NodeParser[Template] =
  def toMap(cfg: CfgNode): Map[String, AnyRef] = (cfg.iterator map (_ -> parameter(_))).toMap

  def parameter(node: Node): AnyRef = node match
    case scalar: ScalarNode => scalar.unwrapped match
      case value: String => parameterParser.lift(value) getOrElse value
      case value => value
    case iterable: IterableNode => (iterable.iterator map parameter).toArray
    case cfg: CfgNode => toMap(cfg)

  NodeParserPlainString andThen templateParser orElse
    (NodeParserConfigNode andThen { cfg =>
      templateParser(NodeParserPlainString(cfg[ScalarNode]("template")))
        .set(toMap(cfg[CfgNode]("parameters")))
    })
