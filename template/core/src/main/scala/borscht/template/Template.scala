package borscht.template

trait Template:
  def set(key: String, value: AnyRef): Template
  
  def set(parameters: IterableOnce[(String, AnyRef)]): Template

  def render: String

  def render(parameters: IterableOnce[(String, AnyRef)]): String = set(parameters).render
