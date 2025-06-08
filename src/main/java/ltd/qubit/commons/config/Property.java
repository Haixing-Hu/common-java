////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config;

import javax.annotation.Nullable;

import ltd.qubit.commons.util.value.NamedMultiValues;

/**
 * {@link Property} 对象表示一个属性，它具有以下属性：
 *
 * <ul>
 * <li>名称</li>
 * <li>类型</li>
 * <li>可选的描述</li>
 * <li>一个布尔标记，指示此属性是否是最终的</li>
 * <li>指定类型的一个或多个值</li>
 * </ul>
 *
 * <p>目前，{@link Property} 对象支持以下类型：
 * <ul>
 * <li>{@code boolean}</li>
 * <li>{@code char}</li>
 * <li>{@code byte}</li>
 * <li>{@code short}</li>
 * <li>{@code int}</li>
 * <li>{@code long}</li>
 * <li>{@code float}</li>
 * <li>{@code double}</li>
 * <li>{@code String}</li>
 * <li>{@code java.util.Date}</li>
 * <li>{@code java.math.BigDecimal}</li>
 * <li>{@code java.math.BigInteger}</li>
 * <li>{@code byte[]}</li>
 * <li>{@code Class}</li>
 * </ul>
 *
 * @author 胡海星
 */
public interface Property extends NamedMultiValues {

  /**
   * 获取此属性的描述。
   *
   * @return
   *     此属性的描述，可以为 null。
   */
  @Nullable
  String getDescription();

  /**
   * 设置此属性的描述。
   *
   * @param description
   *     此属性的描述，可以为 null。
   */
  void setDescription(@Nullable String description);

  /**
   * 测试此属性是否是最终的。
   *
   * <p>一个最终的属性不能通过与同名属性合并来覆盖。
   *
   * @return 如果此属性是最终的，则为 true；否则为 false。
   */
  boolean isFinal();

  /**
   * 设置此属性是否是最终的。
   *
   * <p>一个最终的属性不能通过与同名属性合并来覆盖。
   *
   * @param isFinal
   *     如果为 true，则此属性将被设置为最终的；否则，它将被设置为非最终的。
   */
  void setFinal(boolean isFinal);

  /**
   * 将另一个 {@link Property} 对象分配给此对象。
   *
   * @param other
   *     要分配给此对象的另一个 {@link Property} 对象。
   */
  void assign(Property other);
}