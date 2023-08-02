////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml;

/**
 * Thrown when an error occurs while parsing an XML document.
 *
 * @author Haixing Hu
 */
public class XmlException extends Exception {

  private static final long serialVersionUID = -4430241858376962527L;

  public XmlException() {
  }

  public XmlException(final String message) {
    super(message);
  }

  public XmlException(final Throwable e) {
    super(e);
  }

  public XmlException(final String message, final Throwable e) {
    super(message, e);
  }
}
