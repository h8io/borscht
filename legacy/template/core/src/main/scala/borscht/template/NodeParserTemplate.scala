package borscht.template

import borscht._
import borscht.parsers.given
import borscht.typed.TypedValue

type ParameterParser[T] = String => T

def createNodeParserTemplate(defaultParser: TemplateParser)
                            (using nodeParserTemplateParser: NodeParser[TemplateParser],
                             nodeParserTypedValueParser: NodeParser[TypedValue]): NodeParser[Template] =
  def toMap(cfg: CfgNode): Map[String, AnyRef] = (cfg.iterator map (_ -> parameter(_))).toMap

  def parameter(node: Node): AnyRef = node match
    case scalar: ScalarNode => nodeParserTypedValueParser.lift(scalar) map (_.value) getOrElse scalar.unwrapped
    case iterable: SeqNode => (iterable.iterator map parameter).toArray
    case cfg: CfgNode => toMap(cfg)

  NodeParserPlainString andThen defaultParser orElse
    (NodeParserCfgNode andThen { cfg =>
      val parser = cfg.get[TemplateParser]("parser") getOrElse defaultParser
      parser(NodeParserPlainString(cfg[ScalarNode]("template"))).set(toMap(cfg[CfgNode]("parameters")))
    })
