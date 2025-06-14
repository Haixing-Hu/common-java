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

public class ConcurrentMapSerializers implements Serializers {

  private final Map<ClassKey, JsonSerializer<?>> classMap = new ConcurrentSkipListMap<>();
  private final Map<ClassKey, JsonSerializer<?>> interfaceMap = new ConcurrentSkipListMap<>();

  public void put(final Class<?> type, final JsonSerializer<?> serializer) {
    if (type.isInterface()) {
      interfaceMap.put(new ClassKey(type), serializer);
    } else {
      classMap.put(new ClassKey(type), serializer);
    }
  }

  public JsonSerializer<?> get(final Class<?> type) {
    return findTypeMapping(type, classMap, interfaceMap);
  }

  @Override
  public JsonSerializer<?> findSerializer(final SerializationConfig config,
      final JavaType type, final BeanDescription beanDesc) {
    return get(type.getRawClass());
  }

  @Override
  public JsonSerializer<?> findReferenceSerializer(
      final SerializationConfig config, final ReferenceType type,
      final BeanDescription beanDesc,
      final TypeSerializer contentTypeSerializer,
      final JsonSerializer<Object> contentValueSerializer) {
    return findSerializer(config, type, beanDesc);
  }

  @Override
  public JsonSerializer<?> findArraySerializer(final SerializationConfig config,
      final ArrayType type, final BeanDescription beanDesc,
      final TypeSerializer elementTypeSerializer,
      final JsonSerializer<Object> elementValueSerializer) {
    return findSerializer(config, type, beanDesc);
  }

  @Override
  public JsonSerializer<?> findCollectionSerializer(
      final SerializationConfig config, final CollectionType type,
      final BeanDescription beanDesc,
      final TypeSerializer elementTypeSerializer,
      final JsonSerializer<Object> elementValueSerializer) {
    return findSerializer(config, type, beanDesc);
  }

  @Override
  public JsonSerializer<?> findCollectionLikeSerializer(
      final SerializationConfig config, final CollectionLikeType type,
      final BeanDescription beanDesc,
      final TypeSerializer elementTypeSerializer,
      final JsonSerializer<Object> elementValueSerializer) {
    return findSerializer(config, type, beanDesc);
  }

  @Override
  public JsonSerializer<?> findMapSerializer(final SerializationConfig config,
      final MapType type, final BeanDescription beanDesc,
      final JsonSerializer<Object> keySerializer,
      final TypeSerializer elementTypeSerializer,
      final JsonSerializer<Object> elementValueSerializer) {
    return findSerializer(config, type, beanDesc);
  }

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