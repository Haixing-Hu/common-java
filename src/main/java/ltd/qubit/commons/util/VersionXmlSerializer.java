////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.text.ParseException;

import javax.annotation.concurrent.Immutable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.io.io.serialize.XmlSerializer;
import ltd.qubit.commons.text.xml.DomUtils;
import ltd.qubit.commons.text.xml.InvalidXmlNodeContentException;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlSerializationException;

/**
 * The {@link XmlSerializer} for the {@link Version} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class VersionXmlSerializer implements XmlSerializer {

  public static final String  ROOT_NODE                 = "version";

  public static final VersionXmlSerializer INSTANCE = new VersionXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

  @Override
  public Version deserialize(final Element root) throws XmlException {
    DomUtils.checkNode(root, ROOT_NODE);
    final String str = DomUtils
            .getReqString(root, DomUtils.PRESERVE_SPACE_ATTRIBUTE, false, false);
    try {
      return new Version(str);
    } catch (final ParseException e) {
      throw new InvalidXmlNodeContentException(ROOT_NODE, str);
    }
  }

  @Override
  public Element serialize(final Document doc, final Object obj) throws XmlException {
    final Version version;
    try {
      version = (Version) obj;
    } catch (final ClassCastException e) {
      throw new XmlSerializationException(e);
    }
    final Element result = doc.createElement(ROOT_NODE);
    result.setTextContent(version.toString());
    return result;
  }

}
