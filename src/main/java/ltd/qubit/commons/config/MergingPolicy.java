////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config;

/**
 * 合并配置时使用的策略的枚举。
 *
 * @author 胡海星
 */
public enum MergingPolicy {

  /**
   * 跳过所有现有的属性，无论它是否是final的。
   */
  SKIP,

  /**
   * 合并现有属性的值，如果它是final的则跳过它。如果现有属性具有不同的类型且不是final的，则用新值覆盖它。
   */
  UNION,

  /**
   * 覆盖现有属性，如果它是final的则跳过它。
   */
  OVERWRITE,
}