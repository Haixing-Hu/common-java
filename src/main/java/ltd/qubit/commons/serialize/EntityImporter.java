////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.serialize;

/**
 * 此接口定义了导入实体对象的方法。
 *
 * @param <T>
 *     实体对象的类型。
 */
@FunctionalInterface
public interface EntityImporter<T> {

  /**
   * 导入指定的实体对象。
   *
   * @param entity
   *     待导入的实体对象。
   */
  void importEntity(T entity);
}
