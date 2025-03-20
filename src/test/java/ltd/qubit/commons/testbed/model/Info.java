////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import ltd.qubit.commons.lang.Assignable;

/**
 * A test model class representing basic information.
 *
 * @author Haixing Hu
 */
public class Info implements Serializable, Assignable<Info> {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 标识符。
   */
  protected Long id;

  /**
   * 编码。
   */
  protected String code;

  /**
   * 名称。
   */
  protected String name;

  /**
   * 创建一个新的实例。
   */
  public Info() {
    // empty
  }

  /**
   * 创建一个新的实例。
   *
   * @param name
   *    名称
   */
  public Info(final String name) {
    this.name = name;
  }

  /**
   * 创建一个新的实例。
   *
   * @param id 标识符
   * @param code 编码
   * @param name 名称
   */
  public Info(final Long id, final String code, final String name) {
    this.id = id;
    this.code = code;
    this.name = name;
  }

  public Info(final Info other) {
    assign(other);
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

  @Override
  public void assign(final Info other) {
    this.id = other.id;
    this.code = other.code;
    this.name = other.name;
  }

  @Override
  public Info cloneEx() {
    return new Info(this);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Info other = (Info) o;
    return Objects.equals(id, other.id)
        && Objects.equals(code, other.code)
        && Objects.equals(name, other.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, name);
  }

  @Override
  public String toString() {
    return "Info{"
        + "id=" + id
        + ", code='" + code + '\''
        + ", name='" + name + '\''
        + '}';
  }
}