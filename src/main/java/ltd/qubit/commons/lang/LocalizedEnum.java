////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.Locale;

import ltd.qubit.commons.i18n.Localized;

/**
 * 此接口为枚举对象提供获取本地化名称的方法。
 * <p>
 * <b>注意：</b> 实现此接口的类必须是一个枚举类，而且该类必须实现以下静态模块：
 * <pre><code>
 * static {
 *   EnumUtils.registerLocalizedNames({Enum}.class, "{path}/{basename}");
 * }
 * </code></pre>
 * 其中，`{Enum}` 是实现此接口的枚举类的名称，`{path}` 是资源文件的路径，`{basename}` 是资源文件的基本名称。
 * 资源文件中的键是枚举常量的名称，值是枚举常量的本地化名称。
 * <p>
 * 例如，一个表示性别的枚举类可以实现如下：
 * <pre><code>
 * public enum Gender implements LocalizedEnum {
 *   MALE,
 *   FEMALE;
 *
 *   static {
 *     EnumUtils.registerLocalizedNames(Gender.class, "i18n.gender");
 *   }
 * }
 * </code></pre>
 * 其中{@code i18n/gender.zh_CN.properties}文件内容如下：
 * <pre><code>
 *   MALE            = 男
 *   FEMALE          = 女
 * </code></pre>
 * 而{@code i18n/gender.en.properties}文件内容如下：
 * <pre><code>
 *   MALE            = male
 *   FEMALE          = female
 * </code></pre>
 * 这样我们就可以通过{@link #getLocalizedNameFor(Locale)}方法获取枚举对象的本地化名称。
 *
 * @author 胡海星
 */
public interface LocalizedEnum extends Localized {
  /**
   * 获取此枚举对象的本地化名称。
   *
   * @param locale
   *     指定的本地化区域。
   * @return
   *     此枚举对象的本地化名称。
   */
  @Override
  default String getLocalizedNameFor(final Locale locale) {
    return EnumUtils.getLocalizedName(locale, (Enum<?>) this);
  }
}