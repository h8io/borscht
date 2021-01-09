package borscht.template.impl.apache.commons.text

import borscht.template.Template
import borscht.template.impl.apache.commons.text.ph.parse
import borscht.template.impl.apache.commons.text.renderers.{Renderer, TemporalRenderer}
import org.apache.commons.text.StringSubstitutor
import org.apache.commons.text.lookup.{StringLookup, StringLookupFactory}

import java.text.{DateFormat, NumberFormat}
import java.time.temporal.TemporalAccessor
import java.util.{Calendar, Date, Formatter, Locale}
import scala.jdk.CollectionConverters._

private[text] final class ACTTemplate(substitutor: StringSubstitutor,
                                      template: String,
                                      renderer: Renderer,
                                      valueFormat: ValueFormat,
                                      parameters: Map[String, AnyRef] = Map.empty) extends Template with StringLookup:
  override def set(key: String, value: AnyRef): Template =
    ACTTemplate(substitutor, template, renderer, valueFormat, parameters + (key -> value))

  override def set(parameters: IterableOnce[(String, AnyRef)]): Template =
    ACTTemplate(substitutor, template, renderer, valueFormat, this.parameters ++ parameters)

  override def apply(): String =
    val lookup = StringLookupFactory.INSTANCE.interpolatorStringLookup(
      java.util.Collections.singletonMap("ph", this),
      substitutor.getStringLookup,
      true)
    new StringSubstitutor(substitutor).setVariableResolver(lookup).replace(template)

  override def lookup(ph: String): String =
    val (key, vf) = parse(ph, valueFormat)
    render(vf, parameters.get(key).orNull)

  private def render(vf: ValueFormat, value: AnyRef): String = renderer(vf, value) getOrElse {
    value match
      case null | None => vf.nullValue
      case Some(value) => render(vf, value.asInstanceOf[AnyRef])
      case _ => vf.format map { fmt =>
        value match
          case _: (Character | CharSequence | java.lang.Number | Date | Calendar | TemporalAccessor) =>
            val formatter = Formatter(vf.locale)
            try formatter.format(fmt, value).toString finally formatter.close()
          case seq: Iterable[_] =>
            render(vf, seq.iterator, vf.seqStart, vf.seqSeparator, vf.seqEnd)
          case product: Product =>
            render(vf, product.productIterator, vf.productStart, vf.productSeparator, vf.productEnd)
          case _ => throw IllegalArgumentException(s"Unformattable value $value class ${value.getClass}")
      } getOrElse {
        value match
          case n: Number => NumberFormat.getInstance(vf.locale).format(n)
          case cs: CharSequence => cs.toString
          case temporal: TemporalAccessor => borscht.util.render(temporal, None, vf.locale)
          case date: Date => render(date, vf.locale)
          case calendar: Calendar => render(calendar.getTime, vf.locale)
          case seq: Iterable[_] =>
            render(vf, seq.iterator, vf.seqStart, vf.seqSeparator, vf.seqEnd)
          case product: Product =>
            render(vf, product.productIterator, vf.productStart, vf.productSeparator, vf.productEnd)
          case _ => value.toString
      }
  }

  private def render(date: Date, locale: Locale): String =
    DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale).format(date)

  private def render(vf: ValueFormat, seq: Iterator[_], start: String, separator: String, end: String): String =
    seq map { item => render(vf, item.asInstanceOf[AnyRef]) } mkString (start, separator, end)
