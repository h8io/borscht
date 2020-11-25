package borscht.parsers

import borscht._

given ScalarNodeParser as Parser[ScalarNode] = { case node: ScalarNode => node }

given IterableNodeParser as Parser[IterableNode] = { case node: IterableNode => node }

given ObjectNodeParser as Parser[ObjectNode] = { case node: ObjectNode => node }
