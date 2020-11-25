package borscht.parsers

import borscht._

given ScalarNodeParser as NodeParser[ScalarNode] = { case node: ScalarNode => node }

given IterableNodeParser as NodeParser[IterableNode] = { case node: IterableNode => node }

given ObjectNodeParser as NodeParser[ObjectNode] = { case node: ObjectNode => node }
