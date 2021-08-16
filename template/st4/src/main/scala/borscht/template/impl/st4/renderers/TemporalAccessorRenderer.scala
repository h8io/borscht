package borscht.template.impl.st4.renderers

import borscht.CfgNode
import borscht.parsers.{NodeParserCfgNode, NodeParserString}
import borscht.template.renderers.TimeFormats

import java.time.{LocalDate, LocalTime, OffsetTime}
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.Locale

final class TemporalAccessorRenderer(formats: TimeFormats) extends Renderer[TemporalAccessor]:
  def this() = this(TimeFormats.default)

  def this(formats: CfgNode) = this(new TimeFormats(formats))

  override def toString(value: TemporalAccessor, format: (String | Null), locale: Locale): String =
    formats.format(Option(format), locale, value)
