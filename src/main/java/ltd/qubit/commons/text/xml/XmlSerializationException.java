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
 * Thrown to indicate an XML serialization error.
 *
 * @author Haixing Hu
 */
public class XmlSerializationException extends XmlException {

  private static final long serialVersionUID = 5384515309927497843L;

  public XmlSerializationException() {
  }

  public XmlSerializationException(final String message) {
    super(message);
  }

  public XmlSerializationException(final Throwable e) {
    super(e);
  }

  public XmlSerializationException(final String message, final Throwable e) {
    super(message, e);
  }
}