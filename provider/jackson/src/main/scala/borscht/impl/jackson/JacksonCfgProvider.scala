package borscht.impl.jackson

import borscht.impl.system.SystemCfgNode
import borscht.{CfgException, CfgNode, CfgProvider}

import java.nio.file.Path
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}

import scala.collection.immutable.Queue

open class JacksonCfgProvider(mapper: ObjectMapper, defaultFileNames: List[String] = Nil) extends CfgProvider:
  override def parse(content: String): CfgNode =
    asConfigNode(mapper.readTree(content), JacksonSource.raw)

  override def apply(): CfgNode =
    (defaultFileNames.iterator map ClassLoader.getSystemResource filter (_ != null)).nextOption map { url =>
      val src = JacksonSource.url(url)
      asConfigNode(mapper.readTree(url), src) ++ SystemCfgNode()
    } getOrElse {
      throw CfgException(s"A default file is not found",
        JacksonSource.oneOf(Queue.from(defaultFileNames map JacksonSource.resource.apply)))
    }

  override def apply(paths: Iterable[Path]): CfgNode =
    (paths.iterator map { path =>
      asConfigNode(mapper.readTree(path.toFile), JacksonSource.path(path))
    } reduce (_ ++ _)) ++ SystemCfgNode()
    
  private def asConfigNode(node: JsonNode, src: JacksonSource): CfgNode =
    JacksonCfgNode(asObjectNode(node, src), src)

  private def asObjectNode(node: JsonNode, src: JacksonSource): ObjectNode = node match
    case node: ObjectNode => node
    case _ => throw CfgException(s"The root node must be an object: $node", src)
