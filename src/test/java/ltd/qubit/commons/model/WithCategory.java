////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import javax.annotation.Nullable;

/**
 * 此接口表示实体类属于某个分类。
 *
 * @author 胡海星
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
