package borscht.template.impl.st4

import borscht.{Node, SeqNode}
import borscht.parsers.{NodeParserComponentRef, NodeParserString}
import borscht.reflect.ComponentRef
import borscht.template.impl.st4.renderers.Renderer
import borscht.template.TemplateEngine
import org.stringtemplate.v4.{ST, STGroup}

import java.time.temporal.TemporalAccessor

final class ST4TemplateEngine(group: STGroup) extends TemplateEngine:
  def this() = this(STGroup())

  def this(renderers: List[Renderer[?]]) = this((renderers foldLeft STGroup()) { (group, renderer) => renderer(group) })

  def this(renderers: SeqNode) = this(renderers.list[ComponentRef[Renderer[?]]] map (_.get))

  override def apply(node: Node): ST4Template = ST4Template(ST(group, node.parse[String]))
