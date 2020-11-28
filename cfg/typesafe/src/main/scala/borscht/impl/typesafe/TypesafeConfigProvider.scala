package borscht.impl.typesafe

import java.nio.file.Path

import borscht.{ConfigProvider, ConfigNode, Recipe}
import com.typesafe.config.ConfigFactory

final class TypesafeConfigProvider extends ConfigProvider:
  override def parse(content: String)(using recipe: Recipe): ConfigNode =
    new TypesafeConfigNode(ConfigFactory.parseString(content))

  override def apply()(using recipe: Recipe): ConfigNode = new TypesafeConfigNode(ConfigFactory.load)

  override def apply(paths: Iterable[Path])(using recipe: Recipe): ConfigNode = new TypesafeConfigNode(
    (paths map { path =>
      ConfigFactory.parseFile(path.toFile)
    } reduce { (prev, next) => next withFallback prev }).resolve)