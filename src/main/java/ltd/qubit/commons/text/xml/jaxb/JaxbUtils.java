////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import ltd.qubit.commons.lang.SystemUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Provide utility functions for JAXB serialization.
 *
 * @author Haixing Hu
 */
public class JaxbUtils {

  private static final ClassValue<JAXBContext> CONTEXT_CACHE = new ClassValue<>() {
    @Override
    protected JAXBContext computeValue(final Class<?> type) {
      try {
        return JAXBContext.newInstance(type);
      } catch (final JAXBException e) {
        throw new RuntimeException(e);
      }
    }
  };

  /**
   * Gets the JAXB context bounds to a specified class.
   *
   * <p>Note that the JAXB context is thread-safe.
   *
   * @param cls
   *     the class to bound the JAXB context.
   * @return the JAXB context bounds to a specified class.
   * @see <a href="https://javaee.github.io/jaxb-v2/doc/user-guide/ch06.html">
   *     JAXB 2.0 Frequently Asked Questions: Are the JAXB runtime API's thread
   *     safe</a>
   */
  private static JAXBContext getContext(final Class<?> cls) {
    return CONTEXT_CACHE.get(cls);
  }

  /**
   * Unmarshals an objects from an XML file.
   *
   * @param <T>
   *     the type of the objects to be loaded.
   * @param resource
   *     the name of the resource of an XML file.
   * @param cls
   *     the class of the objects to be loaded.
   * @return the objects deserialized from the XML file.
   * @throws JAXBException
   *     if any error occurs.
   */
  public static <T> T unmarshal(final String resource, final Class<T> cls)
      throws JAXBException {
    final URL url = SystemUtils.getResource(resource, cls);
    if (url == null) {
      throw new JAXBException("Failed to load the resource: " + resource);
    }
    return unmarshal(url, cls);
  }

  /**
   * Unmarshals an objects from an XML file.
   *
   * @param <T>
   *     the type of the objects to be loaded.
   * @param url
   *     the URL of an XML file.
   * @param cls
   *     the class of the objects to be loaded.
   * @return the objects deserialized from the XML file.
   * @throws JAXBException
   *     if any error occurs.
   */
  public static <T> T unmarshal(final URL url, final Class<T> cls)
      throws JAXBException {
    try (final InputStream in = url.openStream()) {
      return unmarshal(in, cls);
    } catch (final IOException e) {
      throw new JAXBException(e);
    }
  }

  /**
   * Unmarshals an objects from an XML file.
   *
   * @param <T>
   *     the type of the objects to be loaded.
   * @param in
   *     the input stream where to read the XML snippets.
   * @param cls
   *     the class of the objects to be loaded.
   * @return the objects deserialized from the XML file.
   * @throws JAXBException
   *     if any error occurs.
   */
  public static <T> T unmarshal(final InputStream in, final Class<T> cls)
      throws JAXBException {
    final InputStreamReader reader = new InputStreamReader(in, UTF_8);
    return unmarshal(reader, cls);
  }

  /**
   * Unmarshals an objects from an XML file.
   *
   * @param <T>
   *     the type of the objects to be loaded.
   * @param reader
   *     the reader where to read the XML snippets.
   * @param cls
   *     the class of the objects to be loaded.
   * @return the objects unmarshaled from the XML file.
   * @throws JAXBException
   *     if any error occurs.
   */
  @SuppressWarnings("unchecked")
  public static <T> T unmarshal(final Reader reader, final Class<T> cls)
      throws JAXBException {
    final JAXBContext context = getContext(cls);
    final Unmarshaller umr = context.createUnmarshaller();
    final StreamSource source = new StreamSource(reader);
    return (T) umr.unmarshal(source);
  }

  /**
   * Unmarshals a list of objects from an XML file.
   *
   * @param <T>
   *     the type of the objects to be loaded.
   * @param resource
   *     the name of the resource of an XML file.
   * @param cls
   *     the class of the objects to be loaded.
   * @return the list of objects deserialized from the XML file.
   * @throws JAXBException
   *     if any error occurs.
   */
  public static <T> List<T> unmarshalList(final String resource,
      final Class<T> cls)
      throws JAXBException {
    final URL url = SystemUtils.getResource(resource, cls);
    if (url == null) {
      throw new JAXBException("Failed to load the resource: " + resource);
    }
    return unmarshalList(url, cls);
  }

  /**
   * Unmarshals a list of objects from an XML file.
   *
   * @param <T>
   *     the type of the objects to be loaded.
   * @param url
   *     the URL of an XML file.
   * @param cls
   *     the class of the objects to be loaded.
   * @return the list of objects deserialized from the XML file.
   * @throws JAXBException
   *     if any error occurs.
   */
  public static <T> List<T> unmarshalList(final URL url, final Class<T> cls)
      throws JAXBException {
    try (final InputStream in = url.openStream()) {
      return unmarshalList(in, cls);
    } catch (final IOException e) {
      throw new JAXBException(e);
    }
  }

