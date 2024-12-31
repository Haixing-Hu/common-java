////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * This class represents a state machine.
 * <p>
 * This class use a simple implementation of state machine, which is a directed
 * graph. The nodes of the graph are the states of the state machine, and the
 * edges of the graph are the transitions of the state machine. The transitions
 * are labeled with events.
 * <p>
 * The subclass of this class should define the states, initial states, final
 * states and transitions of the state machine in its constructor. And the
 * constructed state machine should be immutable.
 *
 * @param <S>
 *     the type of states.
 * @param <E>
 *     the type of events.
 * @author Haixing Hu
 */
@Immutable
public class StateMachine<S extends Enum<S>, E extends Enum<E>> {

  /**
   * The set of initial states of this state machine.
   */
  private final Set<S> initialStates = new HashSet<>();

  /**
   * The set of final states of this state machine.
   */
  private final Set<S> finalStates = new HashSet<>();

  /**
   * The set of all states of this state machine, including initial and final
   * states.
   */
  private final Set<S> states = new HashSet<>();

  /**
   * The set of transitions of this state machine.
   */
  private final Set<Transition<S, E>> transitions = new HashSet<>();

  /**
   * The map of transitions of this state machine.
   */
  private final Map<Pair<S, E>, S> transitionMap = new HashMap<>();

  /**
   * Adds a state to this state machine.
   * <p>
   * This method is intended to be used by the subclass of this class.
   *
   * @param state
   *     the state to be added.
   */
  protected void addState(final S state) {
    states.add(requireNonNull("state", state));
  }

  /**
   * Adds all enumerators of an enumeration class as states to this state machine.
   * <p>
   * This method is intended to be used by the subclass of this class.
   *
   * @param stateEnumClass
   *     the class of an enumeration class, whose enumerators will be added as
   *     states.
   */
  protected void addStates(final Class<S> stateEnumClass) {
    requireNonNull("stateEnumClass", stateEnumClass);
    Collections.addAll(states, stateEnumClass.getEnumConstants());
  }

  /**
   * Sets a state as the initial state of this state machine.
   * <p>
   * Note that a state machine may have multiple initial states.
   * <p>
   * This method is intended to be used by the subclass of this class.
   *
   * @param initialState
   *     the initial state to be set.
   */
  protected void setInitialState(final S initialState) {
    initialStates.add(requireNonNull("initialState", initialState));
  }

  /**
   * Sets a state as the initial state of this state machine.
   * <p>
   * Note that a state machine may have multiple initial states.
   * <p>
   * This method is intended to be used by the subclass of this class.
   *
   * @param initialStates
   *     the initial states to be set.
   */
  @SafeVarargs
  @SuppressWarnings("varargs")
  protected final void setInitialStates(final S... initialStates) {
    Collections.addAll(this.initialStates, requireNonNull("initialStates", initialStates));
  }

  /**
   * Sets a state as the final state of this state machine.
   * <p>
   * Note that a state machine may have multiple final states.
   * <p>
   * This method is intended to be used by the subclass of this class.
   *
   * @param finalState
   *     the final state to be set.
   */
  protected void setFinalState(final S finalState) {
    finalStates.add(requireNonNull("finalState", finalState));
  }

  /**
   * Sets a state as the final state of this state machine.
   * <p>
   * Note that a state machine may have multiple final states.
   * <p>
   * This method is intended to be used by the subclass of this class.
   *
   * @param finalStates
   *     the final states to be set.
   */
  @SafeVarargs
  @SuppressWarnings("varargs")
  protected final void setFinalStates(final S... finalStates) {
    Collections.addAll(this.finalStates, requireNonNull("finalStates", finalStates));
  }

  /**
   * Adds a transition to this state machine.
   * <p>
   * This method is intended to be used by the subclass of this class.
   *
   * @param transition
   *     the transition to be added.
   */
  protected void addTransition(final Transition<S, E> transition) {
    transitions.add(requireNonNull("transition", transition));
    final Pair<S, E> key = new Pair<>(transition.getSource(), transition.getEvent());
    transitionMap.put(key, transition.getTarget());
  }

  /**
   * Adds a transition to this state machine.
   * <p>
   * This method is intended to be used by the subclass of this class.
   *
   * @param source
   *     the source state of the transition.
   * @param target
   *     the target state of the transition.
   * @param event
   *     the event of the transition.
   */
  protected void addTransition(final S source, final E event, final S target) {
    addTransition(new Transition<>(source, event, target));
  }

  /**
   * Gets the set of all states of this state machine, including initial and
   * final states.
   *
   * @return the set of all states of this state machine.
   */
  public Set<S> getStates() {
    return states;
  }

  /**
   * Gets the set of initial states of this state machine.
   *
   * @return
   *     the set of initial states of this state machine.
   */
  public Set<S> getInitialStates() {
    return initialStates;
  }

  /**
   * Gets the set of final states of this state machine.
   *
   * @return
   *     the set of final states of this state machine.
   */
  public Set<S> getFinalStates() {
    return finalStates;
  }

  /**
   * Gets the set of transitions of this state machine.
   *
   * @return
   *     the set of transitions of this state machine.
   */
  public Set<Transition<S, E>> getTransitions() {
    return transitions;
  }

  /**
   * Tests whether a state is an initial state of this state machine.
   *
   * @param state
   *     the state to be tested.
   * @return
   *     {@code true} if the state is an initial state of this state machine;
   *     {@code false} otherwise.
   */
  public boolean isInitialState(final S state) {
    return initialStates.contains(state);
  }

  /**
   * Tests whether a state is a final state of this state machine.
   *
   * @param state
   *     the state to be tested.
   * @return
   *     {@code true} if the state is a final state of this state machine;
   *     {@code false} otherwise.
   */
  public boolean isFinalState(final S state) {
    return finalStates.contains(state);
  }

  /**
   * Gets the target state of a transition from a source state with a given event.
   *
   * @param source
   *     the source state of the transition.
   * @param event
   *     the event of the transition.
   * @return
   *     the target state of the transition, or {@code null} if the transition
   *     does not exist.
   */
  @Nullable
  public S getTarget(final S source, final E event) {
    final Pair<S, E> key = new Pair<>(source, event);
    return transitionMap.get(key);
  }
}
