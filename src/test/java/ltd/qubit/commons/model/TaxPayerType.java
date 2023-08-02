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

import jakarta.xml.bind.annotation.XmlRootElement;

import ltd.qubit.commons.lang.EnumUtils;

import static ltd.qubit.commons.lang.EnumUtils.registerLocalizedNames;

/**
 * 此枚举表示纳税人类型。
 *
 * @author 潘凯
 */
@XmlRootElement(name = "tax-payer-type")
public enum TaxPayerType {

  /**
   * 小规模纳税人。
   */
  SMALL_SCALE,

  /**
   * 一般纳税人。
   */
  GENERAL,

  /**
   * 其他。
   */
  OTHER;

  static {
    registerLocalizedNames(TaxPayerType.class, "i18n/tax-payer-type");
  }

  public String getLocalizedName() {
    return getLocalizedName(Locale.getDefault());
  }

  public String getLocalizedName(final Locale locale) {
    return EnumUtils.getLocalizedName(locale, this);
  }
}
