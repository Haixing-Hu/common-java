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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ltd.qubit.commons.CommonsConfig;
import ltd.qubit.commons.concurrent.Lazy;
import ltd.qubit.commons.config.Config;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.xml.DomUtils;
import ltd.qubit.commons.text.xml.InvalidXmlNodeContentException;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlUtils;

import static ltd.qubit.commons.net.TopLevelDomain.Type.GENERIC;
import static ltd.qubit.commons.net.TopLevelDomain.Type.INFRASTRUCTURE;

/**
 * {@link DomainSuffixRegistry} 对象存储 {@link DomainSuffix} 对象的映射。
 *
 * @author 胡海星
 */
@Immutable
public final class DomainSuffixRegistry {

  /**
   * 此属性的值是 {@link DomainSuffixRegistry} 类的XML资源文件名。
   *
   * <table style="border-collapse:collapse;">
   * <caption>域名后缀XML资源文件名的属性</caption>
   * <tr>
   * <th style="border:1px solid;padding:0.5rem;">类型</th>
   * <th style="border:1px solid;padding:0.5rem;">数量</th>
   * <th style="border:1px solid;padding:0.5rem;">值</th>
   * <th style="border:1px solid;padding:0.5rem;">必需</th>
   * <th style="border:1px solid;padding:0.5rem;">默认值</th>
   * <th style="border:1px solid;padding:0.5rem;">范围</th>
   * </tr>
   * <tr>
   * <td style="border:1px solid;padding:0.5rem;">String</td>
   * <td style="border:1px solid;padding:0.5rem;">1</td>
   * <td style="border:1px solid;padding:0.5rem;">{@link DomainSuffixRegistry} 类的XML
   * 资源文件名。</td>
   * <td style="border:1px solid;padding:0.5rem;">否</td>
   * <td style="border:1px solid;padding:0.5rem;">{@link #DEFAULT_RESOURCE}</td>
   * <td style="border:1px solid;padding:0.5rem;"></td>
   * </tr>
   * </table>
   *
   * @see #DEFAULT_RESOURCE
   */
  public static final String PROPERTY_RESOURCE = "DomainSuffixRegistry.resource";

  /**
   * 属性 {@link #PROPERTY_RESOURCE} 的默认值。
   *
   * @see #PROPERTY_RESOURCE
   */
  public static final String DEFAULT_RESOURCE = "domain-suffixes.xml";

  public static final String ROOT_NODE = "domains";

  public static final String SUFFIXES_NODE = "suffixes";

  public static final String TLDS_NODE = "tlds";

  public static final String ITLDS_NODE = "itlds";

  public static final String GTLDS_NODE = "gtlds";

  public static final String CCTLDS_NODE = "cctlds";

  public static final String TLD_NODE = "tld";

  public static final String STATUS_NODE = "status";

  public static final String DESCRIPTION_NODE = "descrption";

  public static final String COUNTRY_NODE = "country";

  public static final String SUFFIX_NODE = "suffix";

  public static final String DOMAIN_ATTRIBUTE = "domain";

  private static final Logger LOGGER = LoggerFactory
      .getLogger(DomainSuffixRegistry.class);

  private static final Lazy<DomainSuffixRegistry> lazy = Lazy.of(() -> {
    final Config config = CommonsConfig.get();
    final String resource = config.getString(PROPERTY_RESOURCE, DEFAULT_RESOURCE);
    return new DomainSuffixRegistry(resource);
  });

  /**
   * 获取DomainSuffixes类的单例实例，使用懒加载实例化。
   *
   * @return DomainSuffixes类的单例实例。
   */
  public static DomainSuffixRegistry getInstance() {
    return lazy.get();
  }

  private final HashMap<String, DomainSuffix> domains;

  /**
   * 使用指定的配置资源名称创建一个新的DomainSuffixRegistry实例。
   *
   * @param configResourceName
   *     配置资源名称。
   */
  public DomainSuffixRegistry(final String configResourceName) {
    domains = new HashMap<>();
    final ClassLoader classLoader = DomainSuffixRegistry.class.getClassLoader();
    final URL configResourceUrl = classLoader.getResource(configResourceName);
    if (configResourceUrl == null) {
      LOGGER.error("Can't find the domain suffixes configuration file: {}.",
          configResourceName);
    } else {
      loadFromResource(configResourceUrl);
    }
  }

  /**
   * 使用指定的配置资源URL创建一个新的DomainSuffixRegistry实例。
   *
   * @param configResourceUrl
   *     配置资源URL。
   */
  public DomainSuffixRegistry(final URL configResourceUrl) {
    domains = new HashMap<>();
    loadFromResource(configResourceUrl);
  }

