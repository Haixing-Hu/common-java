////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * The {@link XmlSerializer} for the {@link Host} class.
 *
 * @author Haixing Hu
 */
@Immutable
public final class HostXmlSerializer implements XmlSerializer {

  public static final String ROOT_NODE = "host";
  public static final String SCHEME_NODE = "scheme";
  public static final String HOSTNAME_NODE = "hostname";
  public static final String PORT_NODE = "port";

  public static final HostXmlSerializer INSTANCE = new HostXmlSerializer();

  @Override
  public String getRootNodeName() {
    return ROOT_NODE;
  }

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
