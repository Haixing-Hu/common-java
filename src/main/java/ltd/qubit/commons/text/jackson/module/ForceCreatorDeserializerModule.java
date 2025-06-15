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
 * 使用 Objenesis 创建对象的 Jackson 模块。
 * <p>
 * 该模块在反序列化对象时使用 Objenesis 来创建对象。
 *
 * @author 胡海星
 */
@Immutable
public class ForceCreatorDeserializerModule extends SimpleModule {
  @Serial
  private static final long serialVersionUID = 5141940734440493629L;

  /**
   * 模块的单例实例。
   */
  public static final ForceCreatorDeserializerModule INSTANCE = new ForceCreatorDeserializerModule();

  /**
   * {@inheritDoc}
   */
  @Override
  public String getModuleName() {
    return this.getClass().getSimpleName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupModule(final SetupContext context) {
    super.setupModule(context);
    final BeanDeserializerModifier modifier = ForceCreatorBeanDeserializerModifier.getInstance();
    context.addBeanDeserializerModifier(modifier);
  }
}