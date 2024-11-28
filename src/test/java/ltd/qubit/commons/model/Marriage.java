////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jakarta.xml.bind.annotation.XmlRootElement;

import ltd.qubit.commons.lang.EnumUtils;

import static ltd.qubit.commons.lang.EnumUtils.registerLocalizedNames;

/**
 * 此枚举表示婚姻状态。
 *
 * <p><b>参考资料：</b>
 * <ul>
 * <li>【GB/T 2261.2-2003】个人基本信息分类与代码 第2部分：婚姻状况代码</li>
 * </ul>
 *
 * @author 胡海星
 */
@XmlRootElement(name = "marriage")
public enum Marriage {

  /**
   * 未婚。
   */
  UNMARRIED("10"),

  /**
   * 已婚。
   */
  MARRIED("20"),

  /**
   * 初婚。
   */
  MARRIED_FIRST_TIME("21"),

  /**
   * 再婚。
   */
  MARRIED_AGAIN("22"),

  /**
   * 复婚。
   */
  MARRIED_RESTORED("23"),

  /**
   * 丧偶（寡居/鳏居）。
   */
  WIDOWED("30"),

  /**
   * 离婚。
   */
  DIVORCED("40"),

  /**
   * 分居。
   *
   * <p>此项不在【GB/T 2261.2-2003】中，为自定义扩展项。
   */
  SEPARATED("80"),

  /**
   * 未提供。
   */
  UNPROVIDED("90");

  private final String code;

  Marriage(final String code) {
    this.code = code;
  }

  public String code() {
    return code;
  }

  static {
    registerLocalizedNames(Marriage.class, "i18n/marriage");
  }

  public String getLocalizedName() {
    return getLocalizedName(Locale.getDefault());
  }

  public String getLocalizedName(final Locale locale) {
    return EnumUtils.getLocalizedName(locale, this);
  }

  public static Marriage forCode(final String code) {
    return CODE_MAP.get(code);
  }

  private static final Map<String, Marriage> CODE_MAP = new HashMap<>();
  static {
    for (final Marriage e : values()) {
      CODE_MAP.put(e.code, e);
    }
  }
}
