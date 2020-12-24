package borscht.template

import borscht._
import borscht.parsers.given

type ParameterParser[T] = String => T

def NodeParserTemplate(registry: TemplateParserRegistry,
                       parameterParser: TemplateParameterParser = TemplateParameterParser()): NodeParser[Template] =
  def toMap(cfg: CfgNode): Map[String, AnyRef] = (cfg.iterator map (_ -> parameter(_))).toMap

  def parameter(node: Node): AnyRef = node match
    case scalar: ScalarNode => scalar.unwrapped match
      case value: String => parameterParser.lift(value) getOrElse value
      case value => value
    case iterable: SeqNode => (iterable.iterator map parameter).toArray
    case cfg: CfgNode => toMap(cfg)

  NodeParserPlainString andThen registry(None) orElse
    (NodeParserCfgNode andThen new PartialFunction[CfgNode, Template] {
      override def isDefinedAt(cfg: CfgNode): Boolean = cfg.child("parameters") map {
        case _: CfgNode => true
        case _ => false
      } getOrElse true

      override def apply(cfg: CfgNode): Template =
        registry(cfg.get[String]("parser"))(NodeParserPlainString(cfg[ScalarNode]("template")))
          .set(toMap(cfg[CfgNode]("parameters")))
    })
