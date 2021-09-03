package borscht.typedOld.types

import borscht.{CfgNode, NodeParser, NodeParserException, SeqNode}
import borscht.typedOld.AbstractValueType
import borscht.virtual.VirtualScalarNode

object ValueTypeList extends ValueTypeWithOptionalParameter:
  override protected def create(parser: NodeParser[?]): NodeParser[?] = node => node match
    case seq: SeqNode => (seq.iterator map parser).toList
    case _ => List(parser(node))

object ValueTypeMap extends AbstractValueType[(NodeParser[?], NodeParser[?])]:
  override protected def prepare(parameters: List[NodeParser[?]]): Either[String, (NodeParser[?], NodeParser[?])] =
    parameters match
      case Nil => Right(ValueTypeAny -> ValueTypeAny)
      case List(keyParser, valueParser) => Right(keyParser -> valueParser)
      case unexpected => Left(s"two or none parameters expected, but ${parameters.length} received")

  override protected def create(parsers: (NodeParser[?], NodeParser[?])): NodeParser[?] =
    val (keyParser, valueParser) = parsers
    _ match
      case cfg: CfgNode => (cfg.iterator map { (key, value) =>
        keyParser(new VirtualScalarNode(key, cfg)) -> valueParser(value)
      }).toMap
      case node => throw NodeParserException(s"cfg node expected, but ${node.`type`} received", node.position)