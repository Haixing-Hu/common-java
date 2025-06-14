////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml;

/**
 * Thrown to indicate an X-Path expression has an invalid syntax.
 *
 * @author Haixing Hu
 */
public class InvalidXPathExpressionException extends XmlException {

  private static final long serialVersionUID = 2865294933434160448L;

  private final String expression;

  public InvalidXPathExpressionException(final String expression) {
    super("The syntax of the x-path expression is invalid: " + expression);
    this.expression = expression;
  }

  public InvalidXPathExpressionException(final String expression, final Throwable e) {
    super("The syntax of the x-path expression is invalid: " + expression, e);
    this.expression = expression;
  }

  public String getExpression() {
    return expression;
  }
}