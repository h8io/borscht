package borscht

import java.nio.file.Path

trait ConfigProvider:
  def parse(content: String)(using recipe: Recipe): ObjectNode

  def apply()(using recipe: Recipe): ObjectNode

  def apply(paths: Iterable[Path])(using recipe: Recipe): ObjectNode
