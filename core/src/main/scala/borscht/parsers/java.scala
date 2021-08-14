package borscht.parsers

import borscht.NodeParser

import java.text.MessageFormat
import java.util.Locale
import scala.reflect.ClassTag

given NodeParserLocale: NodeParser[Locale] =
  NodeParserString andThen (new Locale.Builder().setLanguageTag(_).build()) orElse (NodeParserCfgNode andThen { cfg =>
    cfg.get[String]("variant") map { variant =>
      Locale(cfg[String]("language"), cfg[String]("country"), variant)
    } getOrElse {
      cfg.get[String]("country") map { country =>
        Locale(cfg[String]("language"), country)
      } getOrElse {
        Locale(cfg[String]("language"))
      }
    }
  })

given NodeParserMessageFormat: NodeParser[MessageFormat] =
  NodeParserString andThen (MessageFormat(_)) orElse (NodeParserCfgNode andThen { cfg =>
    cfg.get[Locale]("locale") map { locale =>
      MessageFormat(cfg[String]("pattern"), locale)
    } getOrElse {
      MessageFormat(cfg[String]("pattern"))
    }
  })
