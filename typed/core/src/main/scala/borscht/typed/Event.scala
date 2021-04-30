package borscht.typed

import scala.annotation.tailrec

enum Event:
  case TypeName(value: String, position: Int)
  case TypeListStart(position: Int)
  case TypeListEnd(position: Int)
  case TypeListSeparator(position: Int)
  case End(position: Int)
  case Error(message: String, position: Int)
