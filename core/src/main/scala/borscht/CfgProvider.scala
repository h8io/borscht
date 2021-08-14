package borscht

import java.nio.file.Path

trait CfgProvider:
  def parse(content: String): CfgNode

  def apply(): CfgNode
  
  def load(paths: Iterable[Path]): CfgNode

