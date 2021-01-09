package borscht.template.impl.apache.commons.text.ph

import borscht.template.impl.apache.commons.text.{DefaultValueFormat, ValueFormat}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import java.util.Locale

class ParserTest extends AnyFlatSpec with Matchers:
  "Parser" should "return a correct placeholder for simplest case" in {
    parse("PHKey format=FormatString separator=SeparatorString null=NullValue", DefaultValueFormat) mustEqual
      ("PHKey",
        ValueFormat(
          format = Some("FormatString"),
          seqSeparator = "SeparatorString",
          productSeparator = "SeparatorString",
          nullValue = "NullValue"))
  }

  it should "return a correct placeholder for quoted key" in {
    parse("\"Quoted Key\" format=FmtStr separator=SepStr null=NullValue", DefaultValueFormat) mustEqual
      ("Quoted Key",
        ValueFormat(
          format = Some("FmtStr"),
          seqSeparator = "SepStr",
          productSeparator = "SepStr",
          nullValue = "NullValue"))
  }

  it should "return a correct placeholder for quoted attribute values" in {
    parse("PHKey format=\"Format String\" separator=\"-\" null=\"???\"", DefaultValueFormat) mustEqual
      ("PHKey",
        ValueFormat(
          format = Some("Format String"),
          seqSeparator = "-",
          productSeparator = "-",
          nullValue = "???"))
  }

  it should "return a correct placeholder for quoted attribute keys" in {
    parse("\"Quoted Key\" \"format\"=%02d \"separator\"=, \"null\"=none", DefaultValueFormat) mustEqual
      ("Quoted Key",
        ValueFormat(format = Some("%02d"), seqSeparator = ",", productSeparator = ",", nullValue = "none"))
  }

  it should "return a correct placeholder for quoted characters" in {
    parse("\"Quoted\\nKey\" \"\\u0066\\u006f\\u0072\\u006D\\u0061\\u0074\"=%02d" +
      " \"separator\"=\"\\t\" \"null\"=\"\"", DefaultValueFormat) mustEqual
      ("Quoted\nKey",
        ValueFormat(format = Some("%02d"), seqSeparator = "\t", productSeparator = "\t", nullValue = ""))
  }

  it should "return a correct placeholder the single key" in {
    parse("PHKey", DefaultValueFormat) mustEqual ("PHKey", ValueFormat())
  }

  it should "return a correct placeholder the single quoted key" in {
    parse("\"Quoted Key\"", DefaultValueFormat) mustEqual ("Quoted Key", ValueFormat())
  }

  it should "parse all parameters" in {
    parse("\"Quoted Key\"" +
      " format=FormatString" +
      " separator=SeparatorString" +
      " seq.start=SeqStartString" +
      " seq.separator=SeqSeparatorString" +
      " seq.end=SeqEndString" +
      " product.start=ProductStartString" +
      " product.separator=ProductSeparatorString" +
      " product.end=ProductEndString" +
      " null=NullValue" +
      " locale=en_US", DefaultValueFormat) mustEqual
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

  it should "use default value format" in {
    val vf = ValueFormat(
      format = Some("DefaultFormatString"),
      seqStart = "DefaultSeqStartString",
      seqSeparator = "DefaultSeqSeparatorString",
      seqEnd = "DefaultSeqEndString",
      productStart = "DefaultProductStartString",
      productSeparator = "DefaultProductSeparatorString",
      productEnd = "DefaultProductEndString",
      nullValue = "DefaultNullValue",
      locale = Locale.JAPAN)
    parse("\"Quoted\\u0020Key\"", vf) mustEqual ("Quoted Key", vf)
  }
