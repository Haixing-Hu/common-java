////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.util.Locale;

import ltd.qubit.commons.lang.EnumUtils;

import static ltd.qubit.commons.lang.EnumUtils.registerLocalizedNames;

/**
 * 此枚举表示医保类型。
 *
 * @author 胡海星
 */
public enum MedicareType {

  /**
   * 城镇职工基本医疗保险。
   */
  EMPLOYEE,

  /**
   * 城镇居民基本医疗保险。
   */
  RESIDENT,

  /**
   * 新型农村合作医疗保险。
   */
  NEW_RURAL_COOPERATIVE,

  /**
   * 其它医疗保险。
   */
  OTHER;

  static {
    registerLocalizedNames(MedicareType.class, "i18n/medicare-type");
  }

  public String getLocalizedName() {
    return getLocalizedName(Locale.getDefault());
  }

  public String getLocalizedName(final Locale locale) {
    return EnumUtils.getLocalizedName(locale, this);
  }
}
