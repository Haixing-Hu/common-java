////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.statemachine;

public enum States {
  START,    // 开始状态
  MIDDLE,   // 中间状态，可能是处理中的状态
  END,      // 结束状态，表示成功完成
  ERROR,    // 错误状态，表示出现了错误
  REOPENED, // 重新开启状态，表示流程或反馈被重新打开
  WITHDRAWN // 撤回状态，表示流程或反馈被主动撤回
}