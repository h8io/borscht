package borscht.template.impl.apache.commons.text

import borscht.template.Template
import org.apache.commons.text.StringSubstitutor
import org.apache.commons.text.lookup.{StringLookup, StringLookupFactory}

import scala.jdk.CollectionConverters._

private[text] final class ACTTemplate(substitutor: StringSubstitutor,
                                      template: String,
                                      parameters: Map[String, AnyRef] = Map.empty) extends Template with StringLookup:
  override def set(key: String, value: AnyRef): Template =
    ACTTemplate(substitutor, template, parameters + (key -> value))

  override def set(parameters: IterableOnce[(String, AnyRef)]): Template =
    ACTTemplate(substitutor, template, this.parameters ++ parameters)

  override def apply(): String =
    val lookup = StringLookupFactory.INSTANCE.interpolatorStringLookup(
      java.util.Collections.singletonMap("cfg", this),
      substitutor.getStringLookup,
      true)
    new StringSubstitutor(substitutor).setVariableResolver(lookup).replace(template)

  override def lookup(key: String): String = parameters.get(key).map(_.toString).orNull
