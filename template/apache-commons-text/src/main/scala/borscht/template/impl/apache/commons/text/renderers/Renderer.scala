package borscht.template.impl.apache.commons.text.renderers

import borscht.template.impl.apache.commons.text.ValueFormat

import java.util.Locale

@FunctionalInterface
trait Renderer:
  def apply(vf: ValueFormat, value: AnyRef): Option[String]

  final def orElse(that: Renderer): Renderer = (vf: ValueFormat, value: AnyRef) =>
    this(vf, value) orElse that(vf, value) 

object Renderer:
  val empty: Renderer = (_, _) => None

  val fail: Renderer = (_, _) => throw IllegalStateException()