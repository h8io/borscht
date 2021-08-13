package borscht.template

import borscht.RenderableString

trait Template extends RenderableString:
  def set(key: String, value: Any): Template

  // Could be overriden for a performance reason
  def set(parameters: IterableOnce[(String, Any)]): Template = (parameters.iterator foldLeft this) { (t, p) =>
    t.set(p._1, p._2)
  }

  final def set(parameters: (String, Any)*): Template = set(parameters)

  final def apply(parameters: IterableOnce[(String, Any)]): String = set(parameters)()

  final def apply(parameters: (String, Any)*): String = apply(parameters)
