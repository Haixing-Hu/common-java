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
    /**
     * 基础设施域。
     */
    INFRASTRUCTURE,

    /**
     * 赞助域。
     */
    SPONSORED,

    /**
     * 未赞助域。
     */
    UNSPONSORED,

    /**
     * 启动域。
     */
    STARTUP,

    /**
     * 提议域。
     */
    PROPOSED,

    /**
     * 已删除域。
     */
    DELETED,

    /**
     * 伪域。
     */
    PSEUDO_DOMAIN,

    /**
     * 已弃用域。
     */
    DEPRECATED,

    /**
     * 正在使用域。
     */
    IN_USE,

    /**
     * 未使用域。
     */
    NOT_IN_USE,

    /**
     * 已拒绝域。
     */
    REJECTED,
  }

  /**
   * 默认状态。
   */
  public static final Status DEFAULT_STATUS = Status.IN_USE;

  /**
   * 域名。
   */
  final String domain;

  /**
   * 状态。
   */
  final Status status;

  /**
   * 描述。
   */
  final String description;

  /**
   * 构造一个新的域后缀。
   */
  DomainSuffix() {
    domain = StringUtils.EMPTY;
    status = DEFAULT_STATUS;
    description = StringUtils.EMPTY;
  }

  /**
   * 构造一个新的域后缀。
   *
   * @param domain
   *     域名。
   * @param status
   *     状态。
   * @param description
   *     描述。
   */
  DomainSuffix(final String domain, final Status status, final String description) {
    this.domain = domain;
    this.status = status;
    this.description = description;
  }

  /**
   * 获取域名。
   *
   * @return 域名。
   */
  public String getDomain() {
    return domain;
  }

  /**
   * 获取状态。
   *
   * @return 状态。
   */
  public Status getStatus() {
    return status;
  }

  /**
   * 获取描述。
   *
   * @return 描述。
   */
  public String getDescription() {
    return description;
  }

  /**
   * 判断是否为顶级域。
   *
   * @return 是否为顶级域。
   */
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