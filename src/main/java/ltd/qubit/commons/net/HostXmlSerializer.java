////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.io.serialize.XmlSerializer;
import ltd.qubit.commons.text.xml.DomUtils;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlSerializationException;

/**
 * {@link Host} 类的 {@link XmlSerializer}。
 *
 * @author 胡海星
 */
@Immutable
public final class HostXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "host";
  public static final String SCHEME_NODE = "scheme";
  public static final String HOSTNAME_NODE = "hostname";
  public static final String PORT_NODE = "port";

  public static final HostXmlSerializer INSTANCE = new HostXmlSerializer();

  /**
   * 获取此类的对象对应的XML DOM树的根节点名称。
   *
   * @return
   *     此类的对象对应的XML DOM树的根节点名称。
   */
  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  /**
   * 从XML DOM树反序列化对象。
   *
   * @param root
   *     XML DOM树的根节点，不能为 {@code null}。
   * @return
   *     从XML DOM树反序列化得到的 {@link Host} 对象。
   * @throws NullPointerException
   *     如果 {@code root} 为 {@code null}。
   * @throws XmlException
   *     如果发生任何XML错误。
   */
  @Override
  public Host deserialize(final Element root) throws XmlException {
    DomUtils.checkNode(root, ROOT_NODE);
    final String scheme = DomUtils.getReqStringChild(root, SCHEME_NODE, null, true,
        true);
    final String hostname = DomUtils.getReqStringChild(root, HOSTNAME_NODE, null,
        true, true);
    final int port = DomUtils.getOptIntChild(root, PORT_NODE, - 1);
    return new Host(scheme, hostname, port);
  }

  /**
   * 将对象序列化为XML DOM树。
   *
   * @param doc
   *     用于创建XML DOM节点的XML DOM文档。
   * @param obj
   *     要序列化的对象。它不能是 {@code null}。
   * @return
   *     生成的XML DOM树的根节点。
   * @throws NullPointerException
   *     如果 {@code doc} 或 {@code obj} 为 {@code null}。
   * @throws XmlException
   *     如果发生任何XML错误。
   */
  @Override
  public Element serialize(final Document doc, final Object obj)
      throws XmlException {
    final Host host;
    try {
      host = (Host) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    DomUtils.appendReqStringChild(doc, root, SCHEME_NODE, null, host.scheme());
    DomUtils.appendReqStringChild(doc, root, HOSTNAME_NODE, null, host.hostname());
    DomUtils.appendOptIntChild(doc, root, PORT_NODE, host.port(), - 1);
    return root;
  }
}