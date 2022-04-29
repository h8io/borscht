package borscht.template.impl.apache.commons.text

import borscht.template.Template
import borscht.template.impl.apache.commons.text.renderers.{Renderer, TemporalRenderer}
import borscht.template.renderers.TimeFormats
import org.apache.commons.text.StringSubstitutor
import org.apache.commons.text.lookup.{StringLookup, StringLookupFactory}

import java.text.{DateFormat, NumberFormat}
import java.time.temporal.TemporalAccessor
import java.util.{Calendar, Date, Formatter, Locale}
import scala.jdk.CollectionConverters.*

final private[text] class ACTTemplate(
    substitutor: StringSubstitutor,
    template: String,
    renderers: List[Renderer],
    valueFormat: ValueFormat,
    parameters: Map[String, Any] = Map.empty
) extends Template
    with StringLookup:
  override def set(key: String, value: Any): Template =
    ACTTemplate(substitutor, template, renderers, valueFormat, parameters + (key -> value))

  override def set(parameters: IterableOnce[(String, Any)]): Template =
    ACTTemplate(substitutor, template, renderers, valueFormat, this.parameters ++ parameters)

  override def render: String =
    val lookup = StringLookupFactory.INSTANCE.interpolatorStringLookup(
      java.util.Collections.singletonMap("ph", this),
      substitutor.getStringLookup,
      true
    )
    new StringSubstitutor(substitutor).setVariableResolver(lookup).replace(template)

  override def lookup(ph: String): String =
    val (key, vf) = parse(ph, valueFormat)
    render(vf, parameters.get(key).orNull)

  private def render(vf: ValueFormat, value: Any): String =
    renderers.iterator.flatMap(_(vf, value)).nextOption getOrElse {
      value match
        case null | None => vf.nullValue
        case Some(value) => render(vf, value)
        case _ =>
          vf.format map { fmt =>
            value.asInstanceOf[AnyRef] match
              case _: (Character | CharSequence | Number | Date | Calendar | TemporalAccessor) =>
                val formatter = Formatter(vf.locale)
                try formatter.format(fmt, value).toString
                finally formatter.close()
              case seq: Iterable[?] =>
                render(vf, seq.iterator, vf.list.start, vf.list.separator, vf.list.end)
              case product: Product =>
                render(vf, product.productIterator, vf.product.start, vf.product.separator, vf.product.end)
              case _ => throw IllegalArgumentException(s"Unformattable value $value class ${value.getClass}")
          } getOrElse {
            value.asInstanceOf[AnyRef] match
              case n: Number                  => NumberFormat.getInstance(vf.locale).format(n)
              case cs: CharSequence           => cs.toString
              case temporal: TemporalAccessor => TimeFormats.default.format(None, vf.locale, temporal)
              case date: Date                 => render(date, vf.locale)
              case calendar: Calendar         => render(calendar.getTime, vf.locale)
              case seq: Iterable[?] =>
                render(vf, seq.iterator, vf.list.start, vf.list.separator, vf.list.end)
              case product: Product =>
                render(vf, product.productIterator, vf.product.start, vf.product.separator, vf.product.end)
              case _ => value.toString
          }
    }

  private def render(date: Date, locale: Locale): String =
    DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale).format(date)

  private def render(vf: ValueFormat, seq: Iterator[?], start: String, separator: String, end: String): String =
    seq map { item => render(vf, item.asInstanceOf[AnyRef]) } mkString (start, separator, end)
