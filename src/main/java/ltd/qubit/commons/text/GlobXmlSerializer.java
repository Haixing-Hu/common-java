////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.io.serialize.XmlSerializer;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.xml.DomUtils;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlSerializationException;
import ltd.qubit.commons.text.xml.XmlUtils;

/**
 * {@link Glob} 类的 {@link XmlSerializer}。
 *
 * @author 胡海星
 */
@Immutable
public final class GlobXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "glob";
  public static final String FLAGS_ATTRIBUTE = "flags";

  /**
   * 单例实例。
   */
  public static final GlobXmlSerializer INSTANCE = new GlobXmlSerializer();

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
  public Glob deserialize(final Element root) throws XmlException {
    DomUtils.checkNode(root, ROOT_NODE);
    final int flags = DomUtils.getOptIntAttr(root, FLAGS_ATTRIBUTE, Glob.DEFAULT_FLAGS);
    final String pattern = DomUtils.getOptString(root, XmlUtils.PRESERVE_SPACE_ATTRIBUTE,
        true, StringUtils.EMPTY);
    return new Glob(pattern, flags);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Element serialize(final Document doc, final Object obj)
      throws XmlException {
    final Glob glob;
    try {
      glob = (Glob) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    DomUtils.setOptIntAttr(root, FLAGS_ATTRIBUTE, glob.flags, Glob.DEFAULT_FLAGS);
    root.setTextContent(glob.pattern);
    return root;
  }
}