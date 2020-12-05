package io.h8.cfg.impl.jackson.yaml

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import io.h8.cfg.impl.jackson.JacksonCfgProvider

private val DefaultFileNames = List("application.yaml", "application.yml")
private val Mapper = ObjectMapper(YAMLFactory())

object YamlCfgProvider extends JacksonCfgProvider(Mapper, DefaultFileNames)
