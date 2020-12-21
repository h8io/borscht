package borscht.impl.jackson.yaml

import borscht.impl.jackson.JacksonCfgProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

private val DefaultFileNames = List("application.yaml", "application.yml")
private val Mapper = ObjectMapper(YAMLFactory())

object YamlCfgProvider extends JacksonCfgProvider(Mapper, DefaultFileNames)
