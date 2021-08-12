package borscht.typed

import borscht.Node

import scala.annotation.targetName

@FunctionalInterface
trait ValueParser extends (Node => Any)
