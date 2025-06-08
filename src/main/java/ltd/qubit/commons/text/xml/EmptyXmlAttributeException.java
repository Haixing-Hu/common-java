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
 * Thrown when a required XML attribute is empty.
 *
 * @author Haixing Hu
 */
public class EmptyXmlAttributeException extends XmlException {

  private static final long serialVersionUID = - 4714885273534110209L;
  private final String tagName;
  private final String attributeName;

  public EmptyXmlAttributeException(final String tagName, final String attributeName) {
    super(formatMessage(tagName, attributeName));
    this.tagName = tagName;
    this.attributeName = attributeName;
  }

  public EmptyXmlAttributeException(final String tagName,
      final String attributeName, final String message) {
    super(formatMessage(tagName, attributeName) + message);
    this.tagName = tagName;
    this.attributeName = attributeName;
  }

  public String getTagName() {
    return tagName;
  }

  public String getAttributeName() {
    return attributeName;
  }

  public static String formatMessage(final String tagName,
      final String attributeName) {
    return "The XML attribute '"
      + attributeName
      + "' of the node <"
      + tagName
      + "> should not be empty. ";
  }
}