package borscht.template

import borscht._
import borscht.parsers.given

type ParameterParser[T] = String => T

def createNodeParserTemplate(defaultParser: TemplateParser,
                             parameterParser: TemplateParameterParser = TemplateParameterParser())
                            (using nodeParserTemplateParser: NodeParser[TemplateParser]): NodeParser[Template] =
  def toMap(cfg: CfgNode): Map[String, AnyRef] = (cfg.iterator map (_ -> parameter(_))).toMap

  def parameter(node: Node): AnyRef = node match
    case scalar: ScalarNode => scalar.unwrapped match
      case value: String => parameterParser.lift(value) getOrElse value
      case value => value
    case iterable: SeqNode => (iterable.iterator map parameter).toArray
    case cfg: CfgNode => toMap(cfg)

  NodeParserPlainString andThen defaultParser orElse
    (NodeParserCfgNode andThen { cfg =>
      val parser = cfg.get[TemplateParser]("parser") getOrElse defaultParser
      parser(NodeParserPlainString(cfg[ScalarNode]("template"))).set(toMap(cfg[CfgNode]("parameters")))
    })
