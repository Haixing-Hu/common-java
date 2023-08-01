////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.security;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.time.Instant;
import javax.annotation.Nullable;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * 此模型表示第三方应用。
 *
 * @author 胡海星
 */
@XmlRootElement(name = "app")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect(fieldVisibility = ANY,
                getterVisibility = NONE,
                isGetterVisibility = NONE,
                setterVisibility = NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class App implements Serializable {

  private static final long serialVersionUID = -4130818293179381259L;

  /**
   * 本系统所对应的 App 的代码。
   */
  public static final String SYSTEM_APP_CODE = "system";

  /**
   * 唯一标识，系统自动生成。
   */
  private Long id;

  /**
   * 名称，同一机构下不可重复。
   */
  private String name;

  /**
   * 代码，全局不可重复，一旦设置不能更改。
   */
  private String code;

  /**
   * 所属机构ID。
   */
  private Long organizationId;

  /**
   * 所属机构名称。
   */
  @Nullable
  private String organizationName;

  /**
   * 所属机构代码。
   */
  @Nullable
  private String organizationCode;

  /**
   * 所属类别ID。
   */
  @Nullable
  private Long categoryId;

  /**
   * 所属类别名称。
   */
  @Nullable
  private String categoryName;

  /**
   * 图标。
   */
  @Nullable
  private String icon;

  /**
   * 网址 URL。
   */
  @Nullable
  private String url;

  /**
   * 描述。
   */
  private String description;

  /**
   * 安全秘钥，从数据库中读取出来的是秘钥加盐后的哈希值。
   */
  private String securityKey;

  /**
   * 访问令牌。
   */
  @Nullable
  private String token;

  /**
   * 访问令牌创建时间。
   */
  @Nullable
  private Instant tokenCreateTime;

  /**
   * 访问令牌过期时间。
   */
  @Nullable
  private Instant tokenExpiredTime;

  /**
   * RSA公钥。
   */
  private String rsaPublicKey;

  /**
   * RSA私钥。
   */
  private String rsaPrivateKey;

  /**
   * 是否是预定义的数据。
   */
  private boolean predefined;

  /**
   * 创建时间。
   */
  private Instant createTime;

  /**
   * 最后一次修改时间。
   */
  @Nullable
  private Instant modifyTime;

  /**
   * 删除时间。
   */
  @Nullable
  private Instant deleteTime;

  public final Long getId() {
    return id;
  }

  public final App setId(final Long id) {
    this.id = id;
    return this;
  }

  public final String getName() {
    return name;
  }

  public final App setName(final String name) {
    this.name = name;
    return this;
  }

  @Nullable
  public final String getCode() {
    return code;
  }

  public final App setCode(@Nullable final String code) {
    this.code = code;
    return this;
  }

  public final Long getOrganizationId() {
    return organizationId;
  }

  public final App setOrganizationId(final Long organizationId) {
    this.organizationId = organizationId;
    return this;
  }

  @Nullable
  public final String getOrganizationName() {
    return organizationName;
  }

  public final App setOrganizationName(@Nullable final String organizationName) {
    this.organizationName = organizationName;
    return this;
  }

  @Nullable
  public final String getOrganizationCode() {
    return organizationCode;
  }

  public final App setOrganizationCode(@Nullable final String organizationCode) {
    this.organizationCode = organizationCode;
    return this;
  }

  @Nullable
  public final Long getCategoryId() {
    return categoryId;
  }

  public final App setCategoryId(@Nullable final Long categoryId) {
    this.categoryId = categoryId;
    return this;
  }

  @Nullable
  public final String getCategoryName() {
    return categoryName;
  }

  public final App setCategoryName(@Nullable final String categoryName) {
    this.categoryName = categoryName;
    return this;
  }

  @Nullable
  public final String getIcon() {
    return icon;
  }

  public final App setIcon(@Nullable final String icon) {
    this.icon = icon;
    return this;
  }

  @Nullable
  public final String getUrl() {
    return url;
  }

  public final App setUrl(@Nullable final String url) {
    this.url = url;
    return this;
  }

  public final String getDescription() {
    return description;
  }

  public final App setDescription(final String description) {
    this.description = description;
    return this;
  }

  public final String getSecurityKey() {
    return securityKey;
  }

  public final App setSecurityKey(final String securityKey) {
    this.securityKey = securityKey;
    return this;
  }

  @Nullable
  public final String getToken() {
    return token;
  }

  public final App setToken(@Nullable final String token) {
    this.token = token;
    return this;
  }

  @Nullable
  public final Instant getTokenCreateTime() {
    return tokenCreateTime;
  }

  public final App setTokenCreateTime(@Nullable final Instant tokenCreateTime) {
    this.tokenCreateTime = tokenCreateTime;
    return this;
  }

  @Nullable
  public final Instant getTokenExpiredTime() {
    return tokenExpiredTime;
  }

  public final App setTokenExpiredTime(@Nullable final Instant tokenExpiredTime) {
    this.tokenExpiredTime = tokenExpiredTime;
    return this;
  }

  public final String getRsaPublicKey() {
    return rsaPublicKey;
  }

  public final App setRsaPublicKey(final String rsaPublicKey) {
    this.rsaPublicKey = rsaPublicKey;
    return this;
  }

  public final String getRsaPrivateKey() {
    return rsaPrivateKey;
  }

  public final App setRsaPrivateKey(final String rsaPrivateKey) {
    this.rsaPrivateKey = rsaPrivateKey;
    return this;
  }

  public final boolean isPredefined() {
    return predefined;
  }

  public final App setPredefined(final boolean predefined) {
    this.predefined = predefined;
    return this;
  }

  public final Instant getCreateTime() {
    return createTime;
  }

  public final App setCreateTime(final Instant createTime) {
    this.createTime = createTime;
    return this;
  }

  @Nullable
  public final Instant getModifyTime() {
    return modifyTime;
  }

  public final App setModifyTime(@Nullable final Instant modifyTime) {
    this.modifyTime = modifyTime;
    return this;
  }

  @Nullable
  public final Instant getDeleteTime() {
    return deleteTime;
  }

  public final App setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final App other = (App) o;
    return Equality.equals(predefined, other.predefined)
            && Equality.equals(id, other.id)
            && Equality.equals(name, other.name)
            && Equality.equals(code, other.code)
            && Equality.equals(organizationId, other.organizationId)
            && Equality.equals(organizationName, other.organizationName)
            && Equality.equals(organizationCode, other.organizationCode)
            && Equality.equals(categoryId, other.categoryId)
            && Equality.equals(categoryName, other.categoryName)
            && Equality.equals(icon, other.icon)
            && Equality.equals(url, other.url)
            && Equality.equals(description, other.description)
            && Equality.equals(securityKey, other.securityKey)
            && Equality.equals(token, other.token)
            && Equality.equals(tokenCreateTime, other.tokenCreateTime)
            && Equality.equals(tokenExpiredTime, other.tokenExpiredTime)
            && Equality.equals(rsaPublicKey, other.rsaPublicKey)
            && Equality.equals(rsaPrivateKey, other.rsaPrivateKey)
            && Equality.equals(createTime, other.createTime)
            && Equality.equals(modifyTime, other.modifyTime)
            && Equality.equals(deleteTime, other.deleteTime);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, organizationId);
    result = Hash.combine(result, multiplier, organizationName);
    result = Hash.combine(result, multiplier, organizationCode);
    result = Hash.combine(result, multiplier, categoryId);
    result = Hash.combine(result, multiplier, categoryName);
    result = Hash.combine(result, multiplier, icon);
    result = Hash.combine(result, multiplier, url);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, securityKey);
    result = Hash.combine(result, multiplier, token);
    result = Hash.combine(result, multiplier, tokenCreateTime);
    result = Hash.combine(result, multiplier, tokenExpiredTime);
    result = Hash.combine(result, multiplier, rsaPublicKey);
    result = Hash.combine(result, multiplier, rsaPrivateKey);
    result = Hash.combine(result, multiplier, predefined);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .append("code", code)
            .append("organizationId", organizationId)
            .append("organizationName", organizationName)
            .append("organizationCode", organizationCode)
            .append("categoryId", categoryId)
            .append("categoryName", categoryName)
            .append("icon", icon)
            .append("url", url)
            .append("description", description)
            .append("securityKey", securityKey)
            .append("token", token)
            .append("tokenCreateTime", tokenCreateTime)
            .append("tokenExpiredTime", tokenExpiredTime)
            .append("rsaPublicKey", rsaPublicKey)
            .append("rsaPrivateKey", rsaPrivateKey)
            .append("predefined", predefined)
            .append("createTime", createTime)
            .append("modifyTime", modifyTime)
            .append("deleteTime", deleteTime)
            .toString();
  }
}
