////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.serialize;

import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.text.xml.XmlException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A {@link XmlSerializer} provides interface to serialize and deserialize
 * objects to and from an XML DOM true.
 *
 * <p><b>NOTE</b>: All implementation of this interface <b>MUST</b> be thread
 * safe.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public interface XmlSerializer {

  /**
   * Gets the name of the root node of the XML DOM tree for the objects of this
   * class.
   *
   * @return the name of the root node in the XML DOM tree for the objects of
   *     this class.
   */
  String getRootNodeName();

  /**
   * Deserializes an object from an XML DOM true.
   *
   * @param root
   *     The root node of the XML DOM tree.
   * @return The object deserialized from XML DOM tree.
   * @throws NullPointerException
   *     If the {@code root} is {@code null}.
   * @throws XmlException
   *     If any XML error occurred.
   */
  Object deserialize(Element root) throws XmlException;

  /**
   * Serializes an object to an XML DOM true.
   *
   * @param doc
   *     The XML DOM document used to create XML DOM node.
   * @param obj
   *     The object to be serialized. It can't be {@code null}.
   * @return The root node of the resulting XML DOM tree.
   * @throws NullPointerException
   *     If the {@code obj} is {@code null}.
   * @throws XmlException
   *     If any XML error occurred.
   */
  Element serialize(Document doc, Object obj) throws XmlException;

}
