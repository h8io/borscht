package borscht.template.impl.st4

import java.time.format.DateTimeFormatter

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class RendererTest extends AnyFlatSpec with Matchers:
  private val parser = ST4TemplateParser()
  
  "Temporal accessor renderer" should "render date and time with format string" in {
    val date = DateTimeFormatter.ISO_DATE_TIME.parse("1961-04-12T09:07:00+03:00[Europe/Moscow]")
    parser("Poyekhali: <date; format=\"dd.MM.yyyy HH:mm\">").apply("date" -> date) mustEqual
      s"Poyekhali: 12.04.1961 09:07"
  }

  it should "render date and time without format string" in {
    val date = DateTimeFormatter.ISO_DATE_TIME.parse("1961-04-12T08:05:00+00:00")
    parser("Landing: <date>").apply("date" -> date) mustEqual s"Landing: 1961-04-12T08:05:00Z"
  }
