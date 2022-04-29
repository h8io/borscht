package borscht.impl.jackson

import borscht.*
import borscht.impl.system.SystemCfgNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}

import java.io.File
import java.net.URL
import java.nio.file.Path
import scala.collection.immutable.Queue

open class JacksonRecipe(mapper: ObjectMapper, defaultFileNames: List[String] = Nil)
    extends Recipe(JacksonCfgProvider(mapper, defaultFileNames))

private class JacksonCfgProvider(mapper: ObjectMapper, defaultFileNames: List[String] = Nil) extends CfgProvider:
  override def apply(sources: Iterable[CfgSource]): CfgNode =
    if sources.isEmpty then
      (defaultFileNames.iterator map ClassLoader.getSystemResource filter (_ != null)).nextOption map { url =>
        val src = JacksonSource.url(url)
        asConfigNode(mapper.readTree(url), src) ++ SystemCfgNode()
      } getOrElse {
        throw CfgException(
          s"A default file is not found",
          JacksonSource.oneOf(Queue.from(defaultFileNames map JacksonSource.resource.apply))
        )
      }
    else
      (sources.iterator map { source =>
        val (node, src) = source match
          case file: File      => (mapper.readTree(file), JacksonSource.file(file))
          case path: Path      => (mapper.readTree(path.toFile), JacksonSource.path(path))
          case content: String => (mapper.readTree(content), JacksonSource.raw)
          case url: URL        => (mapper.readTree(url), JacksonSource.url(url))
        asConfigNode(node, src)
      } reduce (_ ++ _)) ++ SystemCfgNode()

  private def asConfigNode(node: JsonNode, src: JacksonSource): CfgNode =
    JacksonCfgNode(asObjectNode(node, src), src, Meta.Empty)

  private def asObjectNode(node: JsonNode, src: JacksonSource): ObjectNode = node match
    case node: ObjectNode => node
    case _                => throw CfgException(s"The root node must be an object: $node", src)
