package borscht.template

type TemplateParserRegistry = PartialFunction[Option[String], TemplateParser]

class SimpleTemplateParserRegistry(private val default: Option[TemplateParser],
                                   private val parsers: Map[String, TemplateParser]) extends TemplateParserRegistry:
  override def isDefinedAt(name: Option[String]): Boolean = name map parsers.contains getOrElse default.isDefined

  override def apply(id: Option[String]): TemplateParser =
    id flatMap parsers.get orElse default getOrElse (throw MatchError(id))
    
  override def orElse[K <: Option[String], V >: TemplateParser](that: PartialFunction[K, V]): PartialFunction[K, V] =
    that match
      case that: SimpleTemplateParserRegistry =>
        new SimpleTemplateParserRegistry(default orElse that.default, that.parsers ++ parsers)
      case that => super.orElse(that)

object SimpleTemplateParserRegistry:
  def apply(default: Option[TemplateParser],
            parsers: IterableOnce[(String, TemplateParser)]): SimpleTemplateParserRegistry =
    new SimpleTemplateParserRegistry(default, parsers.iterator.toMap)
  
  def apply(default: Option[TemplateParser], parsers: (String, TemplateParser)*): SimpleTemplateParserRegistry =
    apply(default, parsers)

  def apply(default: TemplateParser, parsers: Iterable[(String, TemplateParser)]): SimpleTemplateParserRegistry =
    apply(Some(default), parsers)

  def apply(default: TemplateParser, parsers: (String, TemplateParser)*): SimpleTemplateParserRegistry =
    apply(default, parsers)

  def apply(default: String, parsers: Iterable[(String, TemplateParser)]): SimpleTemplateParserRegistry =
    val map = parsers.toMap
    new SimpleTemplateParserRegistry(Some(map(default)), map)

  def apply(default: String, parsers: (String, TemplateParser)*): SimpleTemplateParserRegistry =
    apply(default, parsers)

  def apply(parsers: Iterable[(String, TemplateParser)]): SimpleTemplateParserRegistry =
    apply(None, parsers)

  def apply(parsers: (String, TemplateParser)*): SimpleTemplateParserRegistry =
    apply(parsers)
