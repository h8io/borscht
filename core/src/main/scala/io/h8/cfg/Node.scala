package io.h8.cfg

import java.lang.{Boolean => jBoolean}

trait Node(using factory: Factory):
  given Factory = factory
  
  def position: Position

  def toString: String
