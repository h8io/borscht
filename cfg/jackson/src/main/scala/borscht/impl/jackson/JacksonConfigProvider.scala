package borscht.impl.jackson

import java.nio.file.Path

import borscht._
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}

import scala.collection.immutable.Queue

final class JacksonConfigProvider(mapper: ObjectMapper, defaultFileNames: List[String] = Nil) extends ConfigProvider:
  override def parse(content: String)(using recipe: Recipe): ConfigNode =
    asConfigNode(mapper.readTree(content), JacksonSource.Raw)

  override def apply()(using recipe: Recipe): ConfigNode =
    (defaultFileNames.iterator map ClassLoader.getSystemResource filter (_ != null)).nextOption map { url =>
      val src = JacksonSource(url)
      asConfigNode(mapper.readTree(url), src)
    } getOrElse {
      throw BorschtException(s"A default file is not found",
        JacksonSource.oneOf(Queue.from(defaultFileNames map JacksonSource.apply)))
    }

  override def apply(paths: Iterable[Path])(using recipe: Recipe): ConfigNode =
    paths.iterator map { path => asConfigNode(mapper.readTree(path.toFile), JacksonSource(path)) } reduce (_ ++ _)
    
  private def asConfigNode(node: JsonNode, src: JacksonSource)(using recipe: Recipe): ConfigNode =
    JacksonConfigNode(asObjectNode(node, src), src)

  private def asObjectNode(node: JsonNode, src: JacksonSource): ObjectNode = node match
    case node: ObjectNode => node
    case _ => throw BorschtException(s"The root node must be an object: $node", src)
