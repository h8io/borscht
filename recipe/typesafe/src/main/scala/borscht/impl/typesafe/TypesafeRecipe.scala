package borscht.impl.typesafe

import borscht.{CfgNode, CfgProvider, Meta, Recipe}
import com.typesafe.config.ConfigFactory

import java.nio.file.Path

private object TypesafeCfgProvider extends CfgProvider:
  override def parse(content: String): CfgNode = new TypesafeCfgNode(ConfigFactory.parseString(content))

  override def apply(): CfgNode = new TypesafeCfgNode(ConfigFactory.load)

  override def load(paths: Iterable[Path]): CfgNode = new TypesafeCfgNode(ConfigFactory.load(
    (paths.iterator map { path =>
      ConfigFactory.parseFile(path.toFile)
    } reduce { (prev, next) => next withFallback prev })))

object TypesafeRecipe extends Recipe(TypesafeCfgProvider)
