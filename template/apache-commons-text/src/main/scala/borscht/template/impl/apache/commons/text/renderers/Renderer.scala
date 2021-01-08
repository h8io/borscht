package borscht.template.impl.apache.commons.text.renderers

import java.util.Locale

@FunctionalInterface
trait Renderer:
  def apply(value: AnyRef, format: Option[String], locale: Locale): Option[String]

  final def orElse(that: Renderer): Renderer = (value: AnyRef, format: Option[String], locale: Locale) =>
    this(value, format, locale) orElse that(value, format, locale) 

object Renderer:
  val empty: Renderer = (_, _, _) => None
