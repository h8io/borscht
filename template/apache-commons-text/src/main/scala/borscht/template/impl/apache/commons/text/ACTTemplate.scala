package borscht.template.impl.apache.commons.text

import borscht.template.Template
import borscht.template.impl.apache.commons.text.ph.parse
import borscht.template.impl.apache.commons.text.renderers.{DefaultRenderer, Renderer, TemporalRenderer}
import org.apache.commons.text.StringSubstitutor
import org.apache.commons.text.lookup.{StringLookup, StringLookupFactory}

import java.util.Locale
import scala.jdk.CollectionConverters._

private[text] final class ACTTemplate(substitutor: StringSubstitutor,
                                      template: String,
                                      parameters: Map[String, AnyRef] = Map.empty,
                                      renderer: Renderer = TemporalRenderer) extends Template with StringLookup:
  override def set(key: String, value: AnyRef): Template =
    ACTTemplate(substitutor, template, parameters + (key -> value))

  override def set(parameters: IterableOnce[(String, AnyRef)]): Template =
    ACTTemplate(substitutor, template, this.parameters ++ parameters)

  override def apply(): String =
    val lookup = StringLookupFactory.INSTANCE.interpolatorStringLookup(
      java.util.Collections.singletonMap("ph", this),
      substitutor.getStringLookup,
      true)
    new StringSubstitutor(substitutor).setVariableResolver(lookup).replace(template)

  // TODO Add formatter
  override def lookup(key: String): String =
    val ph = parse(key)
    render(ph, parameters.get(ph.key))

  private def render(ph: Placeholder, value: Option[AnyRef]): String = value map {
    case list: List[_] =>
      list map { item => render(ph, Option(item.asInstanceOf[AnyRef])) } mkString (ph.separator getOrElse ", ")
    case value =>
      val locale = ph.locale getOrElse Locale.getDefault(Locale.Category.FORMAT)
      renderer(value, ph.format, locale) getOrElse DefaultRenderer(value, ph.format, locale)
  } getOrElse ph.nullValue.orNull
  
