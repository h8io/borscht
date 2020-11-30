ThisBuild / organization := "h8.io"
ThisBuild / scalaVersion := "3.0.0-M1"

ThisBuild / libraryDependencies ++= Seq(
    // "org.scalamock" %% "scalamock" % "5.0.0" % Test,
    "org.scalatest" %% "scalatest" % "3.2.3" % Test)

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
  .settings(name := "borscht-template-core")
  .dependsOn(core)

lazy val `template-st4` = project
  .in(file("template/st4"))
  .settings(
      name := "borscht-template-core",
      libraryDependencies += "org.antlr" % "ST4" % "4.3.1")
  .dependsOn(`template-core`)

lazy val examples = project
  .in(file("examples"))
  .settings(
    name := "borscht-examples",
    libraryDependencies += "org.antlr" % "ST4" % "4.3.1", // for GitHub Scala CI
    publishArtifact := false)
  .dependsOn(`cfg-typesafe`, `template-st4`)

lazy val root = project
  .in(file("."))
  .settings(
    name := "borscht",
    publishArtifact := false)
  .aggregate(
    core, examples,
    `cfg-typesafe`,
    `template-core`, `template-st4`)
