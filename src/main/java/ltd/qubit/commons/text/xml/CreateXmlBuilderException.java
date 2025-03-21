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
 * Thrown to indicate an error occurred while creating the XML document builder.
 *
 * @author Haixing Hu
 */
public class CreateXmlBuilderException extends XmlException {

  private static final long serialVersionUID = 7422489284067686642L;

  private static final String MESSAGE_CREATE_BUILDER_FAILED =
      "An error occurs while creating the XML document builder. ";

  public CreateXmlBuilderException() {
    super(MESSAGE_CREATE_BUILDER_FAILED);
  }

  public CreateXmlBuilderException(final String message) {
    super(message);
  }

  public CreateXmlBuilderException(final Throwable e) {
    super(MESSAGE_CREATE_BUILDER_FAILED + e, e);
  }

  public CreateXmlBuilderException(final String message, final Throwable e) {
    super(message, e);
  }
}
