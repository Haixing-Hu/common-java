////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import javax.annotation.Nullable;

/**
 * 此接口表示实体类属于某个分类。
 *
 * @author Haixing Hu
 */
public interface WithCategory {

  /**
   * 获取当前对象所属分类的基本信息。
   *
   * @return
   *     当前对象所属分类的基本信息。
   */
  @Nullable
  InfoWithEntity getCategory();

  /**
   * 设置当前对象所属分类的基本信息。
   *
   * @param category
   *     当前对象所属的新分类的基本信息。
   */
  void setCategory(@Nullable InfoWithEntity category);
}
