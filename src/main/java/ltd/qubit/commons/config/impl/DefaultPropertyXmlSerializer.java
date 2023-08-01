////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.impl;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.serialize.XmlSerializer;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlSerializationException;
import ltd.qubit.commons.util.value.MultiValues;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static ltd.qubit.commons.text.xml.DomUtils.appendOptStringChild;
import static ltd.qubit.commons.text.xml.DomUtils.checkNode;
import static ltd.qubit.commons.text.xml.DomUtils.getOptBooleanAttr;
import static ltd.qubit.commons.text.xml.DomUtils.getOptEnumAttr;
import static ltd.qubit.commons.text.xml.DomUtils.getOptStringChild;
import static ltd.qubit.commons.text.xml.DomUtils.getReqStringAttr;
import static ltd.qubit.commons.text.xml.DomUtils.setOptBooleanAttr;
import static ltd.qubit.commons.text.xml.DomUtils.setOptEnumAttr;
import static ltd.qubit.commons.text.xml.DomUtils.setReqStringAttr;

/**
 * The {@link XmlSerializer} for the {@link DefaultProperty} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DefaultPropertyXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "property";
  public static final String NAME_ATTRIBUTE = "name";
  public static final String TYPE_ATTRIBUTE = "type";
  public static final String VALUE_NODE = "value";
  public static final String IS_FINAL_ATTRIBUTE = "final";
  public static final String PRESERVE_SPACE_ATTRIBUTE = "preserve-space";
  public static final String DESCRIPTION_NODE = "description";

  public static final DefaultPropertyXmlSerializer INSTANCE = new DefaultPropertyXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public DefaultProperty deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final String name = getReqStringAttr(root, NAME_ATTRIBUTE, false, false);
    final boolean isFinal = getOptBooleanAttr(root, IS_FINAL_ATTRIBUTE,
            DefaultProperty.DEFAULT_IS_FINAL);
    final Type type = getOptEnumAttr(root, TYPE_ATTRIBUTE, true, Type.class,
        MultiValues.DEFAULT_TYPE);
    final String description = getOptStringChild(root, DESCRIPTION_NODE,
        PRESERVE_SPACE_ATTRIBUTE, DefaultProperty.DEFAULT_TRIM, null);
    final DefaultProperty result = new DefaultProperty(name);
    result.setFinal(isFinal);
    result.setDescription(description);
    result.setType(type);
    result.getValuesFromXml(type, root, VALUE_NODE, PRESERVE_SPACE_ATTRIBUTE);
    return result;
  }

  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    final DefaultProperty prop;
    try {
      prop = (DefaultProperty) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    setReqStringAttr(root, NAME_ATTRIBUTE, prop.getName());
    setOptEnumAttr(root, TYPE_ATTRIBUTE, true, prop.getType(), MultiValues.DEFAULT_TYPE);
    setOptBooleanAttr(root, IS_FINAL_ATTRIBUTE, prop.isFinal(), DefaultProperty.DEFAULT_IS_FINAL);
    appendOptStringChild(doc, root, DESCRIPTION_NODE, PRESERVE_SPACE_ATTRIBUTE,
        prop.getDescription());
    prop.appendValuesToXml(doc, root, null, VALUE_NODE, PRESERVE_SPACE_ATTRIBUTE);
    return root;
  }
}
