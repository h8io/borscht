package borscht.template.impl.apache.commons.text

import org.apache.commons.lang3.LocaleUtils

import java.util.Locale

case class Placeholder(key: String,
                       format: Option[String] = None,
                       separator: Option[String] = None,
                       nullValue: Option[String] = None,
                       locale: Option[Locale] = None):
  def update(name: String, value: String): Placeholder = name match
    case "format" => copy(format = Some(value))
    case "separator" => copy(separator = Some(value))
    case "null" => copy(nullValue = Some(value))
    case "locale" => copy(locale = Some(LocaleUtils.toLocale(value)))
    case _ => throw new IllegalArgumentException(s"Illegal placeholder attribute $name")
