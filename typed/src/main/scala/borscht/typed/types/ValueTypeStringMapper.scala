package borscht.typed.types

import borscht.Node
import borscht.parsers.NodeParserString
import borscht.typed.{AbstractValueType, ValueParser}

private val DefaultStringParser = new ValueTypeString

trait ValueTypeStringMapper extends AbstractValueType[String => ?] with StringParser[String]:
  self =>

  override protected def prepare(parameters: List[ValueParser]): Either[String, String => ?] = parameters match
    case Nil => Right(DefaultStringParser.parse)
    case first :: Nil => first match
      case sp: StringParser[?] => Right(sp.parse)
      case _ => Left("string parser expected")
    case _ => Left("an optional parameter expected")

  override protected def create(sp: String => ?): ValueParser = new ValueParser with StringParser[Any]:
    override def parse(value: String): Any = sp(self.parse(value))

    override def apply(node: Node): Any = parse(node.parse[String])
