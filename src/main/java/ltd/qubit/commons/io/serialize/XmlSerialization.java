////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.serialize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.io.IoUtils;
import ltd.qubit.commons.lang.ClassKey;
import ltd.qubit.commons.net.Url;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlSerializationException;
import ltd.qubit.commons.text.xml.XmlUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 提供管理{@link XmlSerializer}的函数，以及帮助序列化和反序列化对象到XML DOM树以及从XML
 * DOM树反序列化和序列化对象的函数。
 *
 * @author 胡海星
 */
public class XmlSerialization {

  private static final Logger LOGGER = LoggerFactory.getLogger(XmlSerialization.class);

  private static final Map<ClassKey, XmlSerializer> REGISTRY = new ConcurrentHashMap<>();

  /**
   * 为指定的类注册一个XML序列化器。
   *
   * @param objClass
   *     类对象。
   * @param serializer
   *     为指定类构造的XML序列化器。
   */
  public static void register(final Class<?> objClass,
      final XmlSerializer serializer) {
    requireNonNull("objClass", objClass);
    requireNonNull("serializer", serializer);
    LOGGER.debug("Registering an XML serializer for class {}.", objClass);
    REGISTRY.put(new ClassKey(objClass), serializer);
  }

  /**
   * 获取为指定类注册的XML序列化器。
   *
   * @param objClass
   *     类对象。
   * @return
   *     为指定类构造的XML序列化器，如果该类没有注册的XML序列化器，则返回{@code null}。
   */
  public static XmlSerializer getSerializer(final Class<?> objClass) {
    requireNonNull("objClass", objClass);
    LOGGER.debug("Getting the XML serializer for class {}.", objClass);
    // ensure the objClass has been load, such that the register code in
    // the static initialization block of the class could be run.
    final String className = objClass.getName();
    try {
      Class.forName(className);
    } catch (final ClassNotFoundException e) {
      LOGGER.error("Failed to load the class {}", className);
    }
    return REGISTRY.get(new ClassKey(objClass));
  }

