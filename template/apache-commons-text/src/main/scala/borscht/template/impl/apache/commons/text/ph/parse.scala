package borscht.template.impl.apache.commons.text.ph

import borscht.template.impl.apache.commons.text.Placeholder

def parse(chars: String): Placeholder = Parser(chars)()
