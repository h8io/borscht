package borscht.template

type TemplateParameterValueParser = PartialFunction[String, AnyRef]

object TemplateParameterValueParser:
  def apply(parsers: Map[String, String => AnyRef] = DefaultParsers,
            separator: String = "::"): TemplateParameterValueParser = new TemplateParameterValueParser:
    def apply(value: String): AnyRef = parse(value) match {
      case (Some(parser), raw) => parser(raw)
      case (None, _) => throw IllegalArgumentException(s"Parser not found for $value")
    }

    def isDefinedAt(value: String): Boolean = parse(value)._1.isDefined

    private def parse(value: String): (Option[String => AnyRef], String) = value.split(separator, 2) match
      case Array(v) => None -> v
      case Array(t, v) => parsers.get(t) -> v
      case _ => throw IllegalStateException("It should not happen")

  val DefaultParsers: Map[String, String => AnyRef] = Map()
