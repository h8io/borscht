package borscht

import borscht.parsers.{NodeParserCfgNode, NodeParserComponentRef, NodeParserRef}
import borscht.reflect.ComponentRef
import borscht.typed.*
import borscht.typed.types.{DefaultRefTypes, RefTypeComponent}
import borscht.typedOld.*
import borscht.typedOld.types.DefaultValueTypes

import scala.annotation.targetName

case class Meta(val nodeParserRenderableString: Option[NodeParser[RenderableString]],
                val refTypes: Map[String, RefType],
                val valueTypes: Map[String, ValueType] = Map.empty):
  @targetName("merge")
  def ++(that: Meta): Meta = if (this == that || that == Meta.Empty) this else new Meta(
    merge(nodeParserRenderableString, that.nodeParserRenderableString),
    refTypes ++ that.refTypes,
    valueTypes ++ that.valueTypes)

  private def merge[T](fallback: Option[NodeParser[T]], main: Option[NodeParser[T]]): Option[NodeParser[T]] =
    fallback match
      case `main` => main
      case Some(fallbackParser) => main orElse fallback
      case None => main

object Meta extends (CfgNode => Meta):
  override def apply(cfg: CfgNode): Meta =
    cfg.get[CfgNode]("borscht") map { nps =>
      new Meta(
        nps.child("renderable-string") map (RefTypeComponent(_).cast[NodeParser[RenderableString]].value),
        DefaultRefTypes ++
          (nps.map[ComponentRef[RefType]]("ref-types") map  { (key, value) => key -> value.get }),
        DefaultValueTypes ++
          (nps.map[ComponentRef[ValueType]]("value-types") map  { (key, value) => key -> value.get }))
    } getOrElse Empty

  object Empty extends Meta(None, DefaultRefTypes, DefaultValueTypes):
    @targetName("merge")
    override def ++(that: Meta): Meta = that
