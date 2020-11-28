package borscht

import java.nio.file.Path

trait ConfigProvider:
  def parse(content: String)(using recipe: Recipe): ConfigNode

  def apply()(using recipe: Recipe): ConfigNode

  def apply(paths: Iterable[Path])(using recipe: Recipe): ConfigNode
