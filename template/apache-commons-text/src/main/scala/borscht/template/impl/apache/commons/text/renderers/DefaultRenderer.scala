package borscht.template.impl.apache.commons.text.renderers

import borscht.template.impl.apache.commons.text.ValueFormat

import java.text.{DateFormat, NumberFormat, SimpleDateFormat}
import java.time.{LocalDate, LocalTime, OffsetTime}
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.{Calendar, Date, Formatter, Locale}

object DefaultRenderer:
  def apply(renderer: Renderer, vf: ValueFormat, value: AnyRef): String = renderer(vf, value) getOrElse {
    value match
      case null | None => vf.nullValue
      case Some(value) => apply(renderer, vf, value.asInstanceOf[AnyRef])
      case _ => vf.format map { fmt =>
        value match
          case _: (Character | CharSequence | java.lang.Number | Date | Calendar | TemporalAccessor) =>
            val formatter = Formatter(vf.locale)
            try formatter.format(fmt, value).toString finally formatter.close()
          case seq: Iterable[_] =>
            render(renderer, vf, seq.iterator, vf.seqStart, vf.seqSeparator, vf.seqEnd)
          case product: Product =>
            render(renderer, vf, product.productIterator, vf.productStart, vf.productSeparator, vf.productEnd)
          case _ => throw IllegalArgumentException(s"Unformattable value $value class ${value.getClass}")
      } getOrElse {
        value match
          case n: Number => NumberFormat.getInstance(vf.locale).format(n)
          case cs: CharSequence => cs.toString
          case temporal: TemporalAccessor => borscht.util.render(temporal, None, vf.locale)
          case date: Date => render(date, vf.locale)
          case calendar: Calendar => render(calendar.getTime, vf.locale)
          case seq: Iterable[_] =>
            render(renderer, vf, seq.iterator, vf.seqStart, vf.seqSeparator, vf.seqEnd)
          case product: Product =>
            render(renderer, vf, product.productIterator, vf.productStart, vf.productSeparator, vf.productEnd)
          case _ => value.toString
      }
  }

  private def render(date: Date, locale: Locale): String =
    DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale).format(date)

  private def render(renderer: Renderer, vf: ValueFormat, seq: Iterator[_],
                     start: String, separator: String, end: String) =
    seq map { item => DefaultRenderer(renderer, vf, item.asInstanceOf[AnyRef]) } mkString (start, separator, end)
