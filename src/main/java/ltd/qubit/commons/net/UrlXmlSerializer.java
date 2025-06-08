////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.net.MalformedURLException;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.io.serialize.XmlSerializer;
import ltd.qubit.commons.text.xml.DomUtils;
import ltd.qubit.commons.text.xml.InvalidXmlNodeContentException;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlSerializationException;

/**
 * {@link Url} 类的 {@link XmlSerializer}。
 *
 * @author 胡海星
 */
@Immutable
public final class UrlXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "url";

  public static final UrlXmlSerializer INSTANCE = new UrlXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  /**
   * 从XML DOM树反序列化对象。
   *
   * @param root
   *     XML DOM树的根节点。
   * @return 从XML DOM树反序列化得到的 {@link Url} 对象。
   * @throws XmlException
   *     如果发生任何XML错误。
   */
  @Override
  public Url deserialize(final Element root) throws XmlException {
    DomUtils.checkNode(root, ROOT_NODE);
    final String str = DomUtils.getReqString(root, null, true, true);
    if (str.length() == 0) {
      return new Url();
    } else {
      try {
        return new Url(str);
      } catch (final MalformedURLException e) {
        throw new InvalidXmlNodeContentException(ROOT_NODE, str);
      }
    }
  }

  /**
   * 将对象序列化为XML DOM树。
   *
   * @param doc
   *     用于创建XML DOM节点的XML DOM文档。
   * @param obj
   *     要序列化的对象。它不能是 {@code null}。
   * @return 生成的XML DOM树的根节点。
   * @throws XmlException
   *     如果发生任何XML错误。
   */
  @Override
  public Element serialize(final Document doc, final Object obj)
      throws XmlException {
    final Url url;
    try {
      url = (Url) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element result = doc.createElement(ROOT_NODE);
    result.setTextContent(url.toString());
    return result;
  }
}