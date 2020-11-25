package borscht.parsers

import borscht._

given ScalarNodeParser as Parser[ScalarNode] = borscht.ScalarNodeParser

given IterableNodeParser as Parser[IterableNode] = borscht.IterableNodeParser

given ObjectNodeParser as Parser[ObjectNode] = borscht.ObjectNodeParser
