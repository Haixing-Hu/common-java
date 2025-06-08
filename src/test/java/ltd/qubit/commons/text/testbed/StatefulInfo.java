////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.testbed;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示有状态且可删除的对象的基本信息。
 *
 * @author 胡海星
 */
public class StatefulInfo extends Info implements WithStatefulInfo {

  private static final long serialVersionUID = 2919962686331957698L;

  /**
   * 状态。
   */
  protected State state;

  public StatefulInfo() {
    //  empty
  }

  public StatefulInfo(final Long id) {
    super(id, null, null);
    this.state = null;
  }

  public StatefulInfo(final Long id, final String code) {
    super(id, code, null);
    this.state = null;
  }

  public StatefulInfo(final Long id, final String code, final String name) {
    super(id, code, name);
    this.state = null;
  }

  public StatefulInfo(final Long id, final String code, final String name,
      final State state) {
    super(id, code, name);
    this.state = state;
  }

  public StatefulInfo(final StatefulInfo other) {
    assign(other);
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
  public void assign(final Info other) {
    super.assign(other);
    if (other instanceof StatefulInfo) {
      state = ((StatefulInfo) other).state;
    }
  }

  @Override
  public void assign(final StatefulInfo other) {
    super.assign(other);
    state = other.state;
  }

  @Override
  public StatefulInfo cloneEx() {
    return new StatefulInfo(this);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final StatefulInfo other = (StatefulInfo) o;
    return Equality.equals(state, other.state)
            && super.equals(other);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, super.hashCode());
    result = Hash.combine(result, multiplier, state);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("state", state)
            .toString();
  }
}