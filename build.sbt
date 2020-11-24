ThisBuild / organization := "h8.io"
ThisBuild / scalaVersion := "3.0.0-M1"

ThisBuild / libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.3" % Test

lazy val core = project
  .in(file("core"))
  .settings(name := "borscht-core")

lazy val `cfg-typesafe` = project
  .in(file("cfg/typesafe"))
  .settings(
    name := "borscht-cfg-typesafe",

    libraryDependencies += "com.typesafe" % "config" % "1.4.0")
  .dependsOn(core)

lazy val `template-core` = project
  .in(file("template/core"))
  .settings(
    name := "borscht-template-core",
    // Move it to implementation later
    libraryDependencies += "org.antlr" % "ST4" % "4.3.1")
  .dependsOn(core)

lazy val tests = project
  .in(file("tests"))
  .settings(
    name := "borscht-tests",
    publishArtifact := false)
  .dependsOn(core, `cfg-typesafe`)

lazy val root = project
  .in(file("."))
  .settings(
    name := "borscht",
    publishArtifact := false)
  .aggregate(core, `cfg-typesafe`, `template-core`, tests)
