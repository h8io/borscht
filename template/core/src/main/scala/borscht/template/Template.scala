package borscht.template

trait Template:
  def set(key: String, value: AnyRef): Template

  def render: String

  final def render(parameters: Iterable[(String, AnyRef)]): String = (parameters foldLeft this) { (template, kv) =>
    template.set(kv._1, kv._2)
  }.render
