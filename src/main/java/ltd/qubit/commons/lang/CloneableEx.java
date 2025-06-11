////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

/**
 * {@link java.lang.Cloneable} 接口的扩展版本，
 * 显式提供 {@link #cloneEx()} 方法。
 *
 * @author 胡海星
 */
public interface CloneableEx<T> {

  /**
   * 创建并返回此对象的副本。
   *
   * @return 此对象的副本。
   */
  T cloneEx();
}