////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;

import ltd.qubit.commons.text.jackson.impl.ForceCreatorBeanDeserializerModifier;

/**
 * A Jackson module which uses Objenesis to create objects.
 * <p>
 * This module is used to create objects using Objenesis when deserializing objects.
 *
 * @author Haixing Hu
 */
public class ForceCreatorDeserializerModule extends SimpleModule {

  public static ForceCreatorDeserializerModule INSTANCE = new ForceCreatorDeserializerModule();

  @Override
  public String getModuleName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public void setupModule(final SetupContext context) {
    super.setupModule(context);
    final BeanDeserializerModifier modifier = ForceCreatorBeanDeserializerModifier.getInstance();
    context.addBeanDeserializerModifier(modifier);
  }
}
