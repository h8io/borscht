package borscht.template.impl.apache.commons.text.ph

import borscht.template.impl.apache.commons.text.ValueFormat

def parse(chars: String, valueFormat: ValueFormat): (String, ValueFormat) = Parser(chars)(valueFormat)
