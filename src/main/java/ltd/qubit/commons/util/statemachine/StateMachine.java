////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.statemachine;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.util.pair.Pair;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 此类表示状态机。
 *
 * <p>此类使用状态机的简单实现,即有向图。图的节点是状态机的状态,图的边是状态机的转换。
 * 转换用事件标记。
 *
 * <p>此类的子类应在其构造函数中定义状态机的状态,初始状态,最终状态和转换。
 * 并且构造的状态机应该是不可变的。
 *
 * @param <S>
 *     状态的类型。
 * @param <E>
 *     事件的类型。
 * @author 胡海星
 */
@Immutable
public class StateMachine<S extends Enum<S>, E extends Enum<E>> {

  /**
   * 此状态机的初始状态集。
   */
  private final Set<S> initialStates = new HashSet<>();

  /**
   * 此状态机的最终状态集。
   */
  private final Set<S> finalStates = new HashSet<>();

  /**
   * 此状态机的所有状态的集合,包括初始状态和最终状态。
   */
  private final Set<S> states = new HashSet<>();

  /**
   * 此状态机的转换集。
   */
  private final Set<Transition<S, E>> transitions = new HashSet<>();

  /**
   * 此状态机的转换映射。
   */
  private final Map<Pair<S, E>, S> transitionMap = new HashMap<>();

  /**
   * 将状态添加到此状态机。
   *
   * <p>此方法旨在供此类的子类使用。
   *
   * @param state
   *     要添加的状态。
   */
  protected void addState(final S state) {
    states.add(requireNonNull("state", state));
  }

  /**
   * 将枚举类的所有枚举器作为状态添加到此状态机。
   *
   * <p>此方法旨在供此类的子类使用。
   *
   * @param stateEnumClass
   *     枚举类的类,其枚举器将作为状态添加。
   */
  protected void addStates(final Class<S> stateEnumClass) {
    requireNonNull("stateEnumClass", stateEnumClass);
    Collections.addAll(states, stateEnumClass.getEnumConstants());
  }

  /**
   * 将状态设置为此状态机的初始状态。
   *
   * <p>请注意,状态机可以具有多个初始状态。
   *
   * <p>此方法旨在供此类的子类使用。
   *
   * @param initialState
   *     要设置的初始状态。
   */
  protected void setInitialState(final S initialState) {
    initialStates.add(requireNonNull("initialState", initialState));
  }

  /**
   * 将状态设置为此状态机的初始状态。
   *
   * <p>请注意,状态机可以具有多个初始状态。
   *
   * <p>此方法旨在供此类的子类使用。
   *
   * @param initialStates
   *     要设置的初始状态。
   */
  @SafeVarargs
  @SuppressWarnings("varargs")
  protected final void setInitialStates(final S... initialStates) {
    Collections.addAll(this.initialStates, requireNonNull("initialStates", initialStates));
  }

  /**
   * 将状态设置为此状态机的最终状态。
   *
   * <p>请注意,状态机可以具有多个最终状态。
   *
   * <p>此方法旨在供此类的子类使用。
   *
   * @param finalState
   *     要设置的最终状态。
   */
  protected void setFinalState(final S finalState) {
    finalStates.add(requireNonNull("finalState", finalState));
  }

  /**
   * 将状态设置为此状态机的最终状态。
   *
   * <p>请注意,状态机可以具有多个最终状态。
   *
   * <p>此方法旨在供此类的子类使用。
   *
   * @param finalStates
   *     要设置的最终状态。
   */
  @SafeVarargs
  @SuppressWarnings("varargs")
  protected final void setFinalStates(final S... finalStates) {
    Collections.addAll(this.finalStates, requireNonNull("finalStates", finalStates));
  }

  /**
   * 将转换添加到此状态机。
   *
   * <p>此方法旨在供此类的子类使用。
   *
   * @param transition
   *     要添加的转换。
   */
  protected void addTransition(final Transition<S, E> transition) {
    transitions.add(requireNonNull("transition", transition));
    final Pair<S, E> key = new Pair<>(transition.getSource(), transition.getEvent());
    transitionMap.put(key, transition.getTarget());
  }

  /**
   * 将转换添加到此状态机。
   *
   * <p>此方法旨在供此类的子类使用。
   *
   * @param source
   *     转换的源状态。
   * @param target
   *     转换的目标状态。
   * @param event
   *     转换的事件。
   */
  protected void addTransition(final S source, final E event, final S target) {
    addTransition(new Transition<>(source, event, target));
  }

  /**
   * 获取此状态机的所有状态的集合,包括初始状态和最终状态。
   *
   * @return 此状态机的所有状态的集合。
   */
  public Set<S> getStates() {
    return states;
  }

  /**
   * 获取此状态机的初始状态集。
   *
   * @return
   *     此状态机的初始状态集。
   */
  public Set<S> getInitialStates() {
    return initialStates;
  }

  /**
   * 获取此状态机的最终状态集。
   *
   * @return
   *     此状态机的最终状态集。
   */
  public Set<S> getFinalStates() {
    return finalStates;
  }

  /**
   * 获取此状态机的转换集。
   *
   * @return
   *     此状态机的转换集。
   */
  public Set<Transition<S, E>> getTransitions() {
    return transitions;
  }

  /**
   * 测试状态是否是此状态机的初始状态。
   *
   * @param state
   *     要测试的状态。
   * @return
   *     如果状态是此状态机的初始状态,则为{@code true}；否则为{@code false}。
   */
  public boolean isInitialState(final S state) {
    return initialStates.contains(state);
  }

  /**
   * 测试状态是否是此状态机的最终状态。
   *
   * @param state
   *     要测试的状态。
   * @return
   *     如果状态是此状态机的最终状态,则为{@code true}；否则为{@code false}。
   */
  public boolean isFinalState(final S state) {
    return finalStates.contains(state);
  }

  /**
   * 在此状态机中触发事件。
   *
   * @param source
   *     事件的源状态。
   * @param event
   *     要触发的事件。
   * @return
   *     如果转换成功,则为目标状态；否则为{@code null}。
   */
  @Nullable
  public S getTarget(final S source, final E event) {
    final Pair<S, E> key = new Pair<>(source, event);
    return transitionMap.get(key);
  }
}