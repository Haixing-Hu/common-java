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
 * 此枚举表示性别。
 *
 * <p><b>参考资料：</b>
 * <ul>
 *   <li>《GB/T 2261.1-2003 个人基本信息分类与代码 第1部分：人的性别代码》</li>
 *   <li>《江苏省医疗服务监管系统接入规范（草稿） v0.8》8.3 性别编码表</li>
 * </ul>
 *
 * @author 胡海星
 */
@XmlRootElement(name = "gender")
public enum Gender {

  /**
   * 未知。
   */
  UNKNOWN("0"),

  /**
   * 男性。
   */
  MALE("1"),

  /**
   * 女性。
   */
  FEMALE("2"),

  /**
   * 女性变为男性。
   */
  FEMALE_TO_MALE("5"),

  /**
   * 男性变为女性。
   */
  MALE_TO_FEMALE("6"),

  /**
   * 未提供。
   */
  UNPROVIDED("9");

  private final String code;

  Gender(final String code) {
    this.code = code;
  }

  public String code() {
    return code;
  }

  static {
    registerLocalizedNames(Gender.class, "i18n/gender");
  }

  public String getLocalizedName() {
    return getLocalizedName(Locale.getDefault());
  }

  public String getLocalizedName(final Locale locale) {
    return EnumUtils.getLocalizedName(locale, this);
  }

  public static Gender forCode(final String code) {
    return CODE_MAP.get(code);
  }

  private static final Map<String, Gender> CODE_MAP = new HashMap<>();
  static {
    for (final Gender e : values()) {
      CODE_MAP.put(e.code, e);
    }
  }
}
