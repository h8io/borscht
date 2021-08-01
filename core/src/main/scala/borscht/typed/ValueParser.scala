package borscht.typed

import borscht.Node

@FunctionalInterface
trait ValueParser extends (Node => Any)
