////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Computed;
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
 * 此模型表示街道。
 *
 * @author 胡海星
 */
public class Street implements Identifiable, WithInfo, Assignable<Street>,
    Creatable, Modifiable, Deletable {

  private static final long serialVersionUID = 6379477503176717412L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 街道的代码，同一区县内不可重复。
   */
  @Size(min = 1, max = 64)
  @Unique
  private String code;

  /**
   * 街道的名称，同一区县内不可重复。
   */
  @Size(min = 1, max = 128)
  @Unique(respectTo = "district")
  private String name;

  /**
   * 所属区县的基本信息。
   */
  @Reference(entity = District.class, property = "info")
  private Info district;

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
   * 标记删除时间。
   */
  @Precision(value = TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public Street() {
    // empty
  }

  public Street(final Street other) {
    assign(other);
  }

  @Override
  public void assign(final Street other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    code = other.code;
    name = other.name;
    district = Assignment.clone(other.district);
    level = other.level;
    postalcode = other.postalcode;
    icon = other.icon;
    url = other.url;
    description = other.description;
    location = Assignment.clone(other.location);
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Street cloneEx() {
    return new Street(this);
  }

  @Override
  @Nullable
  public final Long getId() {
    return id;
  }

  @Override
  public final void setId(@Nullable final Long id) {
    this.id = id;
  }

  @Override
  @Nullable
  public final String getCode() {
    return code;
  }

  @Override
  public final void setCode(@Nullable final String code) {
    this.code = code;
  }

  @Override
  @Nullable
  public final String getName() {
    return name;
  }

  @Override
  public final void setName(@Nullable final String name) {
    this.name = name;
  }

  @Nullable
  public final Info getDistrict() {
    return district;
  }

  public final void setDistrict(@Nullable final Info district) {
    this.district = district;
  }

  @Nullable
  public final Integer getLevel() {
    return level;
  }

  public final void setLevel(@Nullable final Integer level) {
    this.level = level;
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

  @Nullable
  public final Location getLocation() {
    return location;
  }

  public final void setLocation(@Nullable final Location location) {
    this.location = location;
  }

  @Override
  @Nullable
  public final Instant getCreateTime() {
    return createTime;
  }

  @Override
  public final void setCreateTime(@Nullable final Instant createTime) {
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
    final Street other = (Street) o;
    return Equality.equals(id, other.id)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(district, other.district)
        && Equality.equals(level, other.level)
        && Equality.equals(postalcode, other.postalcode)
        && Equality.equals(icon, other.icon)
        && Equality.equals(url, other.url)
        && Equality.equals(description, other.description)
        && Equality.equals(location, other.location)
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
    result = Hash.combine(result, multiplier, district);
    result = Hash.combine(result, multiplier, level);
    result = Hash.combine(result, multiplier, postalcode);
    result = Hash.combine(result, multiplier, icon);
    result = Hash.combine(result, multiplier, url);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, location);
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
        .append("district", district)
        .append("level", level)
        .append("postalcode", postalcode)
        .append("icon", icon)
        .append("url", url)
        .append("description", description)
        .append("location", location)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }

  @Computed
  public String foo() {
    return name;
  }

  public Street setFoo(final String foo) {
    this.name = foo;
    return this;
  }

  public void hello() {
    System.out.println("hello");
  }

  public Street setHello() {
    System.out.println("hello");
    return this;
  }

  public Street sayHello() {
    System.out.println("hello");
    return this;
  }
}