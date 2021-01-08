package borscht.template.impl.apache.commons.text.ph

import borscht.template.impl.apache.commons.text.ValueFormat
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import java.util.Locale
import javax.swing.JToolBar.Separator

class ParserTest extends AnyFlatSpec with Matchers:
  "Parser" should "return a correct placeholder for simplest case" in {
    Parser("PHKey format=FormatString separator=SeparatorString null=NullValue")() mustEqual
      ("PHKey",
        ValueFormat(
          format = Some("FormatString"),
          seqSeparator = "SeparatorString",
          productSeparator = "SeparatorString",
          nullValue = "NullValue"))
  }

  it should "return a correct placeholder for quoted key" in {
    Parser("\"Quoted Key\" format=FormatString separator=SeparatorString null=NullValue")() mustEqual
      ("Quoted Key",
        ValueFormat(
          format = Some("FormatString"),
          seqSeparator = "SeparatorString",
          productSeparator = "SeparatorString",
          nullValue = "NullValue"))
  }

  it should "return a correct placeholder for quoted attribute values" in {
    Parser("PHKey format=\"Format String\" separator=\"Separator String\" null=\"???\"")() mustEqual
      ("PHKey",
        ValueFormat(
          format = Some("Format String"),
          seqSeparator = "Separator String",
          productSeparator = "Separator String",
          nullValue = "???"))
  }

  it should "return a correct placeholder for quoted attribute keys" in {
    Parser("\"Quoted Key\" \"format\"=%02d \"separator\"=, \"null\"=none")() mustEqual
      ("Quoted Key",
        ValueFormat(format = Some("%02d"), seqSeparator = ",", productSeparator = ",", nullValue = "none"))
  }

  it should "return a correct placeholder for quoted characters" in {
    Parser("\"Quoted\\nKey\" \"\\u0066\\u006f\\u0072\\u006D\\u0061\\u0074\"=%02d" +
      " \"separator\"=\"\\t\" \"null\"=\"\"")() mustEqual
      ("Quoted\nKey",
        ValueFormat(format = Some("%02d"), seqSeparator = "\t", productSeparator = "\t", nullValue = ""))
  }

  it should "return a correct placeholder the single key" in {
    Parser("PHKey")() mustEqual ("PHKey", ValueFormat())
  }

  it should "return a correct placeholder the single quoted key" in {
    Parser("\"Quoted Key\"")() mustEqual ("Quoted Key", ValueFormat())
  }

  it should "parse all parameters" in {
    Parser("\"Quoted Key\"" +
      " format=FormatString" +
      " separator=SeparatorString" +
      " seq.start=SeqStartString" +
      " seq.separator=SeqSeparatorString" +
      " seq.end=SeqEndString" +
      " product.start=ProductStartString" +
      " product.separator=ProductSeparatorString" +
      " product.end=ProductEndString" +
      " null=NullValue" +
      " locale=en_US")() mustEqual
      ("Quoted Key",
        ValueFormat(
          format = Some("FormatString"),
          seqStart = "SeqStartString",
          seqSeparator = "SeqSeparatorString",
          seqEnd = "SeqEndString",
          productStart = "ProductStartString",
          productSeparator = "ProductSeparatorString",
          productEnd = "ProductEndString",
          nullValue = "NullValue",
          locale = Locale.US))
  }