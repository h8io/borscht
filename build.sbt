ThisBuild / organization := "io.h8"
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
    id    = "eshu",
    name  = "Pavel Parkhomenko",
    email = "tjano.xibalba@gmail.com",
    url   = url("https://github.com/h8io/")
  )
)

ThisBuild / description := "Borscht: Scala 3 configuration liblrary"
ThisBuild / licenses := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / homepage := Some(url("https://github.com/h8io/borscht"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://s01.oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true
ThisBuild / versionScheme := Some("semver-spec")

ThisBuild / scalaVersion := "3.0.0-RC3"

ThisBuild / libraryDependencies ++= Seq(
  //Dependencies.ScalaMock % Test,
  Dependencies.ScalaTest % Test)

ThisBuild / scalacOptions ++= Seq(
  //"-Yexplicit-nulls",
  "-Ysafe-init")

lazy val core = project
  .in(file("core"))
  .settings(name := "borscht-core")

lazy val `provider-typesafe` = project
  .in(file("provider/typesafe"))
  .settings(
    name := "borscht-typesafe",
    libraryDependencies += Dependencies.TypesafeConfig)
  .dependsOn(core)

lazy val `provider-jackson` = project
  .in(file("provider/jackson"))
  .settings(
    name := "borscht-jackson",
    libraryDependencies += Dependencies.JacksonDatabind)
  .dependsOn(core)

lazy val `provider-jackson-yaml` = project
  .in(file("provider/jackson/yaml"))
  .settings(
    name := "borscht-jackson-yaml",
    libraryDependencies += Dependencies.JacksonDataformatYAML)
  .dependsOn(`provider-jackson`)

lazy val `typed-core` = project
  .in(file("typed/core"))
  .settings(
    name := "borscht-typed-core")
  .dependsOn(`core`, `provider-typesafe` % "test -> compile")

lazy val `template-core` = project
  .in(file("template/core"))
  .settings(name := "borscht-template-core")
  .dependsOn(`core`, `typed-core`)

lazy val `template-st4` = project
  .in(file("template/st4"))
  .settings(
    name := "borscht-template-st4",
    libraryDependencies += Dependencies.ST4)
  .dependsOn(`template-core`, util)

lazy val `template-apache-commons-text` = project
  .in(file("template/apache-commons-text"))
  .settings(
    name := "borscht-template-apache-commons-text",
    libraryDependencies += Dependencies.ApacheCommonsText)
  .dependsOn(`template-core`, util)

lazy val util = project
  .in(file("util"))
  .settings(name := "borscht-util")

lazy val examples = project
  .in(file("examples"))
  .settings(
    name := "borscht-examples",
    libraryDependencies ++= Seq( // for GitHub Scala CI
      Dependencies.JacksonDataformatYAML,
      Dependencies.ST4,
      Dependencies.ApacheCommonsText),
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
    `typed-core`,
    `template-core`, `template-st4`, `template-apache-commons-text`)
