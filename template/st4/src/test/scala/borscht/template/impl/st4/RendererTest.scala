package borscht.template.impl.st4

import borscht.test.{cfg, scalar, seq}
import borscht.template.impl.st4.renderers.TemporalAccessorRenderer
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import java.time.format.DateTimeFormatter

class RendererTest extends AnyFlatSpec with Matchers:
  private val parser = ST4TemplateEngine(cfg("renderers" -> seq(classOf[TemporalAccessorRenderer].getName)))
  
  "Temporal accessor renderer" should "render date and time with format string" in {
    val date = DateTimeFormatter.ISO_DATE_TIME.parse("1961-04-12T09:07:00+03:00[Europe/Moscow]")
    parser(scalar("Poyekhali: <date; format=\"dd.MM.yyyy HH:mm\">")).set("date" -> date).render mustEqual
      s"Poyekhali: 12.04.1961 09:07"
  }

  it should "render date and time without format string" in {
    val date = DateTimeFormatter.ISO_DATE_TIME.parse("1961-04-12T08:05:00+00:00")
    parser(scalar("Landing: <date>")).set("date" -> date).render mustEqual s"Landing: 1961-04-12T08:05:00Z"
  }
