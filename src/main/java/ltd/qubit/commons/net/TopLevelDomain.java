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

import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 顶级域名（TLD）是互联网域名的最后部分；即跟在域名最后一个点后面的字母。
 *
 * <p>例如，在域名 {@code www.website.com} 中，顶级域名是 {@code com}。
 *
 * @author 胡海星
 * @see <a href="http://www.iana.org/">INNA</a>
 * @see <a href="http://en.wikipedia.org/wiki/Top-level_domain">Top-Level Domain</a>
 */
@Immutable
public final class TopLevelDomain extends DomainSuffix {

  private static final long serialVersionUID = - 3880755857023154643L;

  public enum Type {
    INFRASTRUCTURE,
    GENERIC,
    COUNTRY
  }

  Type type;
  String country;

  TopLevelDomain() {
    type = Type.GENERIC;
    country = StringUtils.EMPTY;
  }

  TopLevelDomain(final String domain, final Status status,
      final String description, final Type type, final String country) {
    super(domain, status, description);
    this.type = type;
    this.country = country;
  }

  public Type getType() {
    return type;
  }

  @Override
  public boolean isTopLevel() {
    return true;
  }

  /**
   * 如果此TLD是国家代码TLD，则返回国家名称。
   *
   * @return 国家名称或null
   */
  public String getCountry() {
    return country;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("domain", domain)
               .append("status", status)
               .append("description", description)
               .append("type", type)
               .append("country", country)
               .toString();
  }
}
