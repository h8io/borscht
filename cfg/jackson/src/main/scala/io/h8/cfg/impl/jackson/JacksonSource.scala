package io.h8.cfg.impl.jackson

import io.h8.cfg.Position

import java.net.URL
import java.nio.file.Path
import scala.collection.immutable.Queue

private[jackson] enum JacksonSource extends Position:
  case raw
  case resource(name: String)
  case path(path: Path)
  case url(url: URL)
  case oneOf(sources: Iterable[JacksonSource])

  override def toString: String = this match
    case _: raw.type => "raw"
    case resource(name) => s"resource($name)"
    case path(path) => s"path(${path.toAbsolutePath})"
    case url(url: URL) => s"url($url)"
    case oneOf(sources: Iterable[JacksonSource]) => s"one-of(${sources mkString ", "})"