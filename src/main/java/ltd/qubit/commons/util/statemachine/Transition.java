////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.statemachine;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 此类表示状态机中的转换。
 *
 * @param <S>
 *     状态的类型。
 * @param <E>
 *     事件的类型。
 * @author 胡海星
 */
@Immutable
public class Transition<S extends Enum<S>, E extends Enum<E>> {

  /**
   * 此转换的源状态。
   */
  private final S source;

  /**
   * 触发此转换的事件。
   */
  private final E event;

  /**
   * 此转换的目标状态。
   */
  private final S target;

  /**
   * 构造一个 {@code Transition}.
   *
   * @param source
   *     转换的源状态。
   * @param event
   *     触发转换的事件。
   * @param target
   *     转换的目标状态。
   */
  public Transition(final S source, final E event, final S target) {
    this.source = requireNonNull("source", source);
    this.event = requireNonNull("event", event);
    this.target = requireNonNull("target", target);
  }

  /**
   * 获取此转换的源状态。
   *
   * @return 此转换的源状态。
   */
  public S getSource() {
    return source;
  }

  /**
   * 获取触发此转换的事件。
   *
   * @return 触发此转换的事件。
   */
  public E getEvent() {
    return event;
  }

  /**
   * 获取此转换的目标状态。
   *
   * @return 此转换的目标状态。
   */
  public S getTarget() {
    return target;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Transition<?, ?> other = (Transition<?, ?>) o;
    return Equality.equals(source, other.source)
        && Equality.equals(event, other.event)
        && Equality.equals(target, other.target);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, source);
    result = Hash.combine(result, multiplier, event);
    result = Hash.combine(result, multiplier, target);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("source", source)
        .append("event", event)
        .append("target", target)
        .toString();
  }
}