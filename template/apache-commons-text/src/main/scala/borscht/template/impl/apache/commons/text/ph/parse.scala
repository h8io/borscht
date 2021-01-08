package borscht.template.impl.apache.commons.text.ph

import borscht.template.impl.apache.commons.text.ValueFormat

def parse(chars: String): (String, ValueFormat) = Parser(chars)()
