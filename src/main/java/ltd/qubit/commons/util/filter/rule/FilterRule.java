////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.rule;

/**
 * 用于链式过滤器的过滤规则。
 *
 * @author 胡海星
 */
public abstract class FilterRule {

  /**
   * 给定链式过滤器的当前状态、过滤器链中某个过滤器的过滤结果以及该过滤器的返回操作，
   * 获取链式过滤器的下一个状态。
   *
   * @param currentState
   *     链式过滤器的当前状态，是 {@code FilterState} 中定义的值。
   * @param filterState
   *     过滤器链中当前过滤器返回的状态，是 {@code FilterState} 中定义的值。
   * @param filterAction
   *     过滤器链中当前过滤器返回的操作，是 {@code FilterAction} 中定义的值。
   * @return
   *     链式过滤器下一个状态和将要执行的操作的按位组合。
   */
  public abstract int nextStateAction(int currentState, int filterState,
      int filterAction);

}