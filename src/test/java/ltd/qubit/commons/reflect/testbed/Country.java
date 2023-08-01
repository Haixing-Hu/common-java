////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import jakarta.validation.constraints.Size;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import javax.annotation.Nullable;
import java.time.Instant;

/**
 * 此模型表示国家。
 *
 * @author 胡海星
 */
public class Country implements Identifiable, WithInfo, Assignable<Country>,
    Creatable, Modifiable, Deletable {

  private static final long serialVersionUID = 7415231604886436994L;

  /**
   * 唯一标识，系统自动生成.
   */
  @Unique
  private Long id;

  /**
   * 编码，采用 ISO-3166 国家代码，2个大写字符.
   */
  @Size(min = 1, max = 64)
  @Unique
  private String code;

  /**
   * 国家名称.
   */
  @Size(min = 1, max = 128)
  @Unique
  private String name;

  /**
   * 电话区号.
   */
  @Size(min = 1, max = 16)
  private String phoneArea;

  /**
   * 邮政编码.
   */
  @Size(max = 64)
  @Nullable
  private String postalcode;

  /**
   * 图标.
   */
  @Size(max = 512)
  @Nullable
  private String icon;

  /**
   * 网址.
   */
  @Size(max = 512)
  @Nullable
  private String url;

  /**
   * 描述.
   */
  @Nullable
  private String description;

  /**
   * 创建时间.
   */
  private Instant createTime;

  /**
   * 最后一次修改时间.
   */
  @Nullable
  private Instant modifyTime;

  /**
   * 标记删除时间.
   */
  @Nullable
  private Instant deleteTime;

  public Country() {
    // empty
  }

  public Country(final Country other) {
    assign(other);
  }

  @Override
  public void assign(final Country other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    code = other.code;
    name = other.name;
    phoneArea = other.phoneArea;
    postalcode = other.postalcode;
    icon = other.icon;
    url = other.url;
    description = other.description;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Country clone() {
    return new Country(this);
  }

  @Override
  public final Long getId() {
    return id;
  }

  @Override
  public final void setId(final Long id) {
    this.id = id;
  }

  @Override
  public final String getCode() {
    return code;
  }

  @Override
  public final void setCode(final String code) {
    this.code = code;
  }

  @Override
  public final String getName() {
    return name;
  }

  @Override
  public final void setName(final String name) {
    this.name = name;
  }

  public final String getPhoneArea() {
    return phoneArea;
  }

  public final void setPhoneArea(final String phoneArea) {
    this.phoneArea = phoneArea;
  }

  @Nullable
  public final String getPostalcode() {
    return postalcode;
  }

  public final void setPostalcode(@Nullable final String postalcode) {
    this.postalcode = postalcode;
  }

  @Nullable
  public final String getIcon() {
    return icon;
  }

  public final void setIcon(@Nullable final String icon) {
    this.icon = icon;
  }

  @Nullable
  public final String getUrl() {
    return url;
  }

  public final void setUrl(@Nullable final String url) {
    this.url = url;
  }

  @Nullable
  public final String getDescription() {
    return description;
  }

  public final void setDescription(@Nullable final String description) {
    this.description = description;
  }

  @Override
  public final Instant getCreateTime() {
    return createTime;
  }

  @Override
  public final void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
  }

  @Override
  @Nullable
  public final Instant getModifyTime() {
    return modifyTime;
  }

  @Override
  public final void setModifyTime(@Nullable final Instant modifyTime) {
    this.modifyTime = modifyTime;
  }

  @Override
  @Nullable
  public final Instant getDeleteTime() {
    return deleteTime;
  }

  @Override
  public final void setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Country other = (Country) o;
    return Equality.equals(id, other.id)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(phoneArea, other.phoneArea)
        && Equality.equals(postalcode, other.postalcode)
        && Equality.equals(icon, other.icon)
        && Equality.equals(url, other.url)
        && Equality.equals(description, other.description)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, phoneArea);
    result = Hash.combine(result, multiplier, postalcode);
    result = Hash.combine(result, multiplier, icon);
    result = Hash.combine(result, multiplier, url);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("code", code)
        .append("name", name)
        .append("phoneArea", phoneArea)
        .append("postalcode", postalcode)
        .append("icon", icon)
        .append("url", url)
        .append("description", description)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
