package borscht

import borscht.parsers.{NodeParserCfgNode, NodeParserRefComponent}
import borscht.typed.*
import borscht.typed.types.DefaultRefTypes

import scala.annotation.targetName

case class Meta(nodeParserRenderableString: Option[NodeParser[RenderableString]], refTypes: Map[String, RefType]):
  @targetName("merge")
  def ++(that: Meta): Meta = if this == that || that == Meta.Empty then this
  else new Meta(merge(nodeParserRenderableString, that.nodeParserRenderableString), refTypes ++ that.refTypes)

  private def merge[T](fallback: Option[NodeParser[T]], main: Option[NodeParser[T]]): Option[NodeParser[T]] =
    fallback match
      case `main`  => main
      case Some(_) => main orElse fallback
      case None    => main

object Meta extends (CfgNode => Meta):
  override def apply(cfg: CfgNode): Meta =
    cfg.get[CfgNode]("borscht") map { nps =>
      new Meta(
        nps.child("renderable-string") map (_.as[RefComponent[NodeParser[RenderableString]]].value),
        DefaultRefTypes ++
          (nps.map[Ref[RefType]]("ref-types") map { (key, value) => key -> value.value })
      )
    } getOrElse Empty

  object Empty extends Meta(None, DefaultRefTypes):
    @targetName("merge")
    override def ++(that: Meta): Meta = that
