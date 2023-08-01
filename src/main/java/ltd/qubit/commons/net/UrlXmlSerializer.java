////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import ltd.qubit.commons.io.serialize.XmlSerializer;
import ltd.qubit.commons.text.xml.DomUtils;
import ltd.qubit.commons.text.xml.InvalidXmlNodeContentException;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlSerializationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.net.MalformedURLException;
import javax.annotation.concurrent.Immutable;

/**
 * The {@link XmlSerializer} for the {@link Url} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class UrlXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "url";

  public static final UrlXmlSerializer INSTANCE = new UrlXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

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
