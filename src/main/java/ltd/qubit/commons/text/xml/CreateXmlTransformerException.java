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
 * Thrown to indicate an error occurred while creating the XML filter.
 *
 * @author Haixing Hu
 */
public class CreateXmlTransformerException extends XmlException {

  private static final long serialVersionUID = 3650270261620726147L;

  private static final String MESSAGE_CREATE_TRANSFORMER_FAILED =
      "An error occurs while creating the XML filter. ";

  public CreateXmlTransformerException() {
    super(MESSAGE_CREATE_TRANSFORMER_FAILED);
  }

  public CreateXmlTransformerException(final String message) {
    super(message);
  }

  public CreateXmlTransformerException(final Throwable e) {
    super(MESSAGE_CREATE_TRANSFORMER_FAILED + e, e);
  }

  public CreateXmlTransformerException(final String message, final Throwable e) {
    super(message, e);
  }
}