  /**
   * 将对象序列化为XML。
   *
   * @param <T>
   *     待序列化对象的类型。
   * @param objClass
   *     待序列化对象的类。
   * @param obj
   *     待序列化的对象。
   * @return
   *     从对象序列化得到的XML的字符串表示。
   * @throws XmlException
   *     如果发生任何错误。
   */
  public static <T> String serialize(final Class<T> objClass, final T obj)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Document doc = XmlUtils.newDocument();
    final Element node = serializer.serialize(doc, obj);
    final StringWriter out = new StringWriter();
    XmlUtils.print(node, out);
    return out.toString();
  }

  /**
   * 将对象序列化为XML。
   *
   * @param <T>
   *     待序列化对象的类型。
   * @param objClass
   *     待序列化对象的类。
   * @param obj
   *     待序列化的对象。
   * @param doc
   *     用于构造XML DOM节点的XML DOM文档。
   * @return
   *     从对象序列化得到的XML DOM节点。
   * @throws XmlException
   *     如果发生任何错误。
   */
  public static <T> Element serialize(final Class<T> objClass, final T obj,
      final Document doc) throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    return serializer.serialize(doc, obj);
  }

  /**
   * 将对象序列化为XML并将其打印到输出流。
   *
   * <p>调用此函数后，该流将被刷新但仍保持打开状态。
   *
   * @param <T>
   *     待序列化对象的类型。
   * @param objClass
   *     待序列化对象的类。
   * @param obj
   *     待序列化的对象。
   * @param out
   *     一个输出流，用于写入序列化后的XML。
   * @throws XmlException
   *     如果发生任何错误。
   */
  public static <T> void serialize(final Class<T> objClass, final T obj,
      final OutputStream out) throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Document doc = XmlUtils.newDocument();
    final Element root = serializer.serialize(doc, obj);
    doc.appendChild(root);
    XmlUtils.print(doc, out);
  }

  /**
   * 将对象序列化为XML并将其打印到打印流。
   *
   * <p>调用此函数后，该流将被刷新但仍保持打开状态。
   *
   * @param <T>
   *     待序列化对象的类型。
   * @param objClass
   *     待序列化对象的类。
   * @param obj
   *     待序列化的对象。
   * @param out
   *     一个打印流，用于写入序列化后的XML。
   * @throws XmlException
   *     如果发生任何错误。
   */
  public static <T> void serialize(final Class<T> objClass, final T obj,
      final PrintStream out) throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Document doc = XmlUtils.newDocument();
    final Element root = serializer.serialize(doc, obj);
    doc.appendChild(root);
    XmlUtils.print(doc, out);
  }

  /**
   * 将对象序列化为XML并将其打印到写入器。
   *
   * <p>调用此函数后，该写入器将被刷新但仍保持打开状态。
   *
   * @param <T>
   *     待序列化对象的类型。
   * @param objClass
   *     待序列化对象的类。
   * @param obj
   *     待序列化的对象。
   * @param writer
   *     一个写入器，用于写入序列化后的XML。
   * @throws XmlException
   *     如果发生任何错误。
   */
  public static <T> void serialize(final Class<T> objClass, final T obj,
      final Writer writer) throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Document doc = XmlUtils.newDocument();
    final Element root = serializer.serialize(doc, obj);
    doc.appendChild(root);
    XmlUtils.print(doc, writer);
  }

  /**
   * 将对象序列化为XML并将其存储到文件。
   *
   * @param <T>
   *     待序列化对象的类型。
   * @param objClass
   *     待序列化对象的类。
   * @param obj
   *     待序列化的对象。
   * @param file
   *     一个文件，用于存储序列化后的XML。
   * @throws XmlException
   *     如果发生任何错误。
   */
  public static <T> void serialize(final Class<T> objClass, final T obj,
      final File file) throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    OutputStream os = null;
    Writer writer = null;
    try {
      os = new FileOutputStream(file);
      writer = new OutputStreamWriter(os, UTF_8);
      final Document doc = XmlUtils.newDocument();
      final Element root = serializer.serialize(doc, obj);
      doc.appendChild(root);
      XmlUtils.print(doc, writer);
    } catch (final FileNotFoundException | SecurityException e) {
      throw new XmlSerializationException(e);
    } finally {
      IoUtils.closeQuietly(writer);
      IoUtils.closeQuietly(os);
    }
  }

  /**
   * 从XML节点反序列化对象。
   *
   * @param <T>
   *     待反序列化对象的类型。
   * @param objClass
   *     对象的类。
   * @param node
   *     XML节点。
   * @return
   *     从XML节点反序列化得到的对象。
   * @throws XmlException
   *     如果发生任何错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final Element node)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    try {
      return (T) serializer.deserialize(node);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  /**
   * 从XML节点的字符串表示反序列化对象。
   *
   * @param <T>
   *     待反序列化对象的类型。
   * @param objClass
   *     对象的类。
   * @param xml
   *     XML节点的字符串表示。
   * @return
   *     从XML节点反序列化得到的对象。
   * @throws XmlException
   *     如果发生任何错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final String xml)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final StringReader reader = new StringReader(xml);
    final Document doc = XmlUtils.parse(reader);
    final Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  /**
   * 从XML节点的字符串表示反序列化对象。
   *
   * <p>调用此函数后，该读取器仍保持打开状态。
   *
   * @param <T>
   *     待反序列化对象的类型。
   * @param objClass
   *     对象的类。
   * @param reader
   *     一个读取器，从中读取XML节点的字符串表示。
   * @return
   *     从XML节点反序列化得到的对象。
   * @throws XmlException
   *     如果发生任何错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final Reader reader)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Document doc = XmlUtils.parse(reader);
    final Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  /**
   * 从XML节点的字符串表示反序列化对象。
   *
   * <p>调用此函数后，该输入流仍保持打开状态。
   *
   * @param <T>
   *     待反序列化对象的类型。
   * @param objClass
   *     对象的类。
   * @param in
   *     一个输入流，从中读取XML节点的字符串表示。
   * @return
   *     从XML节点反序列化得到的对象。
   * @throws XmlException
   *     如果发生任何错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final InputStream in)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Document doc = XmlUtils.parse(in);
    final Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  /**
   * 从XML节点的字符串表示反序列化对象。
   *
   * @param <T>
   *     待反序列化对象的类型。
   * @param objClass
   *     对象的类。
   * @param file
   *     一个文件，从中读取XML节点的字符串表示。
   * @return
   *     从XML节点反序列化得到的对象。
   * @throws XmlException
   *     如果发生任何错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final File file)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Document doc = XmlUtils.parse(file);
    final Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  /**
   * 从XML节点的字符串表示反序列化对象。
   *
   * @param <T>
   *     待反序列化对象的类型。
   * @param objClass
   *     对象的类。
   * @param url
   *     一个URL，指向一个文件，从中读取XML节点的字符串表示。
   * @return
   *     从XML节点反序列化得到的对象。
   * @throws XmlException
   *     如果发生任何错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final Url url)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Document doc = XmlUtils.parse(url);
    final Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  /**
   * 从XML节点的字符串表示反序列化对象。
   *
   * @param <T>
   *     待反序列化对象的类型。
   * @param objClass
   *     对象的类。
   * @param url
   *     一个URL，指向一个文件，从中读取XML节点的字符串表示。
   * @return
   *     从XML节点反序列化得到的对象。
   * @throws XmlException
   *     如果发生任何错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final URL url)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Document doc = XmlUtils.parse(url);
    final Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  /**
   * 从XML节点的字符串表示反序列化对象。
   *
   * @param <T>
   *     待反序列化对象的类型。
   * @param objClass
   *     对象的类。
   * @param uri
   *     一个URI，指向一个文件，从中读取XML节点的字符串表示。
   * @return
   *     从XML节点反序列化得到的对象。
   * @throws XmlException
   *     如果发生任何错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass, final URI uri)
      throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Document doc = XmlUtils.parse(uri);
    final Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  /**
   * 从资源反序列化对象。
   *
   * @param <T>
   *     待反序列化对象的类型。
   * @param objClass
   *     对象的类。
   * @param resource
   *     资源的路径。
   * @param loaderClass
   *     用于加载资源的类。
   * @return
   *     从XML节点反序列化得到的对象。
   * @throws XmlException
   *     如果发生任何错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass,
      final String resource, final Class<?> loaderClass) throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Document doc = XmlUtils.parse(resource, loaderClass);
    final Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }

  /**
   * 从资源反序列化对象。
   *
   * @param <T>
   *     待反序列化对象的类型。
   * @param objClass
   *     对象的类。
   * @param resource
   *     资源的路径。
   * @param loader
   *     用于加载资源的类加载器。
   * @return
   *     从XML节点反序列化得到的对象。
   * @throws XmlException
   *     如果发生任何错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final Class<T> objClass,
      final String resource, final ClassLoader loader) throws XmlException {
    final XmlSerializer serializer = getSerializer(objClass);
    if (serializer == null) {
      throw new NoXmlSerializerRegisteredException(objClass);
    }
    final Document doc = XmlUtils.parse(resource, loader);
    final Element root = doc.getDocumentElement();
    try {
      return (T) serializer.deserialize(root);
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
  }
}