package io.h8.cfg.impl.typesafe

import io.h8.cfg.{Factory, NodeParser, NodeParserPlainString}

class TypesafeFactory(NodeParserString: NodeParser[String] = NodeParserPlainString)
  extends Factory(TypesafeCfgProvider, NodeParserString)
