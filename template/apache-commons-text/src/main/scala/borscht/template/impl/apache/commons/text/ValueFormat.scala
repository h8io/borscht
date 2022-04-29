package borscht.template.impl.apache.commons.text

import borscht.CfgNode
import org.apache.commons.lang3.LocaleUtils

import java.util.Locale

case class ValueFormat(
    format: Option[String] = None,
    nullValue: String = "null",
    list: SeqFormat = SeqFormat.list,
    product: SeqFormat = SeqFormat.product,
    locale: Locale = Locale.getDefault(Locale.Category.FORMAT)
):
  def update(name: String, value: String): ValueFormat = name match
    case "format"            => copy(format = Some(value))
    case "null"              => copy(nullValue = value)
    case "separator"         => copy(list = list.copy(separator = value), product = product.copy(separator = value))
    case "list.start"        => copy(list = list.copy(start = value))
    case "list.separator"    => copy(list = list.copy(separator = value))
    case "list.end"          => copy(list = list.copy(end = value))
    case "product.start"     => copy(product = product.copy(start = value))
    case "product.separator" => copy(product = product.copy(separator = value))
    case "product.end"       => copy(product = product.copy(end = value))
    case "locale"            => copy(locale = LocaleUtils.toLocale(value))
    case _                   => throw new IllegalArgumentException(s"Illegal placeholder attribute $name")

object ValueFormat:
  object default extends ValueFormat()

case class SeqFormat(start: String, separator: String, end: String)

object SeqFormat:
  object list extends SeqFormat("[", ", ", "]")
  object product extends SeqFormat("(", ", ", ")")
