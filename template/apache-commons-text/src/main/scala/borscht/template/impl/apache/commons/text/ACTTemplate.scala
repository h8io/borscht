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
    DefaultRenderer(renderer, vf, parameters.get(key).orNull)
  
