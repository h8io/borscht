# Borscht: Scala 3 configuration library

![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.h8.borscht/borscht-core_3/badge.svg)

## Overview
  * supports configuration files (could be extended)
    * [HOCON](https://github.com/lightbend/config/blob/main/README.md)
    * [YAML](https://yaml.org/) ([Jackson YAML](https://github.com/FasterXML/jackson-dataformats-text/tree/master/yaml) is the underlying library)
    * [TOML](https://toml.io/) ([Jackson TOML](https://github.com/FasterXML/jackson-dataformats-text/tree/master/toml) is the underlying library)
  * merges configurations of all formats
  * renders strings from objects with template engines (could be extended)
    * [StringTemplate 4](https://www.stringtemplate.org/)
    * [Apache Commons Text](https://commons.apache.org/proper/commons-text/userguide.html) templates, see [StringSubstitutor](http://commons.apache.org/proper/commons-text/apidocs/org/apache/commons/text/StringSubstitutor.html)
  * has extensible parser for different objects and values
