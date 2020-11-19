package borscht

import java.nio.file.Path

trait ConfigProvider:
  def parse(content: String): ObjectNode

  def apply(): ObjectNode

  def apply(paths: Iterable[Path]): ObjectNode
