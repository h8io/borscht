import sbt._

object Dependencies {
  val TypesafeConfig = "com.typesafe" % "config" % "1.4.1"

  private val JacksonVersion = "2.13.0"
  val JacksonDatabind = "com.fasterxml.jackson.core" % "jackson-databind" % JacksonVersion
  val JacksonDataformatYAML = "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % JacksonVersion

  val ST4 = "org.antlr" % "ST4" % "4.3.1"

  val ApacheCommonsText = "org.apache.commons" % "commons-text" % "1.9"

  val ScalaTest = "org.scalatest" %% "scalatest" % "3.2.9"

  val ScalaMock = "org.scalamock" %% "scalamock" % "5.1.0"
}
