package borscht.typed

import borscht.{CfgException, Position}

class UndefinedValueException(position: Position) extends CfgException(s"Undefined value", position)
