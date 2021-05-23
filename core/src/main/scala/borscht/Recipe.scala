package borscht

import java.nio.file.Path

trait Recipe:
  def parse(content: String): CfgNode

  def apply(): CfgNode

  def apply(paths: Iterable[Path]): CfgNode = if (paths.isEmpty) apply() else load(paths)

  def load(paths: Iterable[Path]): CfgNode
  
  def apply(paths: Path*): CfgNode = apply(paths)

  implicit final class CfgStringContext(sc: StringContext):
    def cfg(args: Any*): CfgNode = parse(sc.s(args: _*).stripMargin)
