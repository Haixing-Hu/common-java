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
 * 抛出此异常表示在创建 XML 文档构建器时发生错误。
 *
 * @author 胡海星
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