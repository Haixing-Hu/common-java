////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.Serializable;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此类表示主机名的最后部分，由权威机构而非个人运营。此信息用于查找主机的域名。
 * 主机的域名定义为域名后缀之前的最后部分，不包括子域名。例如，
 * {@code http://www.sina.com.cn/} 的域名是 {@code sina.com.cn}。
 *
 * <p>此类包含两个字段，<strong>domain</strong> 字段表示后缀（如"co.uk"），
 * <strong>status</strong> 字段表示域的状态。
 *
 * @author 胡海星
 */
@Immutable
public class DomainSuffix implements Serializable {

  private static final long serialVersionUID = - 891087570899663847L;

  /**
   * 顶级域状态的枚举。
   */
  public enum Status {
    INFRASTRUCTURE,
    SPONSORED,
    UNSPONSORED,
    STARTUP,
    PROPOSED,
    DELETED,
    PSEUDO_DOMAIN,
    DEPRECATED,
    IN_USE,
    NOT_IN_USE,
    REJECTED,
  }

  public static final Status DEFAULT_STATUS = Status.IN_USE;

  String domain;
  Status status;
  String description;

  DomainSuffix() {
    domain = StringUtils.EMPTY;
    status = DEFAULT_STATUS;
    description = StringUtils.EMPTY;
  }

  DomainSuffix(final String domain, final Status status,
      final String description) {
    this.domain = domain;
    this.status = status;
    this.description = description;
  }

  public String getDomain() {
    return domain;
  }

  public Status getStatus() {
    return status;
  }

  public String getDescription() {
    return description;
  }

  public boolean isTopLevel() {
    return false;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("domain", domain)
               .append("status", status)
               .append("description", description)
               .toString();
  }
}
