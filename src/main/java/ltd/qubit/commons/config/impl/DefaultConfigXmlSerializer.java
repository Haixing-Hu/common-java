////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.impl;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.io.serialize.XmlSerializer;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlSerializationException;

import static ltd.qubit.commons.text.xml.DomUtils.checkNode;
import static ltd.qubit.commons.text.xml.DomUtils.getChildren;

/**
 * {@link DefaultConfig} 类的 {@link XmlSerializer}。
 *
 * @author 胡海星
 */
@Immutable
public final class DefaultConfigXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "configuration";

  public static final DefaultConfigXmlSerializer INSTANCE = new DefaultConfigXmlSerializer();

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DefaultConfig deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final DefaultConfig result = new DefaultConfig();
    final List<Element> propNodeList = getChildren(root, null);
    if ((propNodeList == null) || propNodeList.isEmpty()) {
      return result;
    }
    final DefaultPropertyXmlSerializer propSerializer = DefaultPropertyXmlSerializer.INSTANCE;
    for (final Element propNode : propNodeList) {
      final DefaultProperty prop = propSerializer.deserialize(propNode);
      result.properties.put(prop.getName(), prop);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    final DefaultConfig config;
    try {
      config = (DefaultConfig) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final DefaultPropertyXmlSerializer propSerializer = DefaultPropertyXmlSerializer.INSTANCE;
    final Element root = doc.createElement(ROOT_NODE);
    for (final DefaultProperty prop : config.properties.values()) {
      final Element node = propSerializer.serialize(doc, prop);
      root.appendChild(node);
    }
    return root;
  }

}