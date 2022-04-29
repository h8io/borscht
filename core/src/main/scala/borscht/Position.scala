package borscht

import scala.annotation.targetName
import scala.collection.mutable

sealed trait Position:
  @targetName("merge")
  def +(that: Position): Position

  override def toString: String

object Position:
  trait Some extends Position:
    @targetName("merge")
    final override def +(that: Position): Position = if that == None then this else Position.Merged(this, that)

  object None extends Position:
    @targetName("merge")
    final override def +(that: Position): Position = that

    override def toString: String = "none"

  private val Separator = " | "

  private def append(sb: mutable.StringBuilder, position: Position): mutable.StringBuilder = position match
    case Merged(p1, p2) => append(append(sb, p1) ++= Separator, p2)
    case p              => sb ++= p.toString

  private case class Merged(p1: Position, p2: Position) extends Position.Some:
    override def toString: String =
      (append(append(StringBuilder() ++= getClass.getName += '(', p1) ++= Separator, p2) += ')').result
