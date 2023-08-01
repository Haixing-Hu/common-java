////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import java.io.Serializable;
import java.time.Instant;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示第三方应用。
 *
 * @author 胡海星
 */
public class App implements Serializable {

  private static final long serialVersionUID = -4130818293179381259L;

  /**
   * 本系统所对应的 App 的代码。
   */
  public static final String SYSTEM_APP_CODE = "system";

  /**
   * 唯一标识，系统自动生成。
   */
  @Unique
  @NotNull
  private Long id;

  /**
   * 代码，全局不可重复，一旦设置不能更改。
   */
  @Unique
  @Size(min = 1, max = 64)
  private String code;

  /**
   * 名称，同一机构下不可重复。
   */
  @Size(min = 1, max = 256)
  private String name;

  /**
   * 所属类别基本信息。
   */
  @Nullable
  private Info category;

  /**
   * 状态。
   */
  @NotNull
  private State state;

  /**
   * 图标。
   */
  @Nullable
  @Size(max = 512)
  private String icon;

  /**
   * 网址 URL。
   */
  @Nullable
  @Size(max = 512)
  private String url;

  /**
   * 描述。
   */
  @Nullable
  private String description;

  /**
   * 备注。
   */
  @Nullable
  private String comment;

  /**
   * 安全秘钥，从数据库中读取出来的是秘钥加盐后的哈希值。
   */
  @Size(min = 1, max = 4096)
  private String securityKey;

  /**
   * 访问令牌。
   */
  @Nullable
  @Size(min = 1, max = 128)
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

  public App() {
    // empty
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Nullable
  public Info getCategory() {
    return category;
  }

  public void setCategory(@Nullable final Info category) {
    this.category = category;
  }

  public State getState() {
    return state;
  }

  public void setState(final State state) {
    this.state = state;
  }

  @Nullable
  public String getIcon() {
    return icon;
  }

  public void setIcon(@Nullable final String icon) {
    this.icon = icon;
  }

  @Nullable
  public String getUrl() {
    return url;
  }

  public void setUrl(@Nullable final String url) {
    this.url = url;
  }

  @Nullable
  public String getDescription() {
    return description;
  }

  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  @Nullable
  public String getComment() {
    return comment;
  }

  public void setComment(@Nullable final String comment) {
    this.comment = comment;
  }

  public String getSecurityKey() {
    return securityKey;
  }

  public void setSecurityKey(final String securityKey) {
    this.securityKey = securityKey;
  }

  @Nullable
  public String getToken() {
    return token;
  }

  public void setToken(@Nullable final String token) {
    this.token = token;
  }

  @Nullable
  public Instant getTokenCreateTime() {
    return tokenCreateTime;
  }

  public void setTokenCreateTime(@Nullable final Instant tokenCreateTime) {
    this.tokenCreateTime = tokenCreateTime;
  }

  @Nullable
  public Instant getTokenExpiredTime() {
    return tokenExpiredTime;
  }

  public void setTokenExpiredTime(@Nullable final Instant tokenExpiredTime) {
    this.tokenExpiredTime = tokenExpiredTime;
  }

  public boolean isPredefined() {
    return predefined;
  }

  public void setPredefined(final boolean predefined) {
    this.predefined = predefined;
  }

  public Instant getCreateTime() {
    return createTime;
  }

  public void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
  }

  @Nullable
  public Instant getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(@Nullable final Instant modifyTime) {
    this.modifyTime = modifyTime;
  }

  @Nullable
  public Instant getDeleteTime() {
    return deleteTime;
  }

  public void setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final App other = (App) o;
    return Equality.equals(id, other.id)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(category, other.category)
        && Equality.equals(state, other.state)
        && Equality.equals(icon, other.icon)
        && Equality.equals(url, other.url)
        && Equality.equals(description, other.description)
        && Equality.equals(comment, other.comment)
        && Equality.equals(securityKey, other.securityKey)
        && Equality.equals(token, other.token)
        && Equality.equals(tokenCreateTime, other.tokenCreateTime)
        && Equality.equals(tokenExpiredTime, other.tokenExpiredTime)
        && Equality.equals(predefined, other.predefined)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, category);
    result = Hash.combine(result, multiplier, state);
    result = Hash.combine(result, multiplier, icon);
    result = Hash.combine(result, multiplier, url);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, comment);
    result = Hash.combine(result, multiplier, securityKey);
    result = Hash.combine(result, multiplier, token);
    result = Hash.combine(result, multiplier, tokenCreateTime);
    result = Hash.combine(result, multiplier, tokenExpiredTime);
    result = Hash.combine(result, multiplier, predefined);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("code", code)
        .append("name", name)
        .append("category", category)
        .append("state", state)
        .append("icon", icon)
        .append("url", url)
        .append("description", description)
        .append("comment", comment)
        .append("securityKey", securityKey)
        .append("token", token)
        .append("tokenCreateTime", tokenCreateTime)
        .append("tokenExpiredTime", tokenExpiredTime)
        .append("predefined", predefined)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
