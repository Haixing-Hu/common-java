////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.serialize;

import javax.annotation.concurrent.ThreadSafe;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.text.xml.XmlException;

/**
 * {@link XmlSerializer} 接口提供了将对象序列化为 XML DOM 树以及从 XML DOM 树反序列化对象
 * 的功能。
 *
 * <p><b>注意</b>：此接口的所有实现都<b>必须</b>是线程安全的。
 *
 * @author 胡海星
 */
@ThreadSafe
public interface XmlSerializer {

  /**
   * 获取此类的对象的XML DOM树的根节点的名称。
   *
   * @return 此类的对象的XML DOM树的根节点的名称。
   */
  String getRootNodeName();

  /**
   * 从XML DOM树反序列化对象。
   *
   * @param root
   *     XML DOM树的根节点。
   * @return 从XML DOM树反序列化得到的对象。
   * @throws NullPointerException
   *     如果 {@code root} 为 {@code null}。
   * @throws XmlException
   *     如果发生任何XML错误。
   */
  Object deserialize(Element root) throws XmlException;

  /**
   * 将对象序列化为XML DOM树。
   *
   * @param doc
   *     用于创建XML DOM节点的XML DOM文档。
   * @param obj
   *     待序列化的对象。它不能为{@code null}。
   * @return 生成的XML DOM树的根节点。
   * @throws NullPointerException
   *     如果 {@code obj} 为 {@code null}。
   * @throws XmlException
   *     如果发生任何XML错误。
   */
  Element serialize(Document doc, Object obj) throws XmlException;

}