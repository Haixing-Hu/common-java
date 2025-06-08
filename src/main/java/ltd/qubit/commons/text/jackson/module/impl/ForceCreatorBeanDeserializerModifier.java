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
 * A {@link BeanDeserializerModifier} that forces the use of Objenesis to create
 * instances of beans that cannot be instantiated by Jackson.
 * <p>
 * This modifier is used to create a {@link ForceValueInstantiator} for
 * beans that cannot be instantiated by Jackson, such as beans without a default
 * constructor.
 *
 * @author Haixing Hu
 */
public class ForceCreatorBeanDeserializerModifier extends BeanDeserializerModifier {

  private static final ForceCreatorBeanDeserializerModifier INSTANCE =
      new ForceCreatorBeanDeserializerModifier();

  public static ForceCreatorBeanDeserializerModifier getInstance() {
    return INSTANCE;
  }

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

  @Override
  public JsonDeserializer<?> modifyMapDeserializer(final DeserializationConfig config,
      final MapType type, final BeanDescription beanDesc,
      final JsonDeserializer<?> deserializer) {
    return new ForceMapDeserializer((MapDeserializer) deserializer);
  }

  private static boolean isNotPossibleInstantiation(final ValueInstantiator valueInstantiator) {
    return !(valueInstantiator.canCreateUsingDelegate()
        || valueInstantiator.canCreateUsingArrayDelegate()
        || valueInstantiator.canCreateFromObjectWith());
  }
}