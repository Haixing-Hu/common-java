////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

/**
 * 提供将此对象转换为 {@link ErrorInfo} 对象的方法的接口。
 *
 * @author 胡海星
 */
public interface ErrorInfoConvertable {

  /**
   * 将此对象转换为 {@link ErrorInfo} 对象。
   *
   * @return
   *    从该对象转换而来的 {@link ErrorInfo} 对象。
   */
  ErrorInfo toErrorInfo();
}