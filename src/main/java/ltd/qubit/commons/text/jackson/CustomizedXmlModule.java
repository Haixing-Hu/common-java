////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import ltd.qubit.commons.text.jackson.deserializer.StripStringDeserializer;
import ltd.qubit.commons.text.jackson.serializer.StripStringSerializer;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.NamingBase;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlAnnotationIntrospector;

public class CustomizedXmlModule extends SimpleModule {

  private static final long serialVersionUID = -2738607014449654700L;

  private final NamingBase namingStrategy;

  public CustomizedXmlModule(final NamingBase namingStrategy) {
    this.namingStrategy = namingStrategy;
    // 对字符串字段值需要 strip 前后空白字符
    this.addDeserializer(String.class, StripStringDeserializer.INSTANCE);
    this.addSerializer(String.class, StripStringSerializer.INSTANCE);
  }

  @Override
  public void setupModule(final SetupContext context) {
    super.setupModule(context);
    context.appendAnnotationIntrospector(new JacksonXmlAnnotationIntrospector());
    context.appendAnnotationIntrospector(new XmlNameConversionIntrospector(namingStrategy));
  }
}
