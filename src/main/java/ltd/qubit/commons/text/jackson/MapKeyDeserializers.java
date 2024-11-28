////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;

import ltd.qubit.commons.lang.ClassKey;

import static ltd.qubit.commons.datastructure.map.MapUtils.findTypeMapping;

public class MapKeyDeserializers implements KeyDeserializers {

  private final Map<ClassKey, KeyDeserializer> classMap = new HashMap<>();
  private final Map<ClassKey, KeyDeserializer> interfaceMap = new HashMap<>();

  public int size() {
    return classMap.size() + interfaceMap.size();
  }

  public void clear() {
    classMap.clear();
    interfaceMap.clear();
  }

  public void put(final Class<?> type, final KeyDeserializer deserializer) {
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
