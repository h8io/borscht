package borscht.impl.jackson.toml

import borscht.impl.jackson.JacksonRecipe
import com.fasterxml.jackson.dataformat.toml.TomlFactory
import com.fasterxml.jackson.databind.ObjectMapper

private val DefaultFileNames = List("application.toml")
private val Mapper = ObjectMapper(TomlFactory())

object TomlRecipe extends JacksonRecipe(Mapper, DefaultFileNames)
