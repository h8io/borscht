package io.h8.cfg.template

trait Template:
  def set(key: String, value: AnyRef): Template
  
  def set(parameters: IterableOnce[(String, AnyRef)]): Template

  def set(parameters: (String, AnyRef)*): Template = set(parameters)
  
  def render: String

  def render(parameters: IterableOnce[(String, AnyRef)]): String = set(parameters).render

  def render(parameters: (String, AnyRef)*): String = render(parameters)
