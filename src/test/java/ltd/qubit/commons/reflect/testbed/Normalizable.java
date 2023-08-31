////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

/**
 * 此接口表示实体类可被正则化。
 *
 * @param <T>
 *     实体类
 * @author Haixing Hu
 */
public interface Normalizable<T> {

  /**
   * 正则化此实体类。
   *
   * @return
   *   当前对象。
   */
  T normalize();
}
