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
 * 当 XML 节点具有无效内容时抛出此异常。
 *
 * @author 胡海星
 */
public class InvalidXmlNodeContentException extends XmlException {

  private static final long serialVersionUID = 2084890707891169214L;

  private final String tagName;
  private final String content;

  public InvalidXmlNodeContentException(final String tagName,
      final String content) {
    super(formatMessage(tagName, content));
    this.tagName = tagName;
    this.content = content;
  }

  public InvalidXmlNodeContentException(final String tagName,
      final String content, final Throwable cause) {
    super(formatMessage(tagName, content), cause);
    this.tagName = tagName;
    this.content = content;
  }

  public InvalidXmlNodeContentException(final String tagName,
      final String content, final String message) {
    super(formatMessage(tagName, content) + message);
    this.tagName = tagName;
    this.content = content;
  }

  public InvalidXmlNodeContentException(final String tagName,
      final String content, final String message, final Throwable cause) {
    super(formatMessage(tagName, content) + message, cause);
    this.tagName = tagName;
    this.content = content;
  }

  /**
   * 获取标签名称。
   *
   * @return 标签名称
   */
  public String getTagName() {
    return tagName;
  }

  /**
   * 获取内容。
   *
   * @return 内容
   */
  public String getContent() {
    return content;
  }

  /**
   * 格式化错误消息。
   *
   * @param tagName
   *     标签名称
   * @param content
   *     内容
   * @return 格式化的错误消息
   */
  public static String formatMessage(final String tagName, final String content) {
    return "Invalid content of node <" + tagName + ">: \"" + content + "\". ";
  }
}