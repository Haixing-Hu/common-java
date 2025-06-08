////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module;

import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;

import ltd.qubit.commons.text.jackson.module.impl.ForceCreatorBeanDeserializerModifier;

/**
 * A Jackson module which uses Objenesis to create objects.
 * <p>
 * This module is used to create objects using Objenesis when deserializing objects.
 *
 * @author Haixing Hu
 */
@Immutable
public class ForceCreatorDeserializerModule extends SimpleModule {
  @Serial
  private static final long serialVersionUID = 5141940734440493629L;

  public static final ForceCreatorDeserializerModule INSTANCE = new ForceCreatorDeserializerModule();

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