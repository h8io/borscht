package borscht.template.impl.st4

import borscht.{CfgNode, Node}
import borscht.parsers.{NodeParserComponentRef, NodeParserList, NodeParserString}
import borscht.reflect.ComponentRef
import borscht.template.impl.st4.renderers.Renderer
import borscht.template.TemplateEngine
import org.stringtemplate.v4.{ST, STGroup}

import java.time.temporal.TemporalAccessor

final class ST4TemplateEngine(group: STGroup) extends TemplateEngine:
  def this(cfg: CfgNode) = this {
    val group = STGroup()
    cfg.list[ComponentRef[Renderer[?]]]("renderers") foreach (_.get(group))
    group
  }

  override def apply(node: Node): ST4Template = ST4Template(ST(group, node.parse[String]))
