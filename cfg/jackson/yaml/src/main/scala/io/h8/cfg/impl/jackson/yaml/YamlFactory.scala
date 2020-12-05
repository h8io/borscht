package io.h8.cfg.impl.jackson.yaml

import io.h8.cfg._

class YamlFactory(NodeParserString: NodeParser[String] = NodeParserPlainString)
  extends Factory(YamlCfgProvider, NodeParserString)