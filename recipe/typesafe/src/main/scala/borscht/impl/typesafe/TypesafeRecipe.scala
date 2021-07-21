package borscht.impl.typesafe

import borscht.{CfgNode, Meta, Recipe}
import com.typesafe.config.ConfigFactory

import java.nio.file.Path

object TypesafeRecipe extends Recipe:
  override def parse(content: String): CfgNode = new TypesafeCfgNode(ConfigFactory.parseString(content))

  override def apply(): CfgNode = new TypesafeCfgNode(ConfigFactory.load)

  override def load(paths: Iterable[Path]): CfgNode = new TypesafeCfgNode(
    (paths.iterator map { path =>
      ConfigFactory.parseFile(path.toFile)
    } reduce { (prev, next) => next withFallback prev }).resolve)