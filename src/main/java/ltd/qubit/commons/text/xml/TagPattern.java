////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml;

import java.io.Serializable;

import javax.annotation.Nullable;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import ltd.qubit.commons.io.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * The {@link TagPattern} object represent a pattern applied to an XML tag.
 *
 * <p>The pattern may specify the tag name, the attribute name and the attribute
 * value.
 *
 * <p>If the tag name is specified, the node matches this pattern must have the
 * same tag name; if the attribute name is specified, the node matches this
 * pattern must have an attribute with the same name; if the attribute value is
 * specified (the attribute value has effect only if the attribute name is
 * specified), the node matches this pattern must have the same attribute value
 * for the specified attribute name.
 *
 * <p>All the comparison of string values are case-sensitive. So if the pattern
 * is applied to the HTML node, the HTML parser must be configured to normalize
 * the tag name and attribute name (usually to lowercase).
 *
 * @author Haixing Hu
 */
public final class TagPattern implements CloneableEx<TagPattern>, Serializable {

  private static final long serialVersionUID = - 7813386763902331244L;

  public static final int    DEFAULT_ORDER   = 0;   // no limit on the order

  static {
    BinarySerialization.register(TagPattern.class, TagPatternBinarySerializer.INSTANCE);
    XmlSerialization.register(TagPattern.class, TagPatternXmlSerializer.INSTANCE);
  }

  protected String tagName;
  protected String attributeName;
  protected String attributeValue;
  protected int order;
  protected TagPattern child;

  public TagPattern() {
    tagName = null;
    attributeName = null;
    attributeValue = null;
    order = DEFAULT_ORDER;
    child = null;
  }

  public String getTagName() {
    return tagName;
  }

  public void setTagName(@Nullable final String tagName) {
    this.tagName = tagName;
  }

  public String getAttributeName() {
    return attributeName;
  }

  public void setAttributeName(@Nullable final String attributeName) {
    this.attributeName = attributeName;
  }

  public String getAttributeValue() {
    return attributeValue;
  }

  public void setAttributeValue(@Nullable final String attributeValue) {
    this.attributeValue = attributeValue;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(final int order) {
    if (order < 0) {
      throw new IllegalArgumentException("The order must be zero or positive.");
    }
    this.order = order;
  }

  public TagPattern getChild() {
    return child;
  }

  public void setChild(final TagPattern child) {
    this.child = child;
  }

  public boolean matches(final Node node) {
    if (node.getNodeType() != Node.ELEMENT_NODE) {
      return false;
    }
    final Element element = (Element) node;
    // check the tag name
    if (tagName != null) {
      final String name = element.getTagName();
      if (! tagName.equals(name)) {
        return false;
      }
    }
    // check the attribute name
    if (attributeName != null) {
      if (! element.hasAttribute(attributeName)) {
        return false;
      }
      // check the attribute value
      if (attributeValue != null) {
        final String attr = element.getAttribute(attributeName);
        return attributeValue.equals(attr);
      }
    }
    return true;
  }

  public Element search(final Node root) {
    final DomNodeIterator iter = new DomNodeIterator(root);
    int n = 0;
    while (iter.hasNext()) {
      final Node current = iter.next();
      final short type = current.getNodeType();
      if (type == Node.COMMENT_NODE) {
        iter.skipChildren();
        continue;
      }
      if (current.getNodeType() != Node.ELEMENT_NODE) {
        continue;
      }
      if (matches(current)) {
        ++n; // this is the n-th matched node
        if (order == 0) {
          // no restriction on the order
          if (child == null) {
            // found a matched node
            return (Element) current;
          } else {
            // try to find a node matches the child in the subtree of current
            // node
            final Element result = child.search(current);
            if (result != null) {
              return result;
            }
            // otherwise, continue searching
          }
        } else if (n == order) {
          // there is a restriction on the order, and this is the "order"-th
          // node matches
          // this pattern
          if (child == null) {
            // found a matched node
            return (Element) current;
          } else {
            // find a node matches the child in the subtree of current node,
            // if it failed, don't need to continue searching the next match
            // since
            // the order is restricted.
            return child.search(current);
          }
        }
      }
    }
    return null;
  }

  @Override
  public TagPattern cloneEx() {
    final TagPattern cloned = new TagPattern();
    cloned.tagName = tagName;
    cloned.attributeName = attributeName;
    cloned.attributeValue = attributeValue;
    cloned.order = order;
    cloned.child = child;
    return cloned;
  }

  @Override
  public int hashCode() {
    final int multiplier = 111;
    int code = 1911;
    code = Hash.combine(code, multiplier, tagName);
    code = Hash.combine(code, multiplier, attributeName);
    code = Hash.combine(code, multiplier, attributeValue);
    code = Hash.combine(code, multiplier, order);
    code = Hash.combine(code, multiplier, child);
    return code;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof TagPattern)) {
      return false;
    }
    final TagPattern other = (TagPattern) obj;
    return (order == other.order)
        && Equality.equals(tagName, other.tagName)
        && Equality.equals(attributeName, other.attributeName)
        && Equality.equals(attributeValue, other.attributeValue)
        && Equality.equals(child, other.child);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("tagName", tagName)
               .append("attributeName", attributeName)
               .append("attributeValue", attributeValue)
               .append("order", order)
               .append("child", child)
               .toString();
  }

}
