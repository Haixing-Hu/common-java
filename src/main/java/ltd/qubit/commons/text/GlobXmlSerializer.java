////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.serialize.XmlSerializer;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.xml.DomUtils;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlSerializationException;
import ltd.qubit.commons.text.xml.XmlUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The {@link XmlSerializer} for the {@link Glob} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class GlobXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "glob";
  public static final String FLAGS_ATTRIBUTE = "flags";

  public static final GlobXmlSerializer INSTANCE = new GlobXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public Glob deserialize(final Element root) throws XmlException {
    DomUtils.checkNode(root, ROOT_NODE);
    final int flags = DomUtils.getOptIntAttr(root, FLAGS_ATTRIBUTE, Glob.DEFAULT_FLAGS);
    final String pattern = DomUtils.getOptString(root, XmlUtils.PRESERVE_SPACE_ATTRIBUTE,
        true, StringUtils.EMPTY);
    return new Glob(pattern, flags);
  }

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
