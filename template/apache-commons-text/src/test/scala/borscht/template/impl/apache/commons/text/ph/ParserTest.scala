package borscht.template.impl.apache.commons.text.ph

import borscht.template.impl.apache.commons.text.Placeholder
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import javax.swing.JToolBar.Separator

class ParserTest extends AnyFlatSpec with Matchers {
  "Parser" should "return a correct placeholder for simplest case" in {
    Parser("PHKey format=FormatString separator=SeparatorString null=NullValue")() mustEqual
      Placeholder("PHKey",
        format = Some("FormatString"),
        separator = Some("SeparatorString"),
        nullValue = Some("NullValue"))
  }

  it should "return a correct placeholder for quoted key" in {
    Parser("\"Quoted Key\" format=FormatString separator=SeparatorString null=NullValue")() mustEqual
      Placeholder("Quoted Key",
        format = Some("FormatString"),
        separator = Some("SeparatorString"),
        nullValue = Some("NullValue"))
  }

  it should "return a correct placeholder for quoted attribute values" in {
    Parser("PHKey format=\"Format String\" separator=\"Separator String\" null=\"Null Value\"")() mustEqual
      Placeholder("PHKey",
        format = Some("Format String"),
        separator = Some("Separator String"),
        nullValue = Some("Null Value"))
  }

  it should "return a correct placeholder for quoted attribute keys" in {
    Parser("\"Quoted Key\" \"format\"=%02d \"separator\"=, \"null\"=null")() mustEqual
      Placeholder("Quoted Key",
        format = Some("%02d"),
        separator = Some(","),
        nullValue = Some("null"))
  }

  it should "return a correct placeholder for quoted characters" in {
    Parser("\"Quoted\\nKey\" \"\\u0066\\u006f\\u0072\\u006D\\u0061\\u0074\"=%02d" +
      " \"separator\"=\"\\t\" \"null\"=\"\"")() mustEqual
      Placeholder("Quoted\nKey",
        format = Some("%02d"),
        separator = Some("\t"),
        nullValue = Some(""))
  }

  it should "return a correct placeholder the single key" in {
    Parser("PHKey")() mustEqual Placeholder("PHKey")
  }

  it should "return a correct placeholder the single quoted key" in {
    Parser("\"Quoted Key\"")() mustEqual Placeholder("Quoted Key")
  }
}
