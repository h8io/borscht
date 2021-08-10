package borscht.template

import borscht.RenderableString

trait Template extends RenderableString :
  def set(key: String, value: AnyRef): Template

  def set(parameters: IterableOnce[(String, AnyRef)]): Template

  def set(parameters: (String, AnyRef)*): Template = set(parameters)

  override def apply(): String

  def apply(parameters: IterableOnce[(String, AnyRef)]): String = set(parameters)()

  def apply(parameters: (String, AnyRef)*): String = apply(parameters)
