package borscht

trait Node:
  def meta: Meta

  def withMeta(meta: Meta): Node

  def position: Position

  def toString: String
