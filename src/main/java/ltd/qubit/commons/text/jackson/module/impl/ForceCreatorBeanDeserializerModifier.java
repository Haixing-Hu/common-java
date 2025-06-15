////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.impl;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import com.fasterxml.jackson.databind.type.MapType;

/**
 * 强制使用 Objenesis 创建 Jackson 无法实例化的 Bean 实例的 {@link BeanDeserializerModifier}。
 * <p>
 * 该修改器用于为 Jackson 无法实例化的 Bean（例如没有默认构造函数的 Bean）创建 {@link ForceValueInstantiator}。
 *
 * @author 胡海星
 */
public class ForceCreatorBeanDeserializerModifier extends BeanDeserializerModifier {

  /**
   * 修改器的单例实例。
   */
  private static final ForceCreatorBeanDeserializerModifier INSTANCE =
      new ForceCreatorBeanDeserializerModifier();

  /**
   * 获取修改器的单例实例。
   *
   * @return 修改器的单例实例。
   */
  public static ForceCreatorBeanDeserializerModifier getInstance() {
    return INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BeanDeserializerBuilder updateBuilder(final DeserializationConfig config,
      final BeanDescription beanDesc,
      final BeanDeserializerBuilder builder) {
    final ValueInstantiator valueInstantiator = builder.getValueInstantiator();
    final Class<?> deserType = beanDesc.getBeanClass();

    final boolean forceCreationTarget = (!deserType.isPrimitive())
        && (deserType != String.class)
        && (beanDesc.findDefaultConstructor() == null)
        && isNotPossibleInstantiation(valueInstantiator);

    if (forceCreationTarget) {
      final ValueInstantiator instantiator = ForceValueInstantiator.getInstance(deserType);
      builder.setValueInstantiator(instantiator);
    }
    return builder;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<?> modifyMapDeserializer(final DeserializationConfig config,
      final MapType type, final BeanDescription beanDesc,
      final JsonDeserializer<?> deserializer) {
    return new ForceMapDeserializer((MapDeserializer) deserializer);
  }

  /**
   * 检查是否无法通过标准方式实例化对象。
   *
   * @param valueInstantiator
   *     值实例化器。
   * @return 如果无法通过标准方式实例化则返回 {@code true}，否则返回 {@code false}。
   */
  private static boolean isNotPossibleInstantiation(final ValueInstantiator valueInstantiator) {
    return !(valueInstantiator.canCreateUsingDelegate()
        || valueInstantiator.canCreateUsingArrayDelegate()
        || valueInstantiator.canCreateFromObjectWith());
  }
}