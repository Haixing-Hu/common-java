////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.CommonsConfig;
import ltd.qubit.commons.config.Config;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.text.xml.DomUtils;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A {@link DefaultPorts} object stores a map between the scheme and its
 * default port.
 *
 * @author Haixing Hu
 */
@Immutable
public final class DefaultPorts {

  /**
   * The value of this property is the name of the XML resource file of the
   * {@link DefaultPorts} class.
   *
   * <table style="border-collapse:collapse;">
   *    <caption>The property of the XML resource filename of default ports</caption>
   *    <thead>
   *      <tr>
   *        <th style="border:1px solid;padding:0.5rem;">Type</th>
   *        <th style="border:1px solid;padding:0.5rem;">Count</th>
   *        <th style="border:1px solid;padding:0.5rem;">Value</th>
   *        <th style="border:1px solid;padding:0.5rem;">Required</th>
   *        <th style="border:1px solid;padding:0.5rem;">Default</th>
   *        <th style="border:1px solid;padding:0.5rem;">Range</th>
   *      </tr>
   *    </thead>
   *    <tbody>
   *      <tr>
   *        <td style="border:1px solid;padding:0.5rem;">String</td>
   *        <td style="border:1px solid;padding:0.5rem;">1</td>
   *        <td style="border:1px solid;padding:0.5rem;">the name of the XML
   *        resource file of the {@link DefaultPorts} class.</td>
   *        <td style="border:1px solid;padding:0.5rem;">no</td>
   *        <td style="border:1px solid;padding:0.5rem;">{@link #DEFAULT_RESOURCE}</td>
   *        <td style="border:1px solid;padding:0.5rem;"></td>
   *      </tr>
   *    </tbody>
   * </table>
   *
   * @see #DEFAULT_RESOURCE
   */
  public static final String PROPERTY_RESOURCE = "DefaultPorts.resource";

  /**
   * The default value of the property {@link #PROPERTY_RESOURCE}.
   *
   * @see #PROPERTY_RESOURCE
   */
  public static final String DEFAULT_RESOURCE = "default-ports.xml";

  public static final String ROOT_NODE = "default-ports";

  public static final String DEFAULT_PORT_NODE = "default-port";

  public static final String SCHEME_NODE = "scheme";

  public static final String PORT_NODE = "port";

  private static volatile Map<String, Integer> ports = null;

  /**
   * Gets the default port number of a specified scheme.
   *
   * @param scheme
   *          a specified scheme.
   * @return the default port number of the specified scheme; or -1 if no such
   *         default port for the specified scheme.
   */
  public static int get(final String scheme) {
    if (ports == null) {
      synchronized (DefaultPorts.class) {
        if (ports == null) {
          final Config config = CommonsConfig.get();
          final String resource = config.getString(PROPERTY_RESOURCE,
              DEFAULT_RESOURCE);
          loadDefaultPorts(resource);
        }
      }
    }
    final Integer result = ports.get(scheme);
    return (result != null ? result.intValue() : - 1);
  }

  private static void loadDefaultPorts(final String resource) {
    ports = new HashMap<>();
    final URL url = SystemUtils.getResource(resource, DefaultPorts.class);
    if (url == null) {
      final Logger logger = LoggerFactory.getLogger(DefaultPorts.class);
      logger.error("Can't find the default ports resource file: {}",
          resource);
      return;
    }
    try {
      final Document doc = XmlUtils.parse(url);
      parse(doc.getDocumentElement());
    } catch (final XmlException e) {
      final Logger logger = LoggerFactory.getLogger(DefaultPorts.class);
      logger.error("Failed to load the default ports from {}.", resource, e);
    }
  }

  private static void parse(final Element root) throws XmlException {
    DomUtils.checkNode(root, ROOT_NODE);
    final List<Element> nodeList = DomUtils.getChildren(root, null);
    if ((nodeList == null) || nodeList.isEmpty()) {
      return;
    }
    for (final Element node : nodeList) {
      DomUtils.checkNode(node, DEFAULT_PORT_NODE);
      final String scheme = DomUtils
              .getReqStringChild(node, SCHEME_NODE, null, true, false);
      final int port = DomUtils.getReqIntChild(node, PORT_NODE);
      ports.put(scheme, port);
    }
  }

}
