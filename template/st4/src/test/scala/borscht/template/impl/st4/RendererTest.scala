package borscht.template.impl.st4

import borscht.template.impl.st4.renderers.TemporalAccessorRenderer
import borscht.test.{cfg, scalar, seq}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalTime, OffsetTime}

class RendererTest extends AnyFlatSpec with Matchers:
  private val parser = ST4TemplateEngine(cfg("renderers" -> seq(classOf[TemporalAccessorRenderer].getName)))
  private val customParser = ST4TemplateEngine(cfg("renderers" -> seq(
    cfg("class" -> classOf[TemporalAccessorRenderer].getName,
      "parameters" -> cfg("formats" -> cfg(
        "date" -> "dd.MM.yyyy",
        "time" -> "HH-mm-ss",
        "datetime" -> "HH:mm MM/dd/yyyy"))))))

  "Default temporal accessor renderer" should "render date and time with format string" in {
    val date = DateTimeFormatter.ISO_DATE_TIME.parse("1961-04-12T09:07:00+03:00[Europe/Moscow]")
    parser(scalar("Poyekhali: <date; format=\"dd.MM.yyyy HH:mm\">")).set("date" -> date).render mustEqual
      s"Poyekhali: 12.04.1961 09:07"
  }

  it should "render date and time without format string" in {
    val date = DateTimeFormatter.ISO_DATE_TIME.parse("1961-04-12T08:05:00+00:00")
    parser(scalar("Landing: <date>")).set("date" -> date).render mustEqual s"Landing: 1961-04-12T08:05:00Z"
  }

  it should "render date without format string" in {
    val date = LocalDate.from(DateTimeFormatter.ISO_DATE.parse("1961-04-12"))
    parser(scalar("Landing: <date>")).set("date" -> date).render mustEqual s"Landing: 1961-04-12"
  }

  it should "render time without format string" in {
    val time = LocalTime.from(DateTimeFormatter.ISO_TIME.parse("08:05:00Z"))
    parser(scalar("Landing: <time>")).set("time" -> time).render mustEqual s"Landing: 08:05:00"
  }

  "Custom temporal accessor renderer" should "render date and time with format string" in {
    val date = DateTimeFormatter.ISO_DATE_TIME.parse("1961-04-12T09:07:00+03:00[Europe/Moscow]")
    customParser(scalar("Poyekhali: <date; format=\"dd.MM.yyyy HH:mm\">")).set("date" -> date).render mustEqual
      s"Poyekhali: 12.04.1961 09:07"
  }

  it should "render date and time without format string" in {
    val date = DateTimeFormatter.ISO_DATE_TIME.parse("1961-04-12T08:05:00+00:00")
    customParser(scalar("Landing: <date>")).set("date" -> date).render mustEqual s"Landing: 08:05 04/12/1961"
  }

  it should "render date without format string" in {
    val date = LocalDate.from(DateTimeFormatter.ISO_DATE.parse("1961-04-12"))
    customParser(scalar("Landing: <date>")).set("date" -> date).render mustEqual s"Landing: 12.04.1961"
  }

  it should "render time without format string" in {
    val time = OffsetTime.from(DateTimeFormatter.ISO_TIME.parse("08:05:00Z"))
    customParser(scalar("Landing: <time>")).set("time" -> time).render mustEqual s"Landing: 08-05-00"
  }
