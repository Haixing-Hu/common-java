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
 * Thrown when a XML node is empty.
 *
 * @author Haixing Hu
 */
public class EmptyXmlNodeException extends XmlException {

  private static final long serialVersionUID = - 1959147058166324842L;

  private final String tagName;

  public EmptyXmlNodeException(final String tagName) {
    super(formatMessage(tagName));
    this.tagName = tagName;
  }

  public EmptyXmlNodeException(final String tagName, final String message) {
    super(formatMessage(tagName) + message);
    this.tagName = tagName;
  }

  public String getTagName() {
    return tagName;
  }

  public static String formatMessage(final String tagName) {
    return "The node <" + tagName + "> should not be empty. ";
  }
}