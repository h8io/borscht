package borscht

import java.nio.file.Path

class Recipe(provider: ConfigProvider,
             val stringParser: Parser[String] = SimpleStringParser):
  given Recipe = this

  def parse(content: String): ObjectNode = provider.parse(content)

  def apply(): ObjectNode = provider()

  def apply(paths: Iterable[Path]): ObjectNode = provider(paths)

  def apply(paths: Path*): ObjectNode = provider(paths)

  implicit final class CfgStringContext(sc: StringContext):
    def cfg(args: Any*): ObjectNode = parse(sc.s(args: _*))
