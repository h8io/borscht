package io.h8.cfg

import java.lang.{Boolean => jBoolean}

trait Node:
  def position: Position

  def toString: String
