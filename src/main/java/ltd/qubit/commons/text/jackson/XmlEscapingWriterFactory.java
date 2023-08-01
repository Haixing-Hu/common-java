////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import org.codehaus.stax2.io.EscapingWriterFactory;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import javax.annotation.concurrent.Immutable;

/**
 * 用于实现XML转义的{@link Writer}的工厂方法。
 *
 * @author 胡海星
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
