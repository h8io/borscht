package borscht

import java.io.InputStream
import java.net.URL
import java.nio.file.Path

trait CfgProvider:
  def apply(sources: Iterable[CfgSource]): CfgNode
