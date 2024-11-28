////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.value;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.io.io.serialize.XmlSerializer;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.lang.TypeUtils;
import ltd.qubit.commons.text.xml.InvalidXmlNodeContentException;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlSerializationException;

import static ltd.qubit.commons.text.xml.DomUtils.PRESERVE_SPACE_ATTRIBUTE;
import static ltd.qubit.commons.text.xml.DomUtils.checkNode;
import static ltd.qubit.commons.text.xml.DomUtils.getOptEnumAttr;
import static ltd.qubit.commons.text.xml.DomUtils.getOptString;
import static ltd.qubit.commons.text.xml.DomUtils.setOptEnumAttr;

/**
 * The {@link XmlSerializer} for the {@link BasicValue} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class BasicValueXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE            = "value";
  public static final String TYPE_ATTRIBUTE       = "type";

  public static final BasicValueXmlSerializer INSTANCE = new BasicValueXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public BasicValue deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final BasicValue result = new BasicValue();
    result.type = getOptEnumAttr(root, TYPE_ATTRIBUTE, true, Type.class, BasicValue.DEFAULT_TYPE);
    final String str = getOptString(root, PRESERVE_SPACE_ATTRIBUTE, true, null);
    if (str == null) {
      result.value = null;
    } else {
      try {
        result.value = TypeUtils.parseObject(result.type, str);
      } catch (final Exception e) {
        throw new InvalidXmlNodeContentException(ROOT_NODE, str, e.toString());
      }
    }
    return result;
  }

  @Override
  public Element serialize(final Document doc, final Object obj)
      throws XmlException {
    final BasicValue var;
    try {
      var = (BasicValue) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = TypeUtils.toXmlNode(var.type, var.value, doc,
        ROOT_NODE, PRESERVE_SPACE_ATTRIBUTE);
    setOptEnumAttr(root, TYPE_ATTRIBUTE, true, var.type,
        BasicValue.DEFAULT_TYPE);
    return root;
  }
}
