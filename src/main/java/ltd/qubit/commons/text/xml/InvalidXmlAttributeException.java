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
 * Thrown when an XML attribute has invalid value.
 *
 * @author Haixing Hu
 */
public class InvalidXmlAttributeException extends XmlException {

  private static final long serialVersionUID = - 4373359857589810490L;

  private final String tagName;
  private final String attributeName;
  private final String attributeValue;

  public InvalidXmlAttributeException(final String tagName,
      final String attributeName, final String attributeValue) {
    super(formatMessage(tagName, attributeName, attributeValue));
    this.tagName = tagName;
    this.attributeName = attributeName;
    this.attributeValue = attributeValue;
  }

  public InvalidXmlAttributeException(final String tagName,
      final String attributeName, final String attributeValue,
      final Throwable cause) {
    super(formatMessage(tagName, attributeName, attributeValue), cause);
    this.tagName = tagName;
    this.attributeName = attributeName;
    this.attributeValue = attributeValue;
  }

  public InvalidXmlAttributeException(final String tagName,
      final String attributeName, final String attributeValue,
      final String message) {
    super(formatMessage(tagName, attributeName, attributeValue) + message);
    this.tagName = tagName;
    this.attributeName = attributeName;
    this.attributeValue = attributeValue;
  }

  public InvalidXmlAttributeException(final String tagName,
      final String attributeName, final String attributeValue,
      final String message, final Throwable cause) {
    super(formatMessage(tagName, attributeName, attributeValue) + message,
        cause);
    this.tagName = tagName;
    this.attributeName = attributeName;
    this.attributeValue = attributeValue;
  }

  public String getTagName() {
    return tagName;
  }

  public String getAttributeName() {
    return attributeName;
  }

  public String getAttributeValue() {
    return attributeValue;
  }

  public static String formatMessage(final String tagName,
      final String attributeName, final String attributeValue) {
    return "Invalid attribute value for the attribute '"
      + attributeName + "' of the node <" + tagName
      + ">: \"" + attributeValue + "\". ";
  }
}