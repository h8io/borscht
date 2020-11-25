package borscht.template

trait Template:
  def set(key: String, value: AnyRef): Template
  
  def set(parameters: Iterable[(String, AnyRef)]): Template

  def render: String

  def render(parameters: Iterable[(String, AnyRef)]): String = set(parameters).render
