package borscht.impl.typesafe

import borscht.{CfgNode, CfgProvider, CfgSource, Recipe}
import com.typesafe.config.ConfigFactory

import java.io.File
import java.net.URL
import java.nio.file.Path

private object TypesafeCfgProvider extends CfgProvider:
  override def apply(paths: Iterable[CfgSource]): CfgNode = new TypesafeCfgNode(
    ConfigFactory.load((paths.iterator map { source =>
      source match
        case file: File      => ConfigFactory.parseFile(file)
        case path: Path      => ConfigFactory.parseFile(path.toFile)
        case content: String => ConfigFactory.parseString(content)
        case url: URL        => ConfigFactory.parseURL(url)
    } reduce { (prev, next) => next withFallback prev }))
  )

object TypesafeRecipe extends Recipe(TypesafeCfgProvider)
