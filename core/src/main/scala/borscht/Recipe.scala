package borscht

import java.nio.file.Path

class Recipe(provider: ConfigProvider, val NodeParserString: NodeParser[String] = NodeParserScalarString):
  given Recipe = this

  def parse(content: String): ConfigNode = provider.parse(content)

  def apply(): ConfigNode = provider()

  def apply(paths: Iterable[Path]): ConfigNode = provider(paths)

  def apply(paths: Path*): ConfigNode = provider(paths)

  implicit final class CfgStringContext(sc: StringContext):
    def cfg(args: Any*): ConfigNode = parse(sc.s(args: _*).stripMargin)
