package borscht

import java.nio.file.Path

class Recipe(provider: CfgProvider):
  def parse(content: String): CfgNode = upgrade(provider.parse(content))

  def apply(): CfgNode = upgrade(provider())

  def apply(paths: Iterable[Path]): CfgNode = if (paths.isEmpty) apply() else load(paths)

  def load(paths: Iterable[Path]): CfgNode = upgrade(provider.load(paths))

  def apply(paths: Path*): CfgNode = apply(paths)

  private def upgrade(cfg: CfgNode): CfgNode = cfg.withMeta(Meta(cfg))

  implicit final class CfgStringContext(sc: StringContext):
    def cfg(args: Any*): CfgNode = parse(sc.s(args: _*).stripMargin)
