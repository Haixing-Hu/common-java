////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import ltd.qubit.commons.lang.ClassKey;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.ReferenceType;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import static ltd.qubit.commons.datastructure.map.MapUtils.findTypeMapping;

public class ConcurrentDeserializers implements Deserializers {

  private final Map<ClassKey, JsonDeserializer<?>> classMap = new ConcurrentSkipListMap<>();
  private final Map<ClassKey, JsonDeserializer<?>> interfaceMap = new ConcurrentSkipListMap<>();

  public <T> void put(final Class<T> type, final JsonDeserializer<T> deserializer) {
    if (type.isInterface()) {
      interfaceMap.put(new ClassKey(type), deserializer);
    } else {
      classMap.put(new ClassKey(type), deserializer);
    }
  }

  public JsonDeserializer<?> get(final Class<?> type) {
    return findTypeMapping(type, classMap, interfaceMap);
  }

  @Override
  public JsonDeserializer<?> findEnumDeserializer(final Class<?> type,
      final DeserializationConfig config, final BeanDescription beanDesc)
      throws JsonMappingException {
    return get(type);
  }

  @Override
  public JsonDeserializer<?> findTreeNodeDeserializer(
      final Class<? extends JsonNode> nodeType,
      final DeserializationConfig config, final BeanDescription beanDesc)
      throws JsonMappingException {
    return get(nodeType);
  }

  @Override
  public JsonDeserializer<?> findBeanDeserializer(final JavaType type,
      final DeserializationConfig config, final BeanDescription beanDesc)
      throws JsonMappingException {
    return get(type.getRawClass());
  }

  @Override
  public JsonDeserializer<?> findReferenceDeserializer(
      final ReferenceType refType, final DeserializationConfig config,
      final BeanDescription beanDesc,
      final TypeDeserializer contentTypeDeserializer,
      final JsonDeserializer<?> contentDeserializer)
      throws JsonMappingException {
    return get(refType.getRawClass());
  }

  @Override
  public JsonDeserializer<?> findArrayDeserializer(final ArrayType type,
      final DeserializationConfig config, final BeanDescription beanDesc,
      final TypeDeserializer elementTypeDeserializer,
      final JsonDeserializer<?> elementDeserializer)
      throws JsonMappingException {
    return get(type.getRawClass());
  }

  @Override
  public JsonDeserializer<?> findCollectionDeserializer(
      final CollectionType type, final DeserializationConfig config,
      final BeanDescription beanDesc,
      final TypeDeserializer elementTypeDeserializer,
      final JsonDeserializer<?> elementDeserializer)
      throws JsonMappingException {
    return get(type.getRawClass());
  }

  @Override
  public JsonDeserializer<?> findCollectionLikeDeserializer(
      final CollectionLikeType type, final DeserializationConfig config,
      final BeanDescription beanDesc,
      final TypeDeserializer elementTypeDeserializer,
      final JsonDeserializer<?> elementDeserializer)
      throws JsonMappingException {
    return get(type.getRawClass());
  }

  @Override
  public JsonDeserializer<?> findMapDeserializer(final MapType type,
      final DeserializationConfig config, final BeanDescription beanDesc,
      final KeyDeserializer keyDeserializer,
      final TypeDeserializer elementTypeDeserializer,
      final JsonDeserializer<?> elementDeserializer)
      throws JsonMappingException {
    return get(type.getRawClass());
  }

  @Override
  public JsonDeserializer<?> findMapLikeDeserializer(final MapLikeType type,
      final DeserializationConfig config, final BeanDescription beanDesc,
      final KeyDeserializer keyDeserializer,
      final TypeDeserializer elementTypeDeserializer,
      final JsonDeserializer<?> elementDeserializer)
      throws JsonMappingException {
    return get(type.getRawClass());
  }
}
