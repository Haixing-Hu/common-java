////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import jakarta.validation.constraints.Size;
import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 此模型表示第三方应用。
 *
 * @author 胡海星
 */
public class App implements HasStatefulInfo, WithOrganization, WithCategory,
    WithToken, WithSecurityKey, WithComment, Predefinable, Desensitizable<App>,
    Emptyful, Normalizable, Auditable, Assignable<App> {

  private static final long serialVersionUID = -4130818293179381259L;

  /**
   * 本系统所对应的 App 的代码。
   */
  public static final String SYSTEM_APP_CODE = "system";

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
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
  @Unique(respectTo = "organization")
  @Size(min = 1, max = 128)
  private String name;

  /**
   * 所属机构的基本信息。
   */
  @Reference(entity = Organization.class, property = "info")
  private StatefulInfo organization;

  /**
   * 所属类别的基本信息。
   */
  @Reference(entity = Category.class, property = "info")
  @Nullable
  private InfoWithEntity category;

  /**
   * 状态。
   */
  private State state = State.NORMAL;

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
   *
   * <p>备注(comment)是由系统管理员填写，描述(description)可以由用户自己填写。</p>
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
  private Token token;

  /**
   * 最后一次授权记录。
   */
  private AuthorizeRecord lastAuthorize;

  /**
   * 是否是预定义的数据。
   */
  private Boolean predefined;

  /**
   * 创建时间，以UTC时区存储。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 最后一次修改时间，以UTC时区存储。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant modifyTime;

  /**
   * 标记删除时间，以UTC时区存储。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public App() {
    // empty
  }

  public App(final App other) {
    assign(other);
  }

  @Override
  public void assign(final App other) {
    requireNonNull("other", other);
    id = other.id;
    code = other.code;
    name = other.name;
    organization = Assignment.clone(other.organization);
    category = Assignment.clone(other.category);
    state = other.state;
    icon = other.icon;
    url = other.url;
    description = other.description;
    comment = other.comment;
    securityKey = other.securityKey;
    token = Assignment.clone(other.token);
    lastAuthorize = Assignment.clone(other.lastAuthorize);
    predefined = other.predefined;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public App clone() {
    return new App(this);
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

  public StatefulInfo getOrganization() {
    return organization;
  }

  public void setOrganization(final StatefulInfo organization) {
    this.organization = organization;
  }

  @Nullable
  public InfoWithEntity getCategory() {
    return category;
  }

  public void setCategory(@Nullable final InfoWithEntity category) {
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
  public Token getToken() {
    return token;
  }

  public void setToken(@Nullable final Token token) {
    this.token = token;
  }

  public AuthorizeRecord getLastAuthorize() {
    return lastAuthorize;
  }

  public void setLastAuthorize(final AuthorizeRecord lastAuthorize) {
    this.lastAuthorize = lastAuthorize;
  }

  public Boolean isPredefined() {
    return predefined;
  }

  public void setPredefined(final Boolean predefined) {
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

  /**
   * 对此对象脱敏。
   */
  public final void desensitize() {
    securityKey = null;
    token = null;
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
        && Equality.equals(organization, other.organization)
        && Equality.equals(category, other.category)
        && Equality.equals(state, other.state)
        && Equality.equals(icon, other.icon)
        && Equality.equals(url, other.url)
        && Equality.equals(description, other.description)
        && Equality.equals(comment, other.comment)
        && Equality.equals(securityKey, other.securityKey)
        && Equality.equals(token, other.token)
        && Equality.equals(lastAuthorize, other.lastAuthorize)
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
    result = Hash.combine(result, multiplier, organization);
    result = Hash.combine(result, multiplier, category);
    result = Hash.combine(result, multiplier, state);
    result = Hash.combine(result, multiplier, icon);
    result = Hash.combine(result, multiplier, url);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, comment);
    result = Hash.combine(result, multiplier, securityKey);
    result = Hash.combine(result, multiplier, token);
    result = Hash.combine(result, multiplier, lastAuthorize);
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
        .append("organization", organization)
        .append("category", category)
        .append("state", state)
        .append("icon", icon)
        .append("url", url)
        .append("description", description)
        .append("comment", comment)
        .append("securityKey", securityKey)
        .append("token", token)
        .append("lastAuthorize", lastAuthorize)
        .append("predefined", predefined)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
