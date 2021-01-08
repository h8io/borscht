val JacksonVersion = "2.12.0"

ThisBuild / organization := "io.h8"
ThisBuild / scalaVersion := "3.0.0-M3"

ThisBuild / libraryDependencies ++= Seq(
    //"org.scalamock" %% "scalamock" % "5.1.+" % Test,
    "org.scalatest" %% "scalatest" % "3.2.+" % Test)

//ThisBuild / scalacOptions += "-Yexplicit-nulls"

lazy val core = project
  .in(file("core"))
  .settings(name := "borscht-core")

lazy val `provider-typesafe` = project
  .in(file("provider/typesafe"))
  .settings(
    name := "borscht-typesafe",
    libraryDependencies += "com.typesafe" % "config" % "1.4.1")
  .dependsOn(core)

lazy val `provider-jackson` = project
  .in(file("provider/jackson"))
  .settings(
      name := "borscht-jackson",
      libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % JacksonVersion)
  .dependsOn(core)

lazy val `provider-jackson-yaml` = project
  .in(file("provider/jackson/yaml"))
  .settings(
    name := "borscht-jackson",
    libraryDependencies += "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % JacksonVersion)
  .dependsOn(`provider-jackson`)

lazy val `template-core` = project
  .in(file("template/core"))
  .settings(name := "borscht-template-core")
  .dependsOn(`core`)

lazy val `template-st4` = project
  .in(file("template/st4"))
  .settings(
      name := "borscht-template-st4",
      libraryDependencies += "org.antlr" % "ST4" % "4.3.1")
  .dependsOn(`template-core`, util)

lazy val `template-apache-commons-text` = project
  .in(file("template/apache-commons-text"))
  .settings(
    name := "borscht-template-apache-commons-text",
    libraryDependencies += "org.apache.commons" % "commons-text" % "1.9")
  .dependsOn(`template-core`, util)

lazy val util = project
  .in(file("util"))
  .settings(name := "borscht-util")

lazy val examples = project
  .in(file("examples"))
  .settings(
    name := "borscht-examples",
    libraryDependencies ++= Seq(  // for GitHub Scala CI
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % JacksonVersion,
      "org.antlr" % "ST4" % "4.3.1",
      "org.apache.commons" % "commons-text" % "1.9"),
    classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat,
    publishArtifact := false)
  .dependsOn(`provider-typesafe`, `provider-jackson-yaml`, `template-st4`, `template-apache-commons-text`)

lazy val root = project
  .in(file("."))
  .settings(
    name := "borscht",
    publishArtifact := false)
  .aggregate(
    core, util, examples,
    `provider-typesafe`, `provider-jackson`, `provider-jackson-yaml`,
    `template-core`, `template-st4`, `template-apache-commons-text`)
