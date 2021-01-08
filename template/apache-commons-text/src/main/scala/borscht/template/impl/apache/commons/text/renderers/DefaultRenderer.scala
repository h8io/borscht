package borscht.template.impl.apache.commons.text.renderers

import borscht.template.impl.apache.commons.text.Placeholder

import java.text.{DateFormat, NumberFormat, SimpleDateFormat}
import java.time.{LocalDate, LocalTime, OffsetTime}
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.{Calendar, Date, Formatter, Locale}

object DefaultRenderer:
  def apply(renderer: Renderer, ph: Placeholder, value: AnyRef): String = renderer(ph, value) getOrElse {
    value match
      case null | None => ph.nullValue
      case Some(value) => apply(renderer, ph, value.asInstanceOf[AnyRef])
      case _ => ph.format map { fmt =>
        value match
          case _: (Character | CharSequence | java.lang.Number | Date | Calendar | TemporalAccessor) =>
            val formatter = Formatter(ph.locale)
            try formatter.format(fmt, value).toString finally formatter.close()
          case seq: Iterable[_] =>
            render(renderer, ph, seq.iterator, ph.seqStart, ph.seqSeparator, ph.seqEnd)
          case product: Product =>
            render(renderer, ph, product.productIterator, ph.productStart, ph.productSeparator, ph.productEnd)
          case _ => throw IllegalArgumentException(s"Unformattable value $value class ${value.getClass}")
      } getOrElse {
        value match
          case n: Number => NumberFormat.getInstance(ph.locale).format(n)
          case cs: CharSequence => cs.toString
          case temporal: TemporalAccessor => borscht.util.render(temporal, None, ph.locale)
          case date: Date => render(date, ph.locale)
          case calendar: Calendar => render(calendar.getTime, ph.locale)
          case seq: Iterable[_] =>
            render(renderer, ph, seq.iterator, ph.seqStart, ph.seqSeparator, ph.seqEnd)
          case product: Product =>
            render(renderer, ph, product.productIterator, ph.productStart, ph.productSeparator, ph.productEnd)
          case _ => value.toString
      }
  }

  private def render(date: Date, locale: Locale): String =
    DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale).format(date)

  private def render(renderer: Renderer, ph: Placeholder, seq: Iterator[_],
                     start: String, separator: String, end: String) =
    seq map { item => DefaultRenderer(renderer, ph, item.asInstanceOf[AnyRef]) } mkString (start, separator, end)
