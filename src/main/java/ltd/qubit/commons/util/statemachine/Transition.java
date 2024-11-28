////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * This class represents a transition in a state machine.
 *
 * @param <S>
 *     the type of states.
 * @param <E>
 *     the type of events.
 * @author Haixing Hu
 */
@Immutable
public class Transition<S extends Enum<S>, E extends Enum<E>> {

  /**
   * The source state of this transition.
   */
  private final S source;

  /**
   * The event that triggers this transition.
   */
  private final E event;

  /**
   * The target state of this transition.
   */
  private final S target;

  public Transition(final S source, final E event, final S target) {
    this.source = requireNonNull("source", source);
    this.event = requireNonNull("event", event);
    this.target = requireNonNull("target", target);
  }

  public S getSource() {
    return source;
  }

  public E getEvent() {
    return event;
  }

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
