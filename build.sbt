ThisBuild / organization := "h8.io"
ThisBuild / scalaVersion := "0.27.0-RC1"
//ThisBuild / scalaVersion := "3.0.0-M1"

ThisBuild / libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % Test

lazy val core = project
  .in(file("core"))
  .settings(name := "borscht-core")

lazy val typesafe = project
  .in(file("typesafe"))
  .settings(
    name := "borscht-typesafe",

    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.4.0",
      "org.antlr" % "ST4" % "4.3.1"))
  .dependsOn(core)

lazy val root = project
  .in(file("."))
  .settings(publishArtifact := false)
  .aggregate(core, typesafe)
