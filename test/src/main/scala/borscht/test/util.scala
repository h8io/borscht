package borscht.test

import borscht.*

def cfg(entries: (String, Any)*): CfgNode = TestCfgNode(entries.toMap, Meta.Empty, Position.None)

def seq(entries: Any*): SeqNode = TestSeqNode(entries.toList, Meta.Empty, Position.None)

def scalar(value: Any): ScalarNode = TestScalarNode(value, Meta.Empty, Position.None)
