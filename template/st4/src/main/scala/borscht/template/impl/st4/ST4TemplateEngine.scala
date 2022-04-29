package borscht.template.impl.st4

import borscht.parsers.{NodeParserRef, NodeParserString}
import borscht.template.TemplateEngine
import borscht.template.impl.st4.renderers.Renderer
import borscht.typed.Ref
import borscht.{Node, SeqNode}
import org.stringtemplate.v4.{ST, STGroup}

import java.time.temporal.TemporalAccessor

final class ST4TemplateEngine(group: STGroup) extends TemplateEngine:
  def this() = this(STGroup())

  def this(renderers: List[Renderer[?]]) = this((renderers foldLeft STGroup())((group, renderer) => renderer(group)))

  def this(renderers: SeqNode) = this(renderers.list[Ref[Renderer[?]]] map (_.value))

  override def apply(node: Node): ST4Template = ST4Template(ST(group, node.as[String]))
