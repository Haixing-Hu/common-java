////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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

import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * {@link TagPattern} 对象表示应用于 XML 标签的模式。
 *
 * <p>该模式可以指定标签名称、属性名称和属性值。
 *
 * <p>如果指定了标签名称，匹配此模式的节点必须具有相同的标签名称；
 * 如果指定了属性名称，匹配此模式的节点必须具有相同名称的属性；
 * 如果指定了属性值（属性值仅在指定属性名称时有效），
 * 匹配此模式的节点必须对指定的属性名称具有相同的属性值。
 *
 * <p>所有字符串值的比较都是区分大小写的。因此，如果将模式应用于 HTML 节点，
 * 必须配置 HTML 解析器以规范化标签名称和属性名称（通常为小写）。
 *
 * @author 胡海星
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

  /**
   * 获取标签名称。
   *
   * @return 标签名称
   */
  public String getTagName() {
    return tagName;
  }

  /**
   * 设置标签名称。
   *
   * @param tagName
   *     新的标签名称
   */
  public void setTagName(@Nullable final String tagName) {
    this.tagName = tagName;
  }

  /**
   * 获取属性名称。
   *
   * @return 属性名称
   */
  public String getAttributeName() {
    return attributeName;
  }

  /**
   * 设置属性名称。
   *
   * @param attributeName
   *     新的属性名称
   */
  public void setAttributeName(@Nullable final String attributeName) {
    this.attributeName = attributeName;
  }

  /**
   * 获取属性值。
   *
   * @return 属性值
   */
  public String getAttributeValue() {
    return attributeValue;
  }

  /**
   * 设置属性值。
   *
   * @param attributeValue
   *     新的属性值
   */
  public void setAttributeValue(@Nullable final String attributeValue) {
    this.attributeValue = attributeValue;
  }

  /**
   * 获取顺序。
   *
   * @return 顺序
   */
  public int getOrder() {
    return order;
  }

  /**
   * 设置顺序。
   *
   * @param order
   *     新的顺序，必须为零或正数
   * @throws IllegalArgumentException
   *     如果顺序为负数
   */
  public void setOrder(final int order) {
    if (order < 0) {
      throw new IllegalArgumentException("The order must be zero or positive.");
    }
    this.order = order;
  }

  /**
   * 获取子模式。
   *
   * @return 子模式
   */
  public TagPattern getChild() {
    return child;
  }

  /**
   * 设置子模式。
   *
   * @param child
   *     新的子模式
   */
  public void setChild(final TagPattern child) {
    this.child = child;
  }

  /**
   * 检查指定节点是否匹配此模式。
   *
   * @param node
   *     要检查的节点
   * @return 如果节点匹配此模式，则返回 {@code true}；否则返回 {@code false}
   */
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

  /**
   * 在指定根节点中搜索匹配此模式的元素。
   *
   * @param root
   *     要搜索的根节点
   * @return 匹配此模式的第一个元素，如果未找到则返回 {@code null}
   */
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