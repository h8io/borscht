package borscht.typed.types

import borscht.{Node, ScalarNode}
import borscht.parsers.given

object RefTypeBigInt extends RefTypeInherited[BigInt]

object RefTypeBigDecimal extends RefTypeInherited[BigDecimal]

object RefTypeByte extends RefTypeInherited[Byte]

object RefTypeDouble extends RefTypeInherited[Double]

object RefTypeFloat extends RefTypeInherited[Float]

object RefTypeInt extends RefTypeInherited[Int]

object RefTypeLong extends RefTypeInherited[Long]

object RefTypeShort extends RefTypeInherited[Short]
