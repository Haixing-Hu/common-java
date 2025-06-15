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
 * 序列化器映射表，用于注册和查找特定类型的序列化器。
 *
 * @author 胡海星
 */
public class MapSerializers implements Serializers {

  /**
   * 类型到序列化器的映射表（用于具体类）。
   */
  private final Map<ClassKey, JsonSerializer<?>> classMap = new HashMap<>();

  /**
   * 类型到序列化器的映射表（用于接口）。
   */
  private final Map<ClassKey, JsonSerializer<?>> interfaceMap = new HashMap<>();

  /**
   * 获取映射表中序列化器的总数量。
   *
   * @return 序列化器的总数量。
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
   * 将指定类型的序列化器添加到映射表中。
   *
   * @param type
   *     要注册的类型。
   * @param serializer
   *     对应的序列化器。
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
   * @param type
   *     要查找的类型。
   * @return 对应的序列化器，如果找不到则返回 {@code null}。
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