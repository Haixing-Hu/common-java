/// /////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module;

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

public class XmlMapModule extends SimpleModule {

  public static final XmlMapModule INSTANCE = new XmlMapModule();

  public XmlMapModule() {
    // 对 map 类型特殊处理
    this.setSerializerModifier(new BeanSerializerModifier() {
      @Override
      public JsonSerializer<?> modifyMapSerializer(final SerializationConfig config,
          final MapType type, final BeanDescription beanDesc, final JsonSerializer<?> serializer) {
        return MapXmlSerializer.INSTANCE;
      }
    });
    this.setDeserializerModifier(new BeanDeserializerModifier() {
      @Override
      public JsonDeserializer<?> modifyMapDeserializer(final DeserializationConfig config,
          final MapType type, final BeanDescription beanDesc, final JsonDeserializer<?> deserializer) {
        return new MapXmlDeserializer(type);
      }
    });
  }

  @Override
  public String getModuleName() {
    return this.getClass().getSimpleName();
  }

  @Override
  public void setupModule(final SetupContext context) {
    super.setupModule(context);
  }
}
