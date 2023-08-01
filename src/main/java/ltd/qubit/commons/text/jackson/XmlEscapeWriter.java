////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.io.IOException;
import java.io.Writer;

import ltd.qubit.commons.text.CharArrayWrapper;
import ltd.qubit.commons.text.EscapeUtils;
import ltd.qubit.commons.text.translate.CharSequenceTranslator;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A {@link Writer} which escape the strings in XML and output the result to the
 * underlying writer.
 *
 * @author Haixing Hu
 */
public class XmlEscapeWriter extends Writer {

  private final CharSequenceTranslator translator = EscapeUtils.ESCAPE_XML11;
  private final char[] singleCharBuffer = new char[1];
  private final CharArrayWrapper singleCharSequence =
      new CharArrayWrapper(singleCharBuffer, 0, 1);
  private final Writer writer;

  public XmlEscapeWriter(final Writer writer) {
    this.writer = requireNonNull("writer", writer);
  }

  @Override
  public void write(final char[] buffer, final int off, final int len)
      throws IOException {
    final CharArrayWrapper seq = new CharArrayWrapper(buffer, off, off + len);
    translator.transform(seq, writer);
  }

  public void write(final int ch) throws IOException {
    singleCharBuffer[0] = (char) ch;
    translator.transform(singleCharSequence, writer);
  }

  @Override
  public void flush() throws IOException {
    writer.flush();
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }
}
