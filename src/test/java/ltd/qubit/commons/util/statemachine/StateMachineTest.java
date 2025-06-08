////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.statemachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests of the {@link StateMachine}
 *
 * @author Haixing Hu
 */
public class StateMachineTest {

  private StateMachine<States, Events> stateMachine;

  @BeforeEach
  void setUp() {
    stateMachine = new StateMachine<>();
    // 假设States枚举中有额外的状态
    stateMachine.addStates(States.class);
    stateMachine.setInitialState(States.START);
    stateMachine.setFinalState(States.END);
    stateMachine.setFinalState(States.ERROR);

    // 定义一个更复杂的状态转换流程
    stateMachine.addTransition(States.START, Events.GO, States.MIDDLE);
    stateMachine.addTransition(States.MIDDLE, Events.STOP, States.END);
    stateMachine.addTransition(States.MIDDLE, Events.ERROR, States.ERROR);
    stateMachine.addTransition(States.START, Events.ERROR, States.ERROR);
    stateMachine.addTransition(States.END, Events.REOPEN, States.REOPENED);
    stateMachine.addTransition(States.REOPENED, Events.RESET, States.START);
  }

  @Test
  void testMultipleFinalStates() {
    assertTrue(stateMachine.getFinalStates().contains(States.END));
    assertTrue(stateMachine.getFinalStates().contains(States.ERROR));
    assertEquals(2, stateMachine.getFinalStates().size());
  }

  @Test
  void testTransitionToErrorState() {
    assertEquals(States.ERROR, stateMachine.getTarget(States.MIDDLE, Events.ERROR));
    assertEquals(States.ERROR, stateMachine.getTarget(States.START, Events.ERROR));
  }

  @Test
  void testReopenAndResetFlow() {
    assertEquals(States.REOPENED, stateMachine.getTarget(States.END, Events.REOPEN));
    assertEquals(States.START, stateMachine.getTarget(States.REOPENED, Events.RESET));
  }

  @Test
  void testInvalidEventForState() {
    assertNull(stateMachine.getTarget(States.END, Events.GO),
        "End state should not transition on GO event.");
  }

  @Test
  void testInitialStateTransition() {
    assertEquals(States.MIDDLE, stateMachine.getTarget(States.START, Events.GO));
  }

  @Test
  void testUnreachableState() {
    // Assuming ERROR is an end state and shouldn't transition to any other state
    assertNull(stateMachine.getTarget(States.ERROR, Events.GO),
        "Error state should not transition to any other state.");
    assertNull(stateMachine.getTarget(States.ERROR, Events.RESET),
        "Error state should not transition to any other state.");
  }

  @Test
  void testTransitionExists() {
    // Verify that the transition explicitly exists
    assertTrue(stateMachine
            .getTransitions().stream()
            .anyMatch(t -> t.getSource() == States.START
                && t.getTarget() == States.MIDDLE
                && t.getEvent() == Events.GO),
        "Transition from START to MIDDLE on GO event should exist.");
  }
}