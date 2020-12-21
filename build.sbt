val JacksonVersion = "2.12.0"

ThisBuild / organization := "io.h8.cfg"
ThisBuild / scalaVersion := "3.0.0-M3"

ThisBuild / libraryDependencies ++= Seq(
    // "org.scalamock" %% "scalamock" % "5.0.0" % Test,
    "org.scalatest" %% "scalatest" % "3.2.+" % Test)

//ThisBuild / scalacOptions += "-Yexplicit-nulls"

lazy val core = project
  .in(file("core"))
  .settings(name := "cfg-core")

lazy val `cfg-typesafe` = project
  .in(file("cfg/typesafe"))
  .settings(
    name := "cfg-typesafe",
    libraryDependencies += "com.typesafe" % "config" % "1.4.1")
  .dependsOn(core)

lazy val `cfg-jackson` = project
  .in(file("cfg/jackson"))
  .settings(
      name := "cfg-jackson",
      libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % JacksonVersion)
  .dependsOn(core)

lazy val `cfg-jackson-yaml` = project
  .in(file("cfg/jackson/yaml"))
  .settings(
    name := "cfg-jackson",
    libraryDependencies += "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % JacksonVersion)
  .dependsOn(`cfg-jackson`)

lazy val `template-core` = project
  .in(file("template/core"))
  .settings(name := "cfg-template-core")
  .dependsOn(`core`)

lazy val `template-st4` = project
  .in(file("template/st4"))
  .settings(
      name := "cfg-template-st4",
      libraryDependencies += "org.antlr" % "ST4" % "4.3.1")
  .dependsOn(`template-core`)

lazy val examples = project
  .in(file("examples"))
  .settings(
    name := "borscht-examples",
    libraryDependencies ++= Seq(  // for GitHub Scala CI
      "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % JacksonVersion,
      "org.antlr" % "ST4" % "4.3.1"),
    classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat,
    publishArtifact := false)
  .dependsOn(`cfg-typesafe`, `cfg-jackson-yaml`, `template-st4`)

lazy val root = project
  .in(file("."))
  .settings(
    name := "cfg",
    publishArtifact := false)
  .aggregate(
    core, examples,
    `cfg-typesafe`, `cfg-jackson`, `cfg-jackson-yaml`,
    `template-core`, `template-st4`)
