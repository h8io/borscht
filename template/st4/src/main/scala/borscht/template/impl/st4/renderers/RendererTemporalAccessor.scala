package borscht.template.impl.st4.renderers

import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.Locale

object RendererTemporalAccessor extends Renderer[TemporalAccessor]:
  override def toString(value: TemporalAccessor, formatString: String, locale: Locale): String = {
    if (formatString == null) DateTimeFormatter.ISO_DATE_TIME.localizedBy(locale)
    else DateTimeFormatter.ofPattern(formatString, locale)
  }.format(value)
