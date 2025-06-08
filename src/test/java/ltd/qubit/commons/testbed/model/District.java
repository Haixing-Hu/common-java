////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示区县（城市下属一级行政单位）。
 *
 * @author 胡海星
 */
public class District implements HasInfo, Auditable, Emptyful, Normalizable,
    Predefinable, Assignable<District> {

  private static final long serialVersionUID = 5449985215877113744L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 区县的代码，全局不可重复。
   */
  @Size(min = 1, max = 64)
  @Unique
  private String code;

  /**
   * 区县的名称，同一城市内不可重复。
   */
  @Size(min = 1, max = 128)
  @Unique(respectTo = "city")
  private String name;

  /**
   * 所属城市的基本信息。
   */
  @Reference(entity = City.class, property = "info")
  private Info city;

  /**
   * 级别。
   */
  @Nullable
  private Integer level;

  /**
   * 邮政编码。
   */
  @Size(max = 64)
  @Nullable
  private String postalcode;

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
   * 地理位置信息。
   */
  @Nullable
  private Location location;

  /**
   * 是否是预定义的数据。
   */
  private Boolean predefined;

  /**
   * 创建时间。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 最后一次修改时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant modifyTime;

  /**
   * 标记删除时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public District() {
    // empty
  }

  public District(final District other) {
    assign(other);
  }

  @Override
  public void assign(final District other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    code = other.code;
    name = other.name;
    city = Assignment.clone(other.city);
    level = other.level;
    postalcode = other.postalcode;
    icon = other.icon;
    url = other.url;
    description = other.description;
    location = Assignment.clone(other.location);
    predefined = other.predefined;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public District cloneEx() {
    return new District(this);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public void setCode(final String code) {
    this.code = code;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }

  public Info getCity() {
    return city;
  }

  public void setCity(final Info city) {
    this.city = city;
  }

  @Nullable
  public Integer getLevel() {
    return level;
  }

  public void setLevel(@Nullable final Integer level) {
    this.level = level;
  }

  @Nullable
  public String getPostalcode() {
    return postalcode;
  }

  public void setPostalcode(@Nullable final String postalcode) {
    this.postalcode = postalcode;
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
  public Location getLocation() {
    return location;
  }

  public void setLocation(@Nullable final Location location) {
    this.location = location;
  }

  @Override
  public Boolean isPredefined() {
    return predefined;
  }

  @Override
  public void setPredefined(final Boolean predefined) {
    this.predefined = predefined;
  }

  @Override
  public Instant getCreateTime() {
    return createTime;
  }

  @Override
  public void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
  }

  @Override
  @Nullable
  public Instant getModifyTime() {
    return modifyTime;
  }

  @Override
  public void setModifyTime(@Nullable final Instant modifyTime) {
    this.modifyTime = modifyTime;
  }

  @Override
  @Nullable
  public Instant getDeleteTime() {
    return deleteTime;
  }

  @Override
  public void setDeleteTime(@Nullable final Instant deleteTime) {
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
    final District other = (District) o;
    return Equality.equals(id, other.id)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(city, other.city)
        && Equality.equals(level, other.level)
        && Equality.equals(postalcode, other.postalcode)
        && Equality.equals(icon, other.icon)
        && Equality.equals(url, other.url)
        && Equality.equals(description, other.description)
        && Equality.equals(location, other.location)
        && Equality.equals(predefined, other.predefined)
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
    result = Hash.combine(result, multiplier, city);
    result = Hash.combine(result, multiplier, level);
    result = Hash.combine(result, multiplier, postalcode);
    result = Hash.combine(result, multiplier, icon);
    result = Hash.combine(result, multiplier, url);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, location);
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
        .append("code", code)
        .append("name", name)
        .append("city", city)
        .append("level", level)
        .append("postalcode", postalcode)
        .append("icon", icon)
        .append("url", url)
        .append("description", description)
        .append("location", location)
        .append("predefined", predefined)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}