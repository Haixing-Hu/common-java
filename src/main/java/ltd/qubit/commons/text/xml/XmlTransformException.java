////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml;

/**
 * Thrown to indicate an error occurred while transforming the XML.
 *
 * @author Haixing Hu
 */
public class XmlTransformException extends XmlException {

  private static final long serialVersionUID = 1582261306326363474L;

  private static final String MESSAGE_TRANSFORM_FAILED =
      "Transforming the XML failed. ";

  public XmlTransformException() {
    super(MESSAGE_TRANSFORM_FAILED);
  }

  public XmlTransformException(final String message) {
    super(message);
  }

  public XmlTransformException(final Throwable t) {
    super(MESSAGE_TRANSFORM_FAILED + t.getMessage(), t);
  }

  public XmlTransformException(final String message, final Throwable t) {
    super(message, t);
  }
}
