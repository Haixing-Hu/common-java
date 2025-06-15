////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;

import ltd.qubit.commons.lang.ClassKey;

import static ltd.qubit.commons.datastructure.map.MapUtils.findTypeMapping;

/**
 * 线程安全的Jackson键反序列化器集合，使用并发Map来存储和管理各种类型的键反序列化器。
 *
 * <p>此类实现了Jackson的{@link KeyDeserializers}接口，提供了一个线程安全的键反序列化器注册和查找机制。
 * 键反序列化器按类型分为两类存储：类类型和接口类型，以便进行高效的类型映射查找。</p>
 *
 * @author 胡海星
 */
public class ConcurrentMapKeyDeserializers implements KeyDeserializers {

  /**
   * 存储类类型键反序列化器的并发映射表。
   */
  private final Map<ClassKey, KeyDeserializer> classMap = new ConcurrentSkipListMap<>();

  /**
   * 存储接口类型键反序列化器的并发映射表。
   */
  private final Map<ClassKey, KeyDeserializer> interfaceMap = new ConcurrentSkipListMap<>();

  /**
   * 注册指定类型的键反序列化器。
   *
   * <p>根据类型是否为接口，将键反序列化器存储到相应的映射表中。</p>
   *
   * @param type 要注册键反序列化器的类型
   * @param deserializer 对应的键反序列化器
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
   * <p>使用类型映射查找逻辑，在类映射表和接口映射表中搜索最匹配的键反序列化器。</p>
   *
   * @param type 要查找键反序列化器的类型
   * @return 对应的键反序列化器，如果未找到则返回null
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