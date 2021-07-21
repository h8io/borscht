package borscht

import scala.annotation.targetName

class Meta(val nodeParserRenderableString: Option[NodeParser[RenderableString]]):
  @targetName("merge")
  def ++(that: Meta): Meta = if (this == that || that == Meta.Empty) this else {
    val nprs: Option[NodeParser[RenderableString]] = nodeParserRenderableString map { thisParser =>
      that.nodeParserRenderableString map { thatParser =>
        thatParser orElse thisParser
      } getOrElse thisParser
    } orElse that.nodeParserRenderableString
    new Meta(nprs)
  }


object Meta extends (CfgNode => Meta):
  override def apply(cfg: CfgNode): Meta = ???

  object Empty extends Meta(None):
    @targetName("merge")
    override def ++(that: Meta): Meta = that

