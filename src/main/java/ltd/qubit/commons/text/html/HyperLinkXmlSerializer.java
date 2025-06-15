////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.html;

import java.net.MalformedURLException;

import javax.annotation.concurrent.ThreadSafe;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.io.serialize.XmlSerializer;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.net.Url;
import ltd.qubit.commons.text.xml.DomUtils;
import ltd.qubit.commons.text.xml.InvalidXmlAttributeException;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlSerializationException;

/**
 * {@link HyperLink} 类的 {@link XmlSerializer}。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class HyperLinkXmlSerializer implements XmlSerializer {

  /**
   * XML 根节点名称。
   */
  public static final String ROOT_NODE = "hyper-link";

  /**
   * XML URL 属性名称。
   */
  public static final String URL_ATTRIBUTE = "url";

  /**
   * 单例实例。
   */
  public static final HyperLinkXmlSerializer INSTANCE = new HyperLinkXmlSerializer();

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HyperLink deserialize(final Element root) throws XmlException {
    DomUtils.checkNode(root, ROOT_NODE);
    final String str = DomUtils.getReqStringAttr(root, URL_ATTRIBUTE, true, true);
    Url url = null;
    try {
      url = new Url(str);
    } catch (final MalformedURLException e) {
      throw new InvalidXmlAttributeException(ROOT_NODE, URL_ATTRIBUTE, str);
    }
    final String anchor = DomUtils.getOptString(root, null, true, null);
    return new HyperLink(url, anchor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    final HyperLink link;
    try {
      link = (HyperLink) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    final Url url = link.url();
    if (url == null) {
      root.setAttribute(URL_ATTRIBUTE, StringUtils.EMPTY);
    } else {
      root.setAttribute(URL_ATTRIBUTE, url.toString());
    }
    final String anchor = link.anchor();
    if (anchor == null) {
      root.setTextContent(StringUtils.EMPTY);
    } else {
      root.setTextContent(anchor);
    }
    return root;
  }
}