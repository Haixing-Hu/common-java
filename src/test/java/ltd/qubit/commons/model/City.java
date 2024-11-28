////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

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
 * 此模型表示城市。
 *
 * @author 胡海星
 */
public class City implements HasInfo, Auditable, Emptyful, Normalizable,
    Predefinable, Assignable<City> {

  private static final long serialVersionUID = 6194796107777707261L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 城市代码，全局不可重复。
   */
  @Size(min = 1, max = 64)
  @Unique
  private String code;

  /**
   * 城市名称，同一省内不可重复。
   */
  @Size(min = 1, max = 128)
  @Unique(respectTo = "province")
  private String name;

  /**
   * 所属省份的基本信息。
   */
  @Reference(entity = Province.class, property = "info")
  private Info province;

  /**
   * 电话区号。
   */
  @Size(max = 16)
  @Nullable
  private String phoneArea;

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

  public City() {
    // empty
  }

  public City(final City other) {
    assign(other);
  }

  @Override
  public void assign(final City other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    code = other.code;
    name = other.name;
    province = Assignment.clone(other.province);
    phoneArea = other.phoneArea;
    postalcode = other.postalcode;
    level = other.level;
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
  public City cloneEx() {
    return new City(this);
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

  public Info getProvince() {
    return province;
  }

  public void setProvince(final Info province) {
    this.province = province;
  }

  @Nullable
  public String getPhoneArea() {
    return phoneArea;
  }

  public void setPhoneArea(@Nullable final String phoneArea) {
    this.phoneArea = phoneArea;
  }

  @Nullable
  public String getPostalcode() {
    return postalcode;
  }

  public void setPostalcode(@Nullable final String postalcode) {
    this.postalcode = postalcode;
  }

  @Nullable
  public Integer getLevel() {
    return level;
  }

  public void setLevel(@Nullable final Integer level) {
    this.level = level;
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

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final City other = (City) o;
    return Equality.equals(id, other.id)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(province, other.province)
        && Equality.equals(phoneArea, other.phoneArea)
        && Equality.equals(postalcode, other.postalcode)
        && Equality.equals(level, other.level)
        && Equality.equals(icon, other.icon)
        && Equality.equals(url, other.url)
        && Equality.equals(description, other.description)
        && Equality.equals(location, other.location)
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
    result = Hash.combine(result, multiplier, province);
    result = Hash.combine(result, multiplier, phoneArea);
    result = Hash.combine(result, multiplier, postalcode);
    result = Hash.combine(result, multiplier, level);
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

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("code", code)
        .append("name", name)
        .append("province", province)
        .append("phoneArea", phoneArea)
        .append("postalcode", postalcode)
        .append("level", level)
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
