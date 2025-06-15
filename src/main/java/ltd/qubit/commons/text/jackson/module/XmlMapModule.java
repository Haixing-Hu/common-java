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

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.type.MapType;

import ltd.qubit.commons.text.jackson.deserializer.MapXmlDeserializer;
import ltd.qubit.commons.text.jackson.serializer.MapXmlSerializer;

/**
 * XML Map模块，为Jackson提供Map类型在XML格式下的专用序列化和反序列化处理。
 * <p>
 * 该模块针对Map类型在XML序列化和反序列化时进行特殊处理，以适应XML格式的特点。
 *
 * @author 胡海星
 */
public class XmlMapModule extends SimpleModule {
  @Serial
  private static final long serialVersionUID = 2497533104195765259L;

  /**
   * XmlMapModule的单例实例。
   */
  public static final XmlMapModule INSTANCE = new XmlMapModule();

  /**
   * 构造一个新的XmlMapModule实例。
   * <p>
   * 该构造函数会设置Map类型的专用序列化器和反序列化器修改器。
   */
  public XmlMapModule() {
    // 对 map 类型特殊处理
    this.setSerializerModifier(new BeanSerializerModifier() {
      /**
       * {@inheritDoc}
       */
      @Override
      public JsonSerializer<?> modifyMapSerializer(final SerializationConfig config,
          final MapType type, final BeanDescription beanDesc, final JsonSerializer<?> serializer) {
        return MapXmlSerializer.INSTANCE;
      }
    });
    this.setDeserializerModifier(new BeanDeserializerModifier() {
      /**
       * {@inheritDoc}
       */
      @Override
      public JsonDeserializer<?> modifyMapDeserializer(final DeserializationConfig config,
          final MapType type, final BeanDescription beanDesc, final JsonDeserializer<?> deserializer) {
        return new MapXmlDeserializer(type);
      }
    });
  }

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
  }
}