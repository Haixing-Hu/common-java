////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
 * @author 胡海星
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
