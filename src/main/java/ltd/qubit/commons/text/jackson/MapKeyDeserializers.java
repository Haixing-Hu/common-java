////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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

/**
 * 键反序列化器映射表，用于注册和查找特定类型的键反序列化器。
 *
 * @author 胡海星
 */
public class MapKeyDeserializers implements KeyDeserializers {

  /**
   * 类型到键反序列化器的映射表（用于具体类）。
   */
  private final Map<ClassKey, KeyDeserializer> classMap = new HashMap<>();

  /**
   * 类型到键反序列化器的映射表（用于接口）。
   */
  private final Map<ClassKey, KeyDeserializer> interfaceMap = new HashMap<>();

  /**
   * 获取映射表中键反序列化器的总数量。
   *
   * @return 键反序列化器的总数量。
   */
  public int size() {
    return classMap.size() + interfaceMap.size();
  }

  /**
   * 清空所有映射表。
   */
  public void clear() {
    classMap.clear();
    interfaceMap.clear();
  }

  /**
   * 将指定类型的键反序列化器添加到映射表中。
   *
   * @param type
   *     要注册的类型。
   * @param deserializer
   *     对应的键反序列化器。
   */
  public void put(final Class<?> type, final KeyDeserializer deserializer) {
    if (type.isInterface()) {
      interfaceMap.put(new ClassKey(type), deserializer);
    } else {
      classMap.put(new ClassKey(type), deserializer);
    }
  }

  /**
   * 获取指定类型的键反序列化器。
   *
   * @param type
   *     要查找的类型。
   * @return 对应的键反序列化器，如果找不到则返回 {@code null}。
   */
  public KeyDeserializer get(final Class<?> type) {
    return findTypeMapping(type, classMap, interfaceMap);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KeyDeserializer findKeyDeserializer(final JavaType type,
      final DeserializationConfig config, final BeanDescription beanDesc)
      throws JsonMappingException {
    return get(type.getRawClass());
  }
}