import sbt.Test

ThisBuild / organization := "io.h8.borscht"
ThisBuild / organizationName := "H8IO"
ThisBuild / organizationHomepage := Some(url("https://github.com/h8io/"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/h8io/borscht"),
    "scm:git@github.com:h8io/borscht.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id = "eshu",
    name = "Pavel Parkhomenko",
    email = "tjano.xibalba@gmail.com",
    url = url("https://github.com/h8io/")
  )
)

ThisBuild / description := "Borscht: Scala 3 configuration liblrary"
ThisBuild / licenses := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / homepage := Some(url("https://github.com/h8io/borscht"))
ThisBuild / versionScheme := Some("semver-spec")

// Remove all additional repository other than Maven Central from POM
pomIncludeRepository := { _ => false }
sonatypeProfileName := "io.h8"
sonatypeCredentialHost := "s01.oss.sonatype.org"
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"
ThisBuild / publishTo := sonatypePublishToBundle.value

import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  // For non cross-build projects, use releaseStepCommand("publishSigned")
  releaseStepCommandAndRemaining("+publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

ThisBuild / scalaVersion := "3.0.2"

ThisBuild / libraryDependencies ++= Seq(
  //Dependencies.ScalaMock % Test,
  Dependencies.ScalaTest % Test)

ThisBuild / scalacOptions ++= Seq(
  //"-Yexplicit-nulls",
  "-Ysafe-init")

ThisBuild / javacOptions += "-parameters"

lazy val core = project
  .in(file("core"))
  .settings(name := "borscht-core")

lazy val `test-core`: Project = project
  .in(file("test"))
  .settings(publishArtifact := false)
  .dependsOn(core)
  .aggregate(core)

lazy val `recipe-typesafe` = project
  .in(file("recipe/typesafe"))
  .settings(
    name := "borscht-typesafe",
    libraryDependencies += Dependencies.TypesafeConfig)
  .dependsOn(core)

lazy val `recipe-jackson` = project
  .in(file("recipe/jackson"))
  .settings(
    name := "borscht-jackson",
    libraryDependencies += Dependencies.JacksonDatabind)
  .dependsOn(core)

lazy val `recipe-jackson-yaml` = project
  .in(file("recipe/jackson/yaml"))
  .settings(
    name := "borscht-jackson-yaml",
    libraryDependencies += Dependencies.JacksonDataformatYAML)
  .dependsOn(`recipe-jackson`)

lazy val `template-core` = project
  .in(file("template/core"))
  .settings(name := "borscht-template-core")
  .dependsOn(core, `test-core` % "test -> compile")

lazy val `template-st4` = project
  .in(file("template/st4"))
  .settings(
    name := "borscht-template-st4",
    libraryDependencies += Dependencies.ST4)
  .dependsOn(`template-core`, `test-core` % "test -> compile")

lazy val `template-apache-commons-text` = project
  .in(file("template/apache-commons-text"))
  .settings(
    name := "borscht-template-apache-commons-text",
    libraryDependencies += Dependencies.ApacheCommonsText)
  .dependsOn(`template-core`, `test-core` % "test -> compile")

lazy val examples = project
  .in(file("examples"))
  .settings(
    name := "borscht-examples",
    libraryDependencies ++= Seq( // for GitHub Scala CI
      Dependencies.JacksonDataformatYAML,
      Dependencies.ST4,
      Dependencies.ApacheCommonsText),
    publishArtifact := false)
  .dependsOn(`recipe-typesafe`, `recipe-jackson-yaml`, `template-st4`, `template-apache-commons-text`)

lazy val classic = project
  .in(file("classic"))
  .settings(
    name := "borscht-classic")
  .dependsOn(
    `recipe-typesafe`, `template-st4`
  )

lazy val root = project
  .in(file("."))
  .settings(
    name := "borscht",
    publishArtifact := false)
  .aggregate(
    core, `test-core`,
    `recipe-typesafe`, `recipe-jackson`, `recipe-jackson-yaml`,
    `template-core`, `template-st4`, `template-apache-commons-text`,
    examples, classic)
