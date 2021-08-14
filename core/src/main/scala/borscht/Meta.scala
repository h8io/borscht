package borscht

import borscht.parsers.given
import borscht.reflect.ComponentRef
import borscht.typed.*

import scala.annotation.targetName

case class Meta(val nodeParserRenderableString: Option[NodeParser[RenderableString]],
                val nodeParserValueParser: Option[NodeParser[ValueParser]],
                val nodeParserValueRef: Option[NodeParser[ValueRef]]):
  @targetName("merge")
  def ++(that: Meta): Meta = if (this == that || that == Meta.Empty) this else new Meta(
    merge(nodeParserRenderableString, that.nodeParserRenderableString),
    merge(nodeParserValueParser, that.nodeParserValueParser),
    merge(nodeParserValueRef, that.nodeParserValueRef))

  private def merge[T](fallback: Option[NodeParser[T]], main: Option[NodeParser[T]]): Option[NodeParser[T]] =
    fallback match
      case `main` => main
      case Some(fallbackParser) => main map (_ orElse fallbackParser) orElse fallback
      case None => main

object Meta extends (CfgNode => Meta) :
  override def apply(cfg: CfgNode): Meta =
    cfg.get[CfgNode]("borscht") map { nps =>
      new Meta(
        nps.get[ComponentRef[NodeParser[RenderableString]]]("renderable-string") map (_.get),
        nps.get[ComponentRef[NodeParser[ValueParser]]]("value-parser") map (_.get),
        nps.get[ComponentRef[NodeParser[ValueRef]]]("value-ref") map (_.get))
    } getOrElse Empty

  object Empty extends Meta(None, None, None) :
    @targetName("merge")
    override def ++(that: Meta): Meta = that

