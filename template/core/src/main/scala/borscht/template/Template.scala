package borscht.template

import borscht.RenderableString

trait Template extends RenderableString :
  def set(key: String, value: Any): Template

  def set(parameters: IterableOnce[(String, Any)]): Template

  def set(parameters: (String, Any)*): Template = set(parameters)

  def apply(parameters: IterableOnce[(String, Any)]): String = set(parameters)()

  def apply(parameters: (String, Any)*): String = apply(parameters)
