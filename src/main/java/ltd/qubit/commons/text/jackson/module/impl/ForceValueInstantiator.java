////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.impl;

import java.io.IOException;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;

import ltd.qubit.commons.reflect.ConstructorUtils;

/**
 * 使用 Objenesis 实例化对象的 Jackson {@link ValueInstantiator}。
 *
 * @author 胡海星
 */
class ForceValueInstantiator extends ValueInstantiator {

  /**
   * 实例化器缓存，用于避免重复创建相同类型的实例化器。
   */
  private static final ClassValue<ForceValueInstantiator> INSTANTIATOR_CACHE =
      new ClassValue<>() {
    @Override
    protected ForceValueInstantiator computeValue(@Nonnull final Class<?> type) {
      return new ForceValueInstantiator(type);
    }
  };

  /**
   * 获取指定类型的实例化器。
   *
   * @param type
   *     要实例化的类型。
   * @return 对应的实例化器。
   */
  public static ForceValueInstantiator getInstance(final Class<?> type) {
    return INSTANTIATOR_CACHE.get(type);
  }

  /**
   * 要实例化的类型。
   */
  private final Class<?> type;

  /**
   * 构造一个强制值实例化器。
   *
   * @param type
   *     要实例化的类型。
   */
  private ForceValueInstantiator(final Class<?> type) {
    this.type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canCreateUsingDefault() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object createUsingDefault(final DeserializationContext ctxt) throws IOException {
    return ConstructorUtils.newInstance(type);
  }
}