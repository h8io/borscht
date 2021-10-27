package borscht.typed.types

import borscht.{CfgNode, NodeParser, NodeParserException, SeqNode}
import borscht.typed.{AbstractRefType, Ref, RefObj}
import borscht.virtual.VirtualScalarNode

import scala.reflect.ClassTag

object RefTypeList extends RefTypeWithOptionalParameter:
  override protected def create(parser: NodeParser[Ref[?]]): NodeParser[Ref[List[?]]] = node => node match
    case seq: SeqNode => RefObj((seq.iterator map { item => parser(item).value }).toList)
    case _ => RefObj(List(parser(node).value))

object RefTypeMap extends AbstractRefType[(NodeParser[Ref[?]], NodeParser[Ref[?]])]:
  override protected def prepare(parameters: List[NodeParser[Ref[?]]]): Either[String, (NodeParser[Ref[?]], NodeParser[Ref[?]])] =
    parameters match
      case Nil => Right(RefTypeAny -> RefTypeAny)
      case List(keyParser, valueParser) => Right(keyParser -> valueParser)
      case unexpected => Left(s"two or none parameters expected, but ${parameters.length} received")

  override protected def create(parsers: (NodeParser[Ref[?]], NodeParser[Ref[?]])): NodeParser[Ref[Map[?, ?]]] =
    val (keyParser, valueParser) = parsers
    _ match
      case cfg: CfgNode => Ref((cfg.iterator map { (key, value) =>
        keyParser(new VirtualScalarNode(key, cfg)).value -> valueParser(value).value
      }).toMap)
      case node => throw NodeParserException(s"cfg node expected, but ${node.`type`} received", node.position)

object RefTypeOption extends RefTypeWithOptionalParameter:
  override protected def create(parameter: NodeParser[Ref[?]]): NodeParser[Ref[Option[?]]] = node => node match
    case seq: SeqNode => RefObj(seq.option(using parameter) map (_.value))
    case _ => RefObj(Some(parameter(node).value))