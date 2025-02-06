////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * A Jackson {@link ValueInstantiator} which uses Objenesis to instantiate objects.
 *
 * @author Haixing Hu
 */
class ForceValueInstantiator extends ValueInstantiator {

  private static final ClassValue<ForceValueInstantiator> INSTANTIATOR_CACHE =
      new ClassValue<>() {
    @Override
    protected ForceValueInstantiator computeValue(@Nonnull final Class<?> type) {
      return new ForceValueInstantiator(type);
    }
  };

  public static ForceValueInstantiator getInstance(final Class<?> type) {
    return INSTANTIATOR_CACHE.get(type);
  }

  private final Class<?> type;

  private ForceValueInstantiator(final Class<?> type) {
    this.type = type;
  }

  @Override
  public boolean canCreateUsingDefault() {
    return true;
  }

  @Override
  public Object createUsingDefault(final DeserializationContext ctxt) throws IOException {
    return ConstructorUtils.newInstance(type);
  }
}
