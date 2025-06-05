////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.Serial;

import javax.annotation.Nullable;
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

  @Serial
  private static final long serialVersionUID = - 3880755857023154643L;

  /**
   * 顶级域名类型。
   */
  public enum Type {
    /**
     * 基础设施域名。
     */
    INFRASTRUCTURE,

    /**
     * 通用域名。
     */
    GENERIC,

    /**
     * 国家代码域名。
     */
    COUNTRY,
  }

  /**
   * 顶级域名类型。
   */
  final Type type;

  /**
   * 国家名称。
   */
  @Nullable
  final String country;

  TopLevelDomain() {
    type = Type.GENERIC;
    country = StringUtils.EMPTY;
  }

  TopLevelDomain(final String domain, final Status status,
      final String description, final Type type, @Nullable final String country) {
    super(domain, status, description);
    this.type = type;
    this.country = country;
  }

  /**
   * 获取顶级域名类型。
   *
   * @return
   *     顶级域名类型。
   */
  public Type getType() {
    return type;
  }

  /**
   * 判断是否为顶级域名。
   *
   * @return
   *     是否为顶级域名。
   */
  @Override
  public boolean isTopLevel() {
    return true;
  }

  /**
   * 如果此TLD是国家代码TLD，则返回国家名称。
   *
   * @return 国家名称或null
   */
  @Nullable
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