package borscht

import scala.annotation.targetName

class Meta(val nodeParserRenderableString: NodeParser[RenderableString]):
  @targetName("merge")
  def ++(that: Meta): Meta = if (this == that || that == Meta.Empty) this else ???

object Meta extends (CfgNode => Meta):
  override def apply(cfg: CfgNode): Meta = ???

  object Empty extends Meta(NodeParserNothing):
    @targetName("merge")
    override def ++(that: Meta): Meta = that

