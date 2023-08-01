////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import ltd.qubit.commons.lang.ClassKey;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;

import static ltd.qubit.commons.datastructure.map.MapUtils.findTypeMapping;

public class ConcurrentKeyDeserializers implements KeyDeserializers {

  private final Map<ClassKey, KeyDeserializer> classMap = new ConcurrentSkipListMap<>();
  private final Map<ClassKey, KeyDeserializer> interfaceMap = new ConcurrentSkipListMap<>();

  public <T> void put(final Class<T> type, final KeyDeserializer deserializer) {
    if (type.isInterface()) {
      interfaceMap.put(new ClassKey(type), deserializer);
    } else {
      classMap.put(new ClassKey(type), deserializer);
    }
  }

  public KeyDeserializer get(final Class<?> type) {
    return findTypeMapping(type, classMap, interfaceMap);
  }

  @Override
  public KeyDeserializer findKeyDeserializer(final JavaType type,
      final DeserializationConfig config, final BeanDescription beanDesc)
      throws JsonMappingException {
    return get(type.getRawClass());
  }
}
