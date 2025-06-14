////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

/**
 * Default implementation of the {@link PropertiesPersister} interface. Follows
 * the native parsing of {@code java.util.Properties}.
 * <p>
 * Allows for reading from any Reader and writing to any Writer, for example to
 * specify a charset for a properties file. This is a capability that standard
 * {@code java.util.Properties} unfortunately lacked up until JDK 5: You were
 * only able to load files using the ISO-8859-1 charset there.
 * <p>
 * Loading from and storing to a stream delegates to {@code Properties.load} and
 * {@code Properties.store}, respectively, to be fully compatible with the
 * Unicode conversion as implemented by the JDK Properties class. As of JDK 6,
 * {@code Properties.load/store} is also used for readers/writers, effectively
 * turning this class into a plain backwards compatibility adapter.
 * <p>
 * The persistence code that works with Reader/Writer follows the JDK's parsing
 * strategy but does not implement Unicode conversion, because the Reader/Writer
 * should already apply proper decoding/encoding of characters. If you prefer to
 * escape unicode characters in your properties files, do <i>not</i> specify an
 * encoding for a Reader/Writer (like ReloadableResourceBundleMessageSource's
 * "defaultEncoding" and "fileEncodings" properties).
 * <p>
 * This class is a copy of
 * {@code org.springframework.util.DefaultPropertiesPersister} with slight
 * modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Sebastien Deleuze
 * @author Haixing Hu
 * @see java.util.Properties
 * @see java.util.Properties#load
 * @see java.util.Properties#store
 */
public class DefaultPropertiesPersister implements PropertiesPersister {

  /**
   * A convenient constant for a default {@code DefaultPropertiesPersister}
   * instance, as used in Spring's common resource support.
   */
  public static final DefaultPropertiesPersister INSTANCE = new DefaultPropertiesPersister();

  @Override
  public void load(final Properties props, final InputStream is) throws IOException {
    props.load(is);
  }

  @Override
  public void load(final Properties props, final Reader reader) throws IOException {
    props.load(reader);
  }

  @Override
  public void store(final Properties props, final OutputStream os, final String header)
      throws IOException {
    props.store(os, header);
  }

  @Override
  public void store(final Properties props, final Writer writer, final String header)
      throws IOException {
    props.store(writer, header);
  }

  @Override
  public void loadFromXml(final Properties props, final InputStream is)
      throws IOException {
    props.loadFromXML(is);
  }

  @Override
  public void storeToXml(final Properties props, final OutputStream os,
      final String header) throws IOException {
    props.storeToXML(os, header);
  }

  @Override
  public void storeToXml(final Properties props, final OutputStream os,
      final String header, final String encoding) throws IOException {
    props.storeToXML(os, header, encoding);
  }

}