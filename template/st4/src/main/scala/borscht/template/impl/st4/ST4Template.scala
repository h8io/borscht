package borscht.template.impl.st4

import borscht.template.Template
import org.stringtemplate.v4.ST

import scala.collection.mutable

private[st4] final class ST4Template private(underlying: ST, parameters: Map[String, Any]) extends Template:
  def this(underlying: ST) = this(underlying, Map.empty)

  override def set(key: String, value: Any): Template = ST4Template(underlying, parameters + (key -> value))

  override def set(parameters: IterableOnce[(String, Any)]): Template =
    ST4Template(underlying, this.parameters ++ parameters)

  override def render: String = (parameters foldLeft ST(underlying))((t, kv) => t.add(kv._1, kv._2)).render
