package borscht.typed.types

import borscht.{CfgNode, NodeParserException, SeqNode}
import borscht.typed.{AbstractValueType, ValueParser}
import borscht.virtual.VirtualScalarNode

object ValueTypeList extends ValueTypeWithOptionalParameter:
  override protected def create(parser: ValueParser): ValueParser = node => node match
    case seq: SeqNode => (seq.iterator map parser).toList
    case _ => List(parser(node))

object ValueTypeMap extends AbstractValueType[(ValueParser, ValueParser)]:
  override protected def prepare(parameters: List[ValueParser]): Either[String, (ValueParser, ValueParser)] =
    parameters match
      case Nil => Right(ValueTypeAny -> ValueTypeAny)
      case List(keyParser, valueParser) => Right(keyParser -> valueParser)
      case unexpected => Left(s"two or none parameters expected, but ${parameters.length} received")

  override protected def create(parsers: (ValueParser, ValueParser)): ValueParser =
    val (keyParser, valueParser) = parsers
    _ match
      case cfg: CfgNode => (cfg.iterator map { (key, value) =>
        keyParser(new VirtualScalarNode(key, cfg)) -> valueParser(value)
      }).toMap
      case node => throw NodeParserException(s"cfg node expected, but ${node.`type`} received", node.position)