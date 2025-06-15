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
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.ReferenceType;

import ltd.qubit.commons.lang.ClassKey;

import static ltd.qubit.commons.datastructure.map.MapUtils.findTypeMapping;

/**
 * 线程安全的Jackson序列化器集合，使用并发Map来存储和管理各种类型的序列化器。
 *
 * <p>此类实现了Jackson的{@link Serializers}接口，提供了一个线程安全的序列化器注册和查找机制。
 * 序列化器按类型分为两类存储：类类型和接口类型，以便进行高效的类型映射查找。</p>
 *
 * @author 胡海星
 */
public class ConcurrentMapSerializers implements Serializers {

  /**
   * 存储类类型序列化器的并发映射表。
   */
  private final Map<ClassKey, JsonSerializer<?>> classMap = new ConcurrentSkipListMap<>();

  /**
   * 存储接口类型序列化器的并发映射表。
   */
  private final Map<ClassKey, JsonSerializer<?>> interfaceMap = new ConcurrentSkipListMap<>();

  /**
   * 注册指定类型的序列化器。
   *
   * <p>根据类型是否为接口，将序列化器存储到相应的映射表中。</p>
   *
   * @param type 要注册序列化器的类型
   * @param serializer 对应的序列化器
   */
  public void put(final Class<?> type, final JsonSerializer<?> serializer) {
    if (type.isInterface()) {
      interfaceMap.put(new ClassKey(type), serializer);
    } else {
      classMap.put(new ClassKey(type), serializer);
    }
  }

  /**
   * 获取指定类型的序列化器。
   *
   * <p>使用类型映射查找逻辑，在类映射表和接口映射表中搜索最匹配的序列化器。</p>
   *
   * @param type 要查找序列化器的类型
   * @return 对应的序列化器，如果未找到则返回null
   */
  public JsonSerializer<?> get(final Class<?> type) {
    return findTypeMapping(type, classMap, interfaceMap);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<?> findSerializer(final SerializationConfig config,
      final JavaType type, final BeanDescription beanDesc) {
    return get(type.getRawClass());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<?> findReferenceSerializer(
      final SerializationConfig config, final ReferenceType type,
      final BeanDescription beanDesc,
      final TypeSerializer contentTypeSerializer,
      final JsonSerializer<Object> contentValueSerializer) {
    return findSerializer(config, type, beanDesc);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<?> findArraySerializer(final SerializationConfig config,
      final ArrayType type, final BeanDescription beanDesc,
      final TypeSerializer elementTypeSerializer,
      final JsonSerializer<Object> elementValueSerializer) {
    return findSerializer(config, type, beanDesc);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<?> findCollectionSerializer(
      final SerializationConfig config, final CollectionType type,
      final BeanDescription beanDesc,
      final TypeSerializer elementTypeSerializer,
      final JsonSerializer<Object> elementValueSerializer) {
    return findSerializer(config, type, beanDesc);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<?> findCollectionLikeSerializer(
      final SerializationConfig config, final CollectionLikeType type,
      final BeanDescription beanDesc,
      final TypeSerializer elementTypeSerializer,
      final JsonSerializer<Object> elementValueSerializer) {
    return findSerializer(config, type, beanDesc);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<?> findMapSerializer(final SerializationConfig config,
      final MapType type, final BeanDescription beanDesc,
      final JsonSerializer<Object> keySerializer,
      final TypeSerializer elementTypeSerializer,
      final JsonSerializer<Object> elementValueSerializer) {
    return findSerializer(config, type, beanDesc);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<?> findMapLikeSerializer(
      final SerializationConfig config, final MapLikeType type,
      final BeanDescription beanDesc,
      final JsonSerializer<Object> keySerializer,
      final TypeSerializer elementTypeSerializer,
      final JsonSerializer<Object> elementValueSerializer) {
    return findSerializer(config, type, beanDesc);
  }
}