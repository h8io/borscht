package borscht.parsers

import borscht.NodeParser

import java.text.MessageFormat
import java.util.Locale

given NodeParserClass[T](using parser: NodeParser[String]): NodeParser[Class[T]] =
  parser andThen { name => Class.forName(name).asInstanceOf[Class[T]] }

given NodeParserLocale(using parser: NodeParser[String]): NodeParser[Locale] =
  parser andThen (new Locale.Builder().setLanguageTag(_).build()) orElse (NodeParserCfgNode andThen { cfg =>
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

given NodeParserMessageFormat(using parser: NodeParser[String]): NodeParser[MessageFormat] =
  parser andThen (MessageFormat(_)) orElse (NodeParserCfgNode andThen { cfg =>
    cfg.get[Locale]("locale") map { locale =>
      MessageFormat(cfg[String]("pattern"), locale)
    } getOrElse {
      MessageFormat(cfg[String]("pattern"))
    }
  })
