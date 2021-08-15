package borscht.typed

import borscht.{CfgException, Position}

class UnknownValueTypeException(`type`: String, position: Position)
  extends CfgException(s"Unknown value type: ${`type`}", position)
