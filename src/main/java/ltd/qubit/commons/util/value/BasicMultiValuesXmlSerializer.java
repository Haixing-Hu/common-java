////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.value;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.serialize.XmlSerializer;
import ltd.qubit.commons.lang.Type;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlSerializationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static ltd.qubit.commons.text.xml.DomUtils.checkNode;
import static ltd.qubit.commons.text.xml.DomUtils.getOptEnumAttr;
import static ltd.qubit.commons.text.xml.DomUtils.setOptEnumAttr;

/**
 * The {@link XmlSerializer} for the {@link BasicMultiValues} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class BasicMultiValuesXmlSerializer implements XmlSerializer {

  public static final BasicMultiValuesXmlSerializer INSTANCE =
      new BasicMultiValuesXmlSerializer();

  public static final String ROOT_NODE = "multi-values";

  public static final String TYPE_ATTRIBUTE = "type";

  public static final String VALUE_NODE = "value";

  public static final String PRESERVE_SPACE_ATTRIBUTE = "preserve-space";

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public BasicMultiValues deserialize(final Element root) throws XmlException {
    checkNode(root, ROOT_NODE);
    final BasicMultiValues result = new BasicMultiValues();
    final Type type = getOptEnumAttr(root, TYPE_ATTRIBUTE, true, Type.class,
        MultiValues.DEFAULT_TYPE);
    result.getValuesFromXml(type, root, VALUE_NODE,
        PRESERVE_SPACE_ATTRIBUTE);
    return result;
  }

  @Override
  public Element serialize(final Document doc, final Object obj)
      throws XmlException {
    final BasicMultiValues var;
    try {
      var = (BasicMultiValues) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element root = doc.createElement(ROOT_NODE);
    setOptEnumAttr(root, TYPE_ATTRIBUTE, true, var.getType(),
        MultiValues.DEFAULT_TYPE);
    var.appendValuesToXml(doc, root, null, VALUE_NODE,
        PRESERVE_SPACE_ATTRIBUTE);
    return root;
  }

}
