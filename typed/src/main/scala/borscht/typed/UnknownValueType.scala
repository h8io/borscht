package borscht.typed

import borscht.{CfgException, Position}

class UnknownValueType(`type`: String, position: Position)
  extends CfgException(s"Unknown value type: ${`type`}", position)
