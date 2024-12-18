////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.Locale;

/**
 * 此接口为对象提供获取本地化名称的方法。
 *
 * @author 胡海星
 */
public interface Localized {

  /**
   * 获取此对象的本地化名称。
   *
   * @return
   *     此对象的本地化名称。
   */
  default String getLocalizedName() {
    return getLocalizedNameFor(Locale.getDefault());
  }

  /**
   * 获取此对象的本地化名称。
   *
   * @param locale
   *     指定的本地化区域。
   * @return
   *     此对象的本地化名称。
   */
  String getLocalizedNameFor(Locale locale);
}
