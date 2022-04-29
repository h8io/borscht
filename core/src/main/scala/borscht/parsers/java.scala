package borscht.parsers

import borscht.{CfgNode, PartialNodeParser, ScalarNode}

import java.text.MessageFormat
import java.util.Locale
import scala.reflect.ClassTag

given NodeParserLocale: PartialNodeParser[Locale] =
  case scalar: ScalarNode => new Locale.Builder().setLanguageTag(scalar.asString).build()
  case cfg: CfgNode =>
    cfg.get[String]("variant") map { variant =>
      Locale(cfg[String]("language"), cfg[String]("country"), variant)
    } getOrElse {
      cfg.get[String]("country") map (Locale(cfg[String]("language"), _)) getOrElse Locale(cfg[String]("language"))
    }

given NodeParserMessageFormat: PartialNodeParser[MessageFormat] =
  case scalar: ScalarNode => MessageFormat(scalar.asString)
  case cfg: CfgNode =>
    cfg.get[Locale]("locale") map { locale =>
      MessageFormat(cfg[String]("pattern"), locale)
    } getOrElse {
      MessageFormat(cfg[String]("pattern"))
    }
