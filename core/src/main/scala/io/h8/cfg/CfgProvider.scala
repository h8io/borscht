package io.h8.cfg

import java.nio.file.Path

trait CfgProvider:
  def parse(content: String)(using factory: Factory): CfgNode

  def apply()(using factory: Factory): CfgNode

  def apply(paths: Iterable[Path])(using factory: Factory): CfgNode
