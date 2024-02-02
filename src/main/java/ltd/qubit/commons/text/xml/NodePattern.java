////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml;

import java.io.Serializable;

import javax.annotation.concurrent.NotThreadSafe;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An {@link NodePattern} represents a pattern of XML DOM node.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class NodePattern implements CloneableEx<NodePattern>, Serializable {

  private static final long serialVersionUID = - 1769792947323446940L;

  private static final Logger LOGGER = LoggerFactory.getLogger(NodePattern.class);

  public static final String DEFAULT_XPATH = "*";

  public static final XPathExpression DEFAULT_EXPRESSION;

  static {
    try {
      DEFAULT_EXPRESSION = XmlUtils.compileXPath(DEFAULT_XPATH);
    } catch (final XmlException e) {
      throw new InitializationError(e);
    }
  }

  static {
    BinarySerialization.register(NodePattern.class, NodePatternBinarySerializer.INSTANCE);
    XmlSerialization.register(NodePattern.class, NodePatternXmlSerializer.INSTANCE);
  }

  protected String xpath;
  protected transient XPathExpression expression;

  protected NodePattern() {
    xpath = DEFAULT_XPATH;
    expression = DEFAULT_EXPRESSION;
  }

  protected NodePattern(final String xpath)
      throws InvalidXPathExpressionException {
    this.xpath = requireNonNull("xpath", xpath);
    expression = XmlUtils.compileXPath(xpath);
  }

  public boolean isDefault() {
    return xpath.equals(DEFAULT_XPATH);
  }

  public void clear() {
    xpath = DEFAULT_XPATH;
    expression = DEFAULT_EXPRESSION;
  }

  public String getXPath() {
    return xpath;
  }

  public void setXPath(final String xpath)
      throws InvalidXPathExpressionException {
    this.xpath = requireNonNull("xpath", xpath);
    expression = XmlUtils.compileXPath(xpath);
  }

  //  constant method
  public Node matchNode(final Node root) {
    requireNonNull("root", root);
    try {
      assert (expression != null);
      return (Node) expression.evaluate(root, XPathConstants.NODE);
    } catch (final XPathExpressionException e) {
      LOGGER.error("{}", e.getMessage());
      return null;
    }
  }

  // constant method
  public NodeList matchNodes(final Node root) {
    requireNonNull("root", root);
    try {
      assert (expression != null);
      return (NodeList) expression.evaluate(root, XPathConstants.NODESET);
    } catch (final XPathExpressionException e) {
      LOGGER.error("{}", e.getMessage());
      return null;
    }
  }

  @Override
  public NodePattern clone() {
    final NodePattern result = new NodePattern();
    result.xpath = xpath;
    result.expression = expression;
    return result;
  }

  @Override
  public int hashCode() {
    return xpath.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final NodePattern other = (NodePattern) obj;
    return xpath.equals(other.xpath);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("xpath", xpath)
               .toString();
  }
}