  /**
   * 测试扩展名是否为已注册的域条目。
   *
   * @param extension
   *     扩展名。
   * @return 如果扩展名是已注册的域条目则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isDomainSuffix(final String extension) {
    return domains.containsKey(extension);
  }

  /**
   * 测试扩展名是否为顶级域条目。
   *
   * @param domain
   *     域名。
   * @return 如果扩展名是顶级域条目则返回 {@code true}；否则返回 {@code false}。
   */
  public boolean isTopLevelDomain(final String domain) {
    final DomainSuffix suffix = domains.get(domain);
    return (suffix != null) && suffix.isTopLevel();
  }

  /**
   * 返回扩展名对应的 {@link DomainSuffix} 对象，如果扩展名是顶级域，
   * 返回的对象将是 {@link TopLevelDomain} 的实例。
   *
   * @param extension
   *     域的扩展名。
   * @return 扩展名对应的 {@link DomainSuffix} 对象。
   */
  public DomainSuffix getDomainSuffix(final String extension) {
    return domains.get(extension);
  }

  /**
   * 返回扩展名对应的 {@link TopLevelDomain} 对象，如果扩展名不是顶级域则返回null。
   *
   * @param extension
   *     顶级域的扩展名。
   * @return 扩展名对应的 {@link DomainSuffix} 对象。
   */
  public TopLevelDomain getTopLevelDomain(final String extension) {
    final DomainSuffix suffix = domains.get(extension);
    if ((suffix != null) && (suffix instanceof TopLevelDomain)) {
      return (TopLevelDomain) suffix;
    } else {
      return null;
    }
  }

  /**
   * Lists all domain suffixes.
   *
   * @return the list of all domain suffixes.
   */
  public List<DomainSuffix> list() {
    return new ArrayList<>(domains.values());
  }

  private void loadFromResource(final URL configResourceUrl) {
    try {
      final Document doc = XmlUtils.parse(configResourceUrl);
      parse(doc.getDocumentElement());
    } catch (final XmlException e) {
      LOGGER.error("Parsring {} failed.", configResourceUrl, e);
    }
  }

  private void parse(final Element root) throws XmlException {
    DomUtils.checkNode(root, ROOT_NODE);
    List<Element> nodeList = DomUtils.getReqChildren(root, 2, 2, null);
    final Element tldsNode = nodeList.get(0);
    DomUtils.checkNode(tldsNode, TLDS_NODE);
    final Element suffixesNode = nodeList.get(1);
    DomUtils.checkNode(suffixesNode, SUFFIXES_NODE);
    nodeList = DomUtils.getReqChildren(tldsNode, 3, 3, nodeList);
    final Element itldsNode = nodeList.get(0);
    DomUtils.checkNode(itldsNode, ITLDS_NODE);
    final Element gtldsNode = nodeList.get(1);
    DomUtils.checkNode(gtldsNode, GTLDS_NODE);
    final Element cctldsNode = nodeList.get(2);
    DomUtils.checkNode(cctldsNode, CCTLDS_NODE);
    parseItlds(itldsNode, nodeList);
    parseGtlds(gtldsNode, nodeList);
    parseCctlds(cctldsNode, nodeList);
    parseSuffixes(suffixesNode, nodeList);
  }

  private void parseItlds(final Element itlds, final List<Element> nodeList)
      throws XmlException {
    LOGGER.trace("Parsing <itlds>.");
    final List<Element> list = DomUtils.getChildren(itlds, nodeList);
    if ((list != null) && (!list.isEmpty())) {
      for (final Element node : list) {
        DomUtils.checkNode(node, TLD_NODE);
        final TopLevelDomain tld = parseGtld(node, INFRASTRUCTURE);
        domains.put(tld.domain, tld);
        LOGGER.trace("Parsed a <itld>: {}", tld);
      }
    }
  }

  private void parseGtlds(final Element gtldsNode, final List<Element> nodeList)
      throws XmlException {
    LOGGER.trace("Parsing <gtlds>.");
    final List<Element> list = DomUtils.getChildren(gtldsNode, nodeList);
    if ((list != null) && (!list.isEmpty())) {
      for (final Element tldNode : list) {
        DomUtils.checkNode(tldNode, TLD_NODE);
        final TopLevelDomain tld = parseGtld(tldNode, GENERIC);
        domains.put(tld.domain, tld);
        LOGGER.trace("Parsed a <gtld>: {}", tld);
      }
    }
  }

