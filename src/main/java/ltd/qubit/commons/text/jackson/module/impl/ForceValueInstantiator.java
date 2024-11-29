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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;

/**
 * A Jackson {@link ValueInstantiator} which uses Objenesis to instantiate objects.
 *
 * @author Haixing Hu
 */
class ForceValueInstantiator extends ValueInstantiator {

  private static final Objenesis OBJENESIS = new ObjenesisStd();

  private static final ConcurrentMap<Class<?>, ForceValueInstantiator>
      INSTANTIATOR_CACHE = new ConcurrentHashMap<>();

  public static ForceValueInstantiator getInstance(final Class<?> type) {
    final ForceValueInstantiator instantiator =
        INSTANTIATOR_CACHE.putIfAbsent(type, new ForceValueInstantiator(type));
    if (instantiator == null) {
      return INSTANTIATOR_CACHE.get(type);
    }
    return instantiator;
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
    return OBJENESIS.newInstance(type);
  }
}
