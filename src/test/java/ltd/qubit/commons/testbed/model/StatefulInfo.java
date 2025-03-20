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
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * 此模型表示有状态且可删除的对象的基本信息。
 *
 * @author 胡海星
 */
@XmlRootElement(name = "stateful-info")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect(fieldVisibility = ANY,
                getterVisibility = NONE,
                isGetterVisibility = NONE,
                setterVisibility = NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatefulInfo extends Info implements Stateful {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 状态。
   */
  protected State state;

  /**
   * 创建一个新的实例。
   */
  public StatefulInfo() {
  }

  /**
   * 创建一个新的实例。
   *
   * @param id 标识符
   */
  public StatefulInfo(final Long id) {
    super(id, null, null);
  }

  /**
   * 创建一个新的实例。
   *
   * @param id 标识符
   * @param code 编码
   */
  public StatefulInfo(final Long id, final String code) {
    super(id, code, null);
  }

  /**
   * 创建一个新的实例。
   *
   * @param id 标识符
   * @param code 编码
   * @param name 名称
   */
  public StatefulInfo(final Long id, final String code, final String name) {
    super(id, code, name);
  }

  /**
   * 创建一个新的实例。
   *
   * @param id 标识符
   * @param code 编码
   * @param name 名称
   * @param state 状态
   */
  public StatefulInfo(final Long id, final String code, final String name,
      final State state) {
    super(id, code, name);
    this.state = state;
  }

  /**
   * 创建一个新的实例。
   *
   * @param source 源对象
   */
  public StatefulInfo(final StatefulInfo source) {
    assign(source);
  }

  @Override
  public State getState() {
    return state;
  }

  @Override
  public void setState(final State state) {
    this.state = state;
  }

  @Override
  public StatefulInfo cloneEx() {
    return new StatefulInfo(this);
  }

  /**
   * 将指定的对象的属性复制到当前对象。
   *
   * @param source 源对象
   */
  public void assign(final StatefulInfo source) {
    if (source == null) {
      super.assign(null);
      this.state = null;
    } else {
      super.assign(source);
      this.state = source.state;
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    final StatefulInfo that = (StatefulInfo) o;
    return state == that.state;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), state);
  }

  @Override
  public String toString() {
    return "StatefulInfo{"
        + "id=" + getId()
        + ", code='" + getCode() + '\''
        + ", name='" + getName() + '\''
        + ", state=" + state
        + '}';
  }
}