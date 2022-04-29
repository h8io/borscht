package borscht

import java.io.{File, InputStream}
import java.net.URL
import java.nio.file.Path

type CfgSource = File | Path | String | URL

class Recipe(provider: CfgProvider):
  def apply(sources: CfgSource*): CfgNode = apply(sources)

  def apply(sources: Iterable[CfgSource]): CfgNode = upgrade(provider(sources))

  private def upgrade(cfg: CfgNode): CfgNode = cfg.withMeta(Meta(cfg))

  implicit final class CfgStringContext(sc: StringContext):
    def cfg(args: Any*): CfgNode = apply(sc.s(args*).stripMargin)
