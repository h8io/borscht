package borscht.template.impl.st4.renderers

import org.stringtemplate.v4.{AttributeRenderer, STGroup}

import scala.reflect.ClassTag

trait Renderer[T](using tag: ClassTag[T]) extends AttributeRenderer[T]:
  final private[st4] def apply(group: STGroup): STGroup =
    group.registerRenderer(tag.runtimeClass.asInstanceOf[Class[T]], this)
    group
