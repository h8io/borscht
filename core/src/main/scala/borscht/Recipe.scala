package borscht

import java.nio.file.Path

class Recipe(provider: ConfigProvider) extends ConfigProvider:
  given Recipe = this

  override def parse(content: String): ObjectNode = provider.parse(content)

  override def apply(): ObjectNode = provider()

  override def apply(paths: Iterable[Path]): ObjectNode = provider(paths)

  def apply(paths: Path*): ObjectNode = provider(paths)

  implicit final class CfgStringContext(sc: StringContext):
    def cfg(args: Any*): ObjectNode = parse(sc.s(args: _*))
