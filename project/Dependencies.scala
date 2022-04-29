import sbt.*

object Dependencies {
  val TypesafeConfig = "com.typesafe" % "config" % "1.4.2"

  private val JacksonVersion = "2.13.2"
  val JacksonDatabind = "com.fasterxml.jackson.core" % "jackson-databind" % (JacksonVersion + ".2")
  val JacksonDataformatYAML = "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % JacksonVersion
  val JacksonDataformatTOML = "com.fasterxml.jackson.dataformat" % "jackson-dataformat-toml" % JacksonVersion

  val ST4 = "org.antlr" % "ST4" % "4.3.3"

  val ApacheCommonsText = "org.apache.commons" % "commons-text" % "1.9"

  val ScalaTest = "org.scalatest" %% "scalatest" % "3.2.12"
}
