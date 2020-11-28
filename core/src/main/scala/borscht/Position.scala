package borscht

trait Position:
  final def |(that: Position): Position = Position.Composite(this, that)
  
  override def toString: String

object Position:
  private val Separator = " | "

  private def append(sb: StringBuilder, position: Position): StringBuilder = position match
    case Composite(p1, p2) => append(append(sb, p1) ++= Separator, p2)
    case p => append(sb, p)

  case class Composite(p1: Position, p2: Position) extends Position:
    override def toString: String = (append(append(StringBuilder() += '{', p1) ++= Separator, p2) += '}').result
