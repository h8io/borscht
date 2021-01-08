package borscht.template.impl.st4.renderers

import borscht.util.render

import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.Locale

object TemporalAccessorRenderer extends Renderer[TemporalAccessor]:
  override def toString(value: TemporalAccessor, formatString: String, locale: Locale): String =
    render(value, Option(formatString), locale)