  /**
   * Unmarshals a list of objects from an XML file.
   *
   * @param <T>
   *     the type of the objects to be loaded.
   * @param in
   *     the input stream of an XML file.
   * @param cls
   *     the class of the objects to be loaded.
   * @return the list of objects deserialized from the XML file.
   * @throws JAXBException
   *     if any error occurs.
   */
  public static <T> List<T> unmarshalList(final InputStream in,
      final Class<T> cls)
      throws JAXBException {
    final InputStreamReader reader = new InputStreamReader(in, UTF_8);
    return unmarshalList(reader, cls);
  }

  /**
   * Unmarshals a list of objects from an XML file.
   *
   * @param <T>
   *     the type of the objects to be loaded.
   * @param reader
   *     the reader where to read the XML snippets.
   * @param cls
   *     the class of the objects to be loaded.
   * @return the list of objects deserialized from the XML file.
   * @throws JAXBException
   *     if any error occurs.
   */
  @SuppressWarnings("unchecked")
  public static <T> List<T> unmarshalList(final Reader reader,
      final Class<T> cls)
      throws JAXBException {
    final JAXBContext context = JAXBContext
        .newInstance(JaxbListWrapper.class, cls);
    final Unmarshaller umr = context.createUnmarshaller();
    final StreamSource source = new StreamSource(reader);
    final JaxbListWrapper<T> wrapper = umr.unmarshal(source, JaxbListWrapper.class).getValue();
    return wrapper.getList();
  }

  /**
   * Stores an objects to an XML document.
   *
   * <p><b>NOTE: </b> this function will <b>not</b> close the writer. The
   * caller must close it by himself.
   *
   * @param <T>
   *     the type of the objects to be stored.
   * @param obj
   *     the object to be stored.
   * @param cls
   *     the class of the object to be stored.
   * @return the string of the XML fragment.
   * @throws JAXBException
   *     if any error occurs.
   */
  public static <T> String marshal(final T obj, final Class<T> cls)
      throws JAXBException {
    final StringWriter writer = new StringWriter();
    marshal(obj, cls, writer);
    return writer.toString();
  }

  /**
   * Stores an objects to an XML document.
   *
   * <p><b>NOTE: </b> this function will <b>not</b> close the writer. The
   * caller must close it by himself.
   *
   * @param <T>
   *     the type of the objects to be stored.
   * @param obj
   *     the object to be stored.
   * @param cls
   *     the class of the object to be stored.
   * @param writer
   *     an writer where to write the marshaled XML.
   * @throws JAXBException
   *     if any error occurs.
   */
  public static <T> void marshal(final T obj, final Class<T> cls,
      final Writer writer) throws JAXBException {
    final JAXBContext context = getContext(cls);
    final Marshaller mr = context.createMarshaller();
    mr.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
    mr.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    mr.marshal(obj, writer);
  }

  /**
   * Stores an objects to an XML document.
   *
   * <p><b>NOTE: </b> this function will <b>not</b> close the writer. The
   * caller must close it by himself.
   *
   * @param <T>
   *     the type of the objects to be stored.
   * @param obj
   *     the object to be stored.
   * @param cls
   *     the class of the object to be stored.
   * @param out
   *     an print stream where to print the marshaled XML.
   * @throws JAXBException
   *     if any error occurs.
   */
  public static <T> void marshal(
      final T obj, final Class<T> cls, final PrintStream out)
      throws JAXBException {
    final String xml = marshal(obj, cls);
    out.println(xml);
  }

  /**
   * Stores a list of objects to an XML document.
   *
   * <p><b>NOTE: </b> this function will <b>not</b> close the output stream.
   * The caller must close it by himself.
   *
   * @param <T>
   *     the type of the objects to be stored.
   * @param list
   *     the list of objects to be stored.
   * @param cls
   *     the class of the objects to be stored.
   * @param rootName
   *     the name of the root element of the stored XML document.
   * @param out
   *     an output stream where to write the marshaled XML.
   * @throws JAXBException
   *     if any error occurs.
   */
  public static <T> void marshalList(final List<T> list, final Class<T> cls,
      final String rootName,
      final OutputStream out) throws JAXBException {
    final OutputStreamWriter writer = new OutputStreamWriter(out, UTF_8);
    marshalList(list, cls, rootName, writer);
  }

  /**
   * Stores a list of objects to an XML document.
   *
   * <p><b>NOTE: </b> this function will <b>not</b> close the writer. The
   * caller must close it by himself.
   *
   * @param <T>
   *     the type of the objects to be stored.
   * @param list
   *     the list of objects to be stored.
   * @param cls
   *     the class of the objects to be stored.
   * @param rootName
   *     the name of the root element of the stored XML document.
   * @param writer
   *     an writer where to write the marshaled XML.
   * @throws JAXBException
   *     if any error occurs.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public static <T> void marshalList(final List<T> list, final Class<T> cls,
      final String rootName, final Writer writer) throws JAXBException {
    final QName qname = new QName(rootName);
    final JaxbListWrapper<T> wrapper = new JaxbListWrapper<>(list);
    final JAXBElement<JaxbListWrapper<T>> element = new JAXBElement(qname,
        JaxbListWrapper.class, wrapper);
    final JAXBContext context = JAXBContext.newInstance(JaxbListWrapper.class, cls);
    final Marshaller mr = context.createMarshaller();
    mr.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    mr.marshal(element, writer);
  }
}
