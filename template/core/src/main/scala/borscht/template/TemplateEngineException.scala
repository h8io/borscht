package borscht.template

import borscht.{CfgException, Position}

class TemplateEngineException(message: String, position: Position, cause: Option[Throwable] = None)
    extends CfgException(message, position, cause)
