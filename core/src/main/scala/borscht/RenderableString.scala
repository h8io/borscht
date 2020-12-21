package borscht

trait RenderableString:
  def render: String

trait RenderableMapString(parameters: Map[String, AnyRef]) extends RenderableString

trait RenderableSeqString(parameters: Iterable[AnyRef]) extends RenderableString
