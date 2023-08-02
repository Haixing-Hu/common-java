////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.jupiter.api.Test;

import com.google.common.base.Ascii;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvTranslatorsTest {

  @Test
  public void csvEscaperCommaTest() throws IOException {
    final CsvEscaper escaper = new CsvEscaper();
    final Writer writer = new StringWriter();
    final String input = "hi,this,is,a,test";
    escaper.translateWhole(input, writer);
    final String data = writer.toString();
    assertThat(data).isEqualTo("\"hi,this,is,a,test\"");
  }

  @Test
  public void csvEscaperCRTest() throws IOException {
    final CsvEscaper escaper = new CsvEscaper();
    final Writer writer = new StringWriter();
    final String input = "hi,this,is,a,CR,test" + String.valueOf(Ascii.CR);
    escaper.translateWhole(input, writer);
    final String data = writer.toString();
    assertThat(data).isEqualTo("\"hi,this,is,a,CR,test" + String.valueOf(Ascii.CR) + "\"");
  }

  @Test
  public void csvEscaperLFTest() throws IOException {
    final CsvEscaper escaper = new CsvEscaper();
    final Writer writer = new StringWriter();
    final String input = "hi,this,is,a,LF,test" + String.valueOf(Ascii.LF);
    escaper.translateWhole(input, writer);
    final String data = writer.toString();
    assertThat(data).isEqualTo("\"hi,this,is,a,LF,test" + String.valueOf(Ascii.LF) + "\"");
  }

  @Test
  public void csvEscaperPlaneTextTest() throws IOException {
    final CsvEscaper escaper = new CsvEscaper();
    final Writer writer = new StringWriter();
    final String input = "hi this is just a plane text nothing to do with csv!";
    escaper.translateWhole(input, writer);
    final String data = writer.toString();
    assertThat(input).isEqualTo(data);
  }

  @Test
  public void csvEscaperQuoteTest() throws IOException {
    final CsvEscaper escaper = new CsvEscaper();
    final Writer writer = new StringWriter();
    final String input = "hi,this,is,a,\"quote,test";
    escaper.translateWhole(input, writer);
    final String data = writer.toString();
    assertThat(data).isEqualTo("\"hi,this,is,a,\"\"quote,test\"");
  }

  @Test
  public void csvUnEscaperPlaneTextTest() throws IOException {
    final CsvUnescaper escaper = new CsvUnescaper();
    final Writer writer = new StringWriter();
    final String input = "hi,this,is,unescape,test";
    escaper.translateWhole(input, writer);
    final String data = writer.toString();
    assertThat(data).isEqualTo("hi,this,is,unescape,test");
  }

  @Test
  public void csvUnEscaperTest1() throws IOException {
    final CsvUnescaper escaper = new CsvUnescaper();
    final Writer writer = new StringWriter();
    final String input = "\"hi,this,is,unescape,test\"";
    escaper.translateWhole(input, writer);
    final String data = writer.toString();
    assertThat(data).isEqualTo("hi,this,is,unescape,test");
  }

  @Test
  public void csvUnEscaperTest2() throws IOException {
    final CsvUnescaper escaper = new CsvUnescaper();
    final Writer writer = new StringWriter();
    final String input = "\"hi,this,is,unescape,test";
    escaper.translateWhole(input, writer);
    final String data = writer.toString();
    assertThat(input).isEqualTo(data);
  }

  @Test
  public void csvUnEscaperTest3() throws IOException {
    final CsvUnescaper escaper = new CsvUnescaper();
    final Writer writer = new StringWriter();
    final String input = "hi,this,is,unescape,test\"";
    escaper.translateWhole(input, writer);
    final String data = writer.toString();
    assertThat(input).isEqualTo(data);
  }

  @Test
  public void csvUnEscaperTest4() throws IOException {
    final CsvUnescaper escaper = new CsvUnescaper();
    final Writer writer = new StringWriter();
    final String input = "\"hi,this,is,\"unescape,test\"";
    escaper.translateWhole(input, writer);
    final String data = writer.toString();
    assertThat(data).isEqualTo("hi,this,is,\"unescape,test");
  }
}
