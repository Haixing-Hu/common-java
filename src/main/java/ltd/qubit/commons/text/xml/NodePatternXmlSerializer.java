////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.io.io.serialize.XmlSerializer;

/**
 * The {@link XmlSerializer} for the {@link NodePattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class NodePatternXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "node-pattern";

  public static final String XPATH_NODE = "xpath";

  public static final NodePatternXmlSerializer INSTANCE = new NodePatternXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public NodePattern deserialize(final Element root) throws XmlException {
    DomUtils.checkNode(root, ROOT_NODE);
    final String xpath = DomUtils
            .getReqStringChild(root, XPATH_NODE, null, true, true);
    return new NodePattern(xpath);
  }

  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    final NodePattern pattern;
    try {
      pattern = (NodePattern) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    DomUtils.appendReqStringChild(doc, root, XPATH_NODE, null, pattern.xpath);
    return root;
  }

}