  private void parseCctlds(final Element cctldsNode,
      final List<Element> nodeList)
      throws XmlException {
    LOGGER.trace("Parsing <cctlds>.");
    final List<Element> list = DomUtils.getChildren(cctldsNode, nodeList);
    if ((list != null) && (!list.isEmpty())) {
      for (final Element tldNode : list) {
        DomUtils.checkNode(tldNode, TLD_NODE);
        final TopLevelDomain tld = parseCctld(tldNode);
        domains.put(tld.domain, tld);
        LOGGER.trace("Parsed a <cctld>: {}", tld);
      }
    }
  }

  private TopLevelDomain parseGtld(final Element tldNode,
      final TopLevelDomain.Type type) throws XmlException {
    final String domain = DomUtils.getReqStringAttr(tldNode, DOMAIN_ATTRIBUTE,
        true, false);
    final DomainSuffix.Status status = parseStatus(tldNode);
    final String description = parseDescription(tldNode);
    return new TopLevelDomain(domain, status, description, type,
        StringUtils.EMPTY);
  }

  private TopLevelDomain parseCctld(final Element cctldNode)
      throws XmlException {
    final String domain = DomUtils.getReqStringAttr(cctldNode,
        DOMAIN_ATTRIBUTE, true, false);
    final String country = parseCountry(cctldNode);
    final DomainSuffix.Status status = parseRestrictedStatus(cctldNode);
    final String description = parseDescription(cctldNode);
    return new TopLevelDomain(domain, status, description,
        TopLevelDomain.Type.COUNTRY, country);
  }

  private DomainSuffix.Status parseStatus(final Element tldNode)
      throws XmlException {
    final Element statusNode = DomUtils.getOptChild(tldNode, STATUS_NODE);
    if (statusNode == null) {
      return DomainSuffix.DEFAULT_STATUS;
    }
    final String statusName = DomUtils.getReqString(statusNode, null, true,
        false);
    try {
      return DomainSuffix.Status.valueOf(statusName);
    } catch (final IllegalArgumentException e) {
      throw new InvalidXmlNodeContentException(STATUS_NODE, statusName);
    }
  }

  private String parseDescription(final Element tldNode) throws XmlException {
    final Element descriptionNode = DomUtils.getOptChild(tldNode,
        DESCRIPTION_NODE);
    if (descriptionNode == null) {
      return StringUtils.EMPTY;
    } else {
      return DomUtils.getOptString(descriptionNode, null, true,
          StringUtils.EMPTY);
    }
  }

  private String parseCountry(final Element tldNode) throws XmlException {
    final Element countryNode = DomUtils.getReqChild(tldNode, COUNTRY_NODE);
    return DomUtils.getReqString(countryNode, null, true, false);
  }

  private DomainSuffix.Status parseRestrictedStatus(final Element tldNode)
      throws XmlException {
    final Element statusNode = DomUtils.getOptChild(tldNode, STATUS_NODE);
    if (statusNode == null) {
      return DomainSuffix.DEFAULT_STATUS;
    }
    final String statusName = DomUtils.getReqString(statusNode, null, true,
        false);
    final DomainSuffix.Status status;
    try {
      status = DomainSuffix.Status.valueOf(statusName);
    } catch (final IllegalArgumentException e) {
      throw new InvalidXmlNodeContentException(STATUS_NODE, statusName);
    }
    switch (status) {
      case IN_USE:
      case NOT_IN_USE:
      case DELETED:
        return status;
      default:
        throw new InvalidXmlNodeContentException(STATUS_NODE, statusName);
    }
  }

  private void parseSuffixes(final Element suffixesNode,
      final List<Element> nodeList)
      throws XmlException {
    LOGGER.trace("Parsing <suffixes> ...");
    final List<Element> list = DomUtils.getChildren(suffixesNode, nodeList);
    if ((list != null) && (!list.isEmpty())) {
      for (final Element suffixNode : list) {
        DomUtils.checkNode(suffixNode, SUFFIX_NODE);
        final DomainSuffix suffix = parseSuffix(suffixNode);
        domains.put(suffix.domain, suffix);
        LOGGER.trace("Parsed a <suffix>: {}", suffix);
      }
    }
  }

  private DomainSuffix parseSuffix(final Element suffixNode)
      throws XmlException {
    final String domain = DomUtils.getReqStringAttr(suffixNode,
        DOMAIN_ATTRIBUTE, true, false);
    final DomainSuffix.Status status = parseRestrictedStatus(suffixNode);
    final String description = parseDescription(suffixNode);
    return new DomainSuffix(domain, status, description);
  }
}