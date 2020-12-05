package io.h8.cfg.template.impl.st4.renderers

import org.stringtemplate.v4.{AttributeRenderer, STGroup}

import scala.reflect.ClassTag

trait Renderer[T](using tag: ClassTag[T]) extends AttributeRenderer[T]:
  private[st4] final def apply(group: STGroup): Unit =
    group.registerRenderer(tag.runtimeClass.asInstanceOf[Class[T]], this) 
