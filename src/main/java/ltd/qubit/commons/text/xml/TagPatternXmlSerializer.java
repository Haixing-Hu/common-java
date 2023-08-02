////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.serialize.XmlSerializer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static ltd.qubit.commons.text.xml.DomUtils.appendOptStringChild;
import static ltd.qubit.commons.text.xml.DomUtils.checkNode;
import static ltd.qubit.commons.text.xml.DomUtils.getOptChild;
import static ltd.qubit.commons.text.xml.DomUtils.getOptIntAttr;
import static ltd.qubit.commons.text.xml.DomUtils.getOptString;
import static ltd.qubit.commons.text.xml.DomUtils.getOptStringChild;
import static ltd.qubit.commons.text.xml.DomUtils.getReqStringAttr;
import static ltd.qubit.commons.text.xml.DomUtils.setOptIntAttr;

/**
 * The {@link XmlSerializer} for the {@link TagPattern} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class TagPatternXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE       = "tag-pattern";
  public static final String ORDER_ATTRIBUTE = "order";
  public static final String TAG_NODE        = "tag";
  public static final String ATTRIBUTE_NODE  = "attribute";
  public static final String NAME_ATTRIBUTE  = "name";

  public static final TagPatternXmlSerializer INSTANCE = new TagPatternXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public TagPattern deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final TagPattern result = new TagPattern();
    result.tagName = getOptStringChild(root, TAG_NODE, null, true, null);
    Element node = getOptChild(root, ATTRIBUTE_NODE);
    if (node != null) {
      result.attributeName = getReqStringAttr(node, NAME_ATTRIBUTE, true, false);
      result.attributeValue = getOptString(node, null, true, null);
    }
    result.order = getOptIntAttr(root, ORDER_ATTRIBUTE, 0,
        Integer.MAX_VALUE, TagPattern.DEFAULT_ORDER);
    node = getOptChild(root, ROOT_NODE);
    if (node != null) {
      result.child = deserialize(node);
    }
    return result;
  }

  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    final TagPattern pattern;
    try {
      pattern = (TagPattern) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    appendOptStringChild(doc, root, TAG_NODE, null, pattern.tagName);
    if (pattern.attributeName != null) {
      final Element node = doc.createElement(ATTRIBUTE_NODE);
      node.setAttribute(NAME_ATTRIBUTE, pattern.attributeName);
      if (pattern.attributeValue != null) {
        node.setTextContent(pattern.attributeValue);
      }
      root.appendChild(node);
    }
    setOptIntAttr(root, ORDER_ATTRIBUTE, pattern.order, TagPattern.DEFAULT_ORDER);
    if (pattern.child != null) {
      final Element childNode = serialize(doc, pattern.child);
      root.appendChild(childNode);
    }
    return root;
  }

}
