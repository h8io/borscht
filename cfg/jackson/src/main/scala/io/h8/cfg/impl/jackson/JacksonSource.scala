package io.h8.cfg.impl.jackson

import io.h8.cfg.Position

import java.net.URL
import java.nio.file.Path
import scala.collection.immutable.Queue

private[jackson] sealed trait JacksonSource extends Position

object JacksonSource:
  val Raw = new JacksonSource:
    override def toString: String = "raw"
  
  def apply(resource: String): JacksonSource = new JacksonSource:
    override def toString: String = s"resource($resource)"

  def apply(path: Path): JacksonSource = new JacksonSource:
    override def toString: String = s"path(${path.toAbsolutePath})"

  def apply(url: URL): JacksonSource = new JacksonSource:
    override def toString: String = s"url($url)"

  def oneOf(sources: Iterable[JacksonSource]): JacksonSource = new JacksonSource:
    override def toString: String = s"one-of(${sources.mkString(", ")})"
