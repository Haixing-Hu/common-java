////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.statemachine;

public enum Events {
  GO,       // 触发开始向中间状态转换的事件
  STOP,     // 触发流程完成的事件
  ERROR,    // 出现错误的事件
  REOPEN,   // 重新开启流程或反馈的事件
  RESET     // 重置流程到开始状态的事件
}
