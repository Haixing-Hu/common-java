////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import org.codehaus.stax2.io.EscapingWriterFactory;

import javax.annotation.concurrent.Immutable;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * 用于实现XML转义的{@link Writer}的工厂方法。
 *
 * @author Haixing Hu
 */
@Immutable
public class XmlEscapingWriterFactory implements EscapingWriterFactory {

  public static final XmlEscapingWriterFactory INSTANCE = new XmlEscapingWriterFactory();

  @Override
  public Writer createEscapingWriterFor(final Writer writer, final String enc)
      throws UnsupportedEncodingException {
    return new XmlEscapeWriter(writer);
  }

  @Override
  public Writer createEscapingWriterFor(final OutputStream out, final String enc)
      throws UnsupportedEncodingException {
    return new XmlEscapeWriter(new OutputStreamWriter(out, enc));
  }
}
