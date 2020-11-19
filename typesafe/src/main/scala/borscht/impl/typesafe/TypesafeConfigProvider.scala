package borscht.impl.typesafe

import java.nio.file.Path

import borscht.{ConfigProvider, ObjectNode}
import com.typesafe.config.ConfigFactory

final class TypesafeConfigProvider extends ConfigProvider:
  override def parse(content: String): ObjectNode =
    new TypesafeObjectNode(ConfigFactory.parseString(content))

  override def apply(): ObjectNode = new TypesafeObjectNode(ConfigFactory.load)

  override def apply(paths: Iterable[Path]): ObjectNode = new TypesafeObjectNode(
    (paths map { path =>
      ConfigFactory.parseFile(path.toFile)
    } reduce { (prev, next) => next withFallback  prev }).resolve)