package borscht.typed.types

import borscht.parsers.NodeParserString
import borscht.typed.{AbstractValueType, ValueParser}

trait ValueTypeStringMapper extends AbstractValueType[String => _] with StringParser[String]:
  override protected def prepare(parameters: List[ValueParser]): Either[String, String => ?] = (parameters match
    case Nil => ValueParserString
    case first :: Nil => Right(first)
    case _ => Left("an optional parameter expected")) flatMap {
      case sp: StringParser[?] => Right(sp.parse)
      case _ => Left("string parser expected")
    }

  override protected def parser(parser: String => _): ValueParser = node => parser(parse(node.parse[String]))
