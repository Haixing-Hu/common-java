////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.rule;

/**
 * The filter rule for a chained filter.
 *
 * @author Haixing Hu
 */
public abstract class FilterRule {

  /**
   * Given the current state, a filter result and a filter action returned by a
   * filter in the filter chain, get the next state of the chained filter.
   *
   * @param currentState
   *     the index state of the chained filter, which is a value defined in
   *     FilterState.
   * @param filterState
   *     a value defined in FilterState returned by the current filter in the
   *     chain.
   * @param filterAction
   *     a value defined in FilterAction returned by the current filter in the
   *     chain.
   * @return a bitwise combination of the next state and the action to be take
   *     by the chained filter.
   */
  public abstract int nextStateAction(int currentState, int filterState,
      int filterAction);

}
