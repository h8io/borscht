package borscht.template.impl.apache.commons.text.renderers

import borscht.template.impl.apache.commons.text.Placeholder

import java.util.Locale

@FunctionalInterface
trait Renderer:
  def apply(ph: Placeholder, value: AnyRef): Option[String]

  final def orElse(that: Renderer): Renderer = (ph: Placeholder, value: AnyRef) =>
    this(ph, value) orElse that(ph, value) 

object Renderer:
  val empty: Renderer = (_, _) => None

  val fail: Renderer = (_, _) => throw IllegalStateException()