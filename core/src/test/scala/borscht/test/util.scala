package borscht.test

import borscht.*

def cfg(entries: (String, Any)*): CfgNode = TestCfgNode((entries map { (key, value) => value match
  case node: Node => key -> node
  case _ => key -> TestScalarNode(value)
}).toMap)

def seq(entries: Any*): SeqNode = TestSeqNode((entries map {
  case node: Node => node
  case value: Any => TestScalarNode(value)
}).toList)

def scalar(value: Any): ScalarNode = TestScalarNode(value)
