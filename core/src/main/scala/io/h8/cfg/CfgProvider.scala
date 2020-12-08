package io.h8.cfg

import java.nio.file.Path

trait CfgProvider:
  def parse(content: String): CfgNode

  def apply(): CfgNode

  def apply(paths: Iterable[Path]): CfgNode
