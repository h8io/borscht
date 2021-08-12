package borscht.test

import borscht.*

def cfg(entries: (String, Any)*): CfgNode = MockCfgNode(entries.toMap, Meta.Empty, Position.None)

def seq(entries: Any*): SeqNode = MockSeqNode(entries.toList, Meta.Empty, Position.None)

def scalar(value: Any): ScalarNode = MockScalarNode(value, Meta.Empty, Position.None)
