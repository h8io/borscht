package borscht.impl.typesafe

import borscht.{CfgNode, CfgProvider}
import com.typesafe.config.ConfigFactory

import java.nio.file.Path

object TypesafeCfgProvider extends CfgProvider:
  override def parse(content: String): CfgNode = new TypesafeCfgNode(ConfigFactory.parseString(content))

  override def apply(): CfgNode = new TypesafeCfgNode(ConfigFactory.load)

  override def apply(paths: Iterable[Path]): CfgNode = new TypesafeCfgNode(
    (paths.iterator map { path =>
      ConfigFactory.parseFile(path.toFile)
    } reduce { (prev, next) => next withFallback prev }).resolve)