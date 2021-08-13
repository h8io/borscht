package borscht.template

import borscht.Node

trait TemplateEngine extends (Node => Template)