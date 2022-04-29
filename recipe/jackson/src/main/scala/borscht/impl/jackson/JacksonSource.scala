package borscht.impl.jackson

import borscht.Position

import java.io.File
import java.net.URL
import java.nio.file.Path
import scala.collection.immutable.Queue

private[jackson] enum JacksonSource extends Position.Some:
  case raw
  case resource(name: String)
  case path(path: Path)
  case file(file: File)
  case url(url: URL)
  case oneOf(sources: Iterable[JacksonSource])

  override def toString: String = this match
    case _: raw.type                             => "raw"
    case resource(name)                          => s"resource($name)"
    case path(path)                              => s"path(${path.toAbsolutePath})"
    case file(file)                              => s"file(${file.getAbsolutePath}"
    case url(url: URL)                           => s"url($url)"
    case oneOf(sources: Iterable[JacksonSource]) => s"one-of(${sources mkString ", "})"
