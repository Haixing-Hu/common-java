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
import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.*;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * 此模型表示省份。
 *
 * @author 胡海星
 */
public class Province implements Identifiable, WithInfo, Assignable<Province>,
    Creatable, Modifiable, Deletable {

  private static final long serialVersionUID = 7389791733752952288L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 省份代码，全局不可重复。
   */
  @Size(min = 1, max = 64)
  @Unique
  private String code;

  /**
   * 省份名称，同一国家内不可重复。
   */
  @Size(min = 1, max = 128)
  @Unique(respectTo = "country")
  private String name;

  /**
   * 所属国家的基本信息。
   */
  @Reference(entity = Country.class, property = "info")
  private Info country;

  /**
   * 邮政编码。
   */
  @Size(max = 64)
  @Nullable
  private String postalcode;

  /**
   * 级别。
   */
  @Nullable
  private Integer level;

  /**
   * 图标。
   */
  @Size(max = 512)
  @Nullable
  private String icon;

  /**
   * 网址。
   */
  @Size(max = 512)
  @Nullable
  private String url;

  /**
   * 描述。
   */
  @Nullable
  private String description;

  /**
   * 创建时间。
   */
  @Precision(value = TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 最后一次修改时间。
   */
  @Precision(value = TimeUnit.SECONDS)
  @Nullable
  private Instant modifyTime;

  /**
   * 删除时间。
   */
  @Precision(value = TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public Province() {
    // empty
  }

  public Province(final Province other) {
    assign(other);
  }

  @Override
  public void assign(final Province other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    code = other.code;
    name = other.name;
    country = Assignment.clone(other.country);
    postalcode = other.postalcode;
    level = other.level;
    icon = other.icon;
    url = other.url;
    description = other.description;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Province clone() {
    return new Province(this);
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

  public final Info getCountry() {
    return country;
  }

  public final void setCountry(final Info country) {
    this.country = country;
  }

  @Nullable
  public final String getPostalcode() {
    return postalcode;
  }

  public final void setPostalcode(@Nullable final String postalcode) {
    this.postalcode = postalcode;
  }

  @Nullable
  public final Integer getLevel() {
    return level;
  }

  public final void setLevel(@Nullable final Integer level) {
    this.level = level;
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
    final Province other = (Province) o;
    return Equality.equals(id, other.id)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(country, other.country)
        && Equality.equals(postalcode, other.postalcode)
        && Equality.equals(level, other.level)
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
    result = Hash.combine(result, multiplier, country);
    result = Hash.combine(result, multiplier, postalcode);
    result = Hash.combine(result, multiplier, level);
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
        .append("country", country)
        .append("postalcode", postalcode)
        .append("level", level)
        .append("icon", icon)
        .append("url", url)
        .append("description", description)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
