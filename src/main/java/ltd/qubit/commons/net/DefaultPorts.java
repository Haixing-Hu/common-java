////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.CommonsConfig;
import ltd.qubit.commons.config.Config;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.text.xml.DomUtils;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlUtils;

/**
 * {@link DefaultPorts} 对象存储方案与其默认端口之间的映射。
 *
 * @author 胡海星
 */
@Immutable
public final class DefaultPorts {

  /**
   * 此属性的值是 {@link DefaultPorts} 类的XML资源文件名。
   *
   * <table style="border-collapse:collapse;">
   *    <caption>默认端口XML资源文件名的属性</caption>
   *    <thead>
   *      <tr>
   *        <th style="border:1px solid;padding:0.5rem;">类型</th>
   *        <th style="border:1px solid;padding:0.5rem;">数量</th>
   *        <th style="border:1px solid;padding:0.5rem;">值</th>
   *        <th style="border:1px solid;padding:0.5rem;">必需</th>
   *        <th style="border:1px solid;padding:0.5rem;">默认值</th>
   *        <th style="border:1px solid;padding:0.5rem;">范围</th>
   *      </tr>
   *    </thead>
   *    <tbody>
   *      <tr>
   *        <td style="border:1px solid;padding:0.5rem;">String</td>
   *        <td style="border:1px solid;padding:0.5rem;">1</td>
   *        <td style="border:1px solid;padding:0.5rem;">{@link DefaultPorts} 类的XML
   *        资源文件名。</td>
   *        <td style="border:1px solid;padding:0.5rem;">否</td>
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
   * 属性 {@link #PROPERTY_RESOURCE} 的默认值。
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
   * 获取指定方案的默认端口号。
   *
   * @param scheme
   *          指定的方案。
   * @return 指定方案的默认端口号；如果指定方案没有默认端口则返回-1。
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
