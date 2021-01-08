package borscht.template.impl.apache.commons.text

import org.apache.commons.lang3.LocaleUtils

import java.util.Locale

case class Placeholder(key: String,
                       format: Option[String] = None,
                       nullValue: String = "null",
                       seqStart: String = "[",
                       seqSeparator: String = ", ",
                       seqEnd: String = "]",
                       productStart: String = "(",
                       productSeparator: String = ", ",
                       productEnd: String = ")",
                       locale: Locale = Locale.getDefault(Locale.Category.FORMAT)):
  def update(name: String, value: String): Placeholder = name match
    case "format" => copy(format = Some(value))
    case "null" => copy(nullValue = value)
    case "separator" => copy(seqSeparator = value, productSeparator = value)
    case "seq.start" => copy(seqStart = value)
    case "seq.separator" => copy(seqSeparator = value)
    case "seq.end" => copy(seqEnd = value)
    case "product.start" => copy(productStart = value)
    case "product.separator" => copy(productSeparator = value)
    case "product.end" => copy(productEnd = value)
    case "locale" => copy(locale = LocaleUtils.toLocale(value))
    case _ => throw new IllegalArgumentException(s"Illegal placeholder attribute $name")
