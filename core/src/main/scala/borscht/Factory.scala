package borscht

import java.nio.file.Path

class Factory(provider: CfgProvider):
  def parse(content: String): CfgNode = provider.parse(content)

  def apply(): CfgNode = provider()

  def apply(paths: Iterable[Path]): CfgNode = if (paths.isEmpty) apply() else provider(paths)

  def apply(paths: Path*): CfgNode = apply(paths)

  implicit final class CfgStringContext(sc: StringContext):
    def cfg(args: Any*): CfgNode = parse(sc.s(args: _*).stripMargin)
