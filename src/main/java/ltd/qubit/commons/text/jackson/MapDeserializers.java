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

import ltd.qubit.commons.lang.ClassKey;

import static ltd.qubit.commons.datastructure.map.MapUtils.findTypeMapping;

/**
 * 反序列化器映射表，用于注册和查找特定类型的反序列化器。
 *
 * @author 胡海星
 */
public class MapDeserializers implements Deserializers {

  /**
   * 类型到反序列化器的映射表（用于具体类）。
   */
  private final Map<ClassKey, JsonDeserializer<?>> classMap = new HashMap<>();

  /**
   * 类型到反序列化器的映射表（用于接口）。
   */
  private final Map<ClassKey, JsonDeserializer<?>> interfaceMap = new HashMap<>();

  /**
   * 获取映射表中反序列化器的总数量。
   *
   * @return 反序列化器的总数量。
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
   * 将指定类型的反序列化器添加到映射表中。
   *
   * @param type
   *     要注册的类型。
   * @param deserializer
   *     对应的反序列化器。
   */
  public void put(final Class<?> type, final JsonDeserializer<?> deserializer) {
    if (type.isInterface()) {
      interfaceMap.put(new ClassKey(type), deserializer);
    } else {
      classMap.put(new ClassKey(type), deserializer);
    }
  }

  /**
   * 获取指定类型的反序列化器。
   *
   * @param type
   *     要查找的类型。
   * @return 对应的反序列化器，如果找不到则返回 {@code null}。
   */
  public JsonDeserializer<?> get(final Class<?> type) {
    return findTypeMapping(type, classMap, interfaceMap);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<?> findEnumDeserializer(final Class<?> type,
      final DeserializationConfig config, final BeanDescription beanDesc)
      throws JsonMappingException {
    return get(type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<?> findTreeNodeDeserializer(
      final Class<? extends JsonNode> nodeType,
      final DeserializationConfig config, final BeanDescription beanDesc)
      throws JsonMappingException {
    return get(nodeType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<?> findBeanDeserializer(final JavaType type,
      final DeserializationConfig config, final BeanDescription beanDesc)
      throws JsonMappingException {
    return get(type.getRawClass());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<?> findReferenceDeserializer(
      final ReferenceType refType, final DeserializationConfig config,
      final BeanDescription beanDesc,
      final TypeDeserializer contentTypeDeserializer,
      final JsonDeserializer<?> contentDeserializer)
      throws JsonMappingException {
    return get(refType.getRawClass());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<?> findArrayDeserializer(final ArrayType type,
      final DeserializationConfig config, final BeanDescription beanDesc,
      final TypeDeserializer elementTypeDeserializer,
      final JsonDeserializer<?> elementDeserializer)
      throws JsonMappingException {
    return get(type.getRawClass());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<?> findCollectionDeserializer(
      final CollectionType type, final DeserializationConfig config,
      final BeanDescription beanDesc,
      final TypeDeserializer elementTypeDeserializer,
      final JsonDeserializer<?> elementDeserializer)
      throws JsonMappingException {
    return get(type.getRawClass());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<?> findCollectionLikeDeserializer(
      final CollectionLikeType type, final DeserializationConfig config,
      final BeanDescription beanDesc,
      final TypeDeserializer elementTypeDeserializer,
      final JsonDeserializer<?> elementDeserializer)
      throws JsonMappingException {
    return get(type.getRawClass());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<?> findMapDeserializer(final MapType type,
      final DeserializationConfig config, final BeanDescription beanDesc,
      final KeyDeserializer keyDeserializer,
      final TypeDeserializer elementTypeDeserializer,
      final JsonDeserializer<?> elementDeserializer)
      throws JsonMappingException {
    return get(type.getRawClass());
  }

  /**
   * {@inheritDoc}
   */
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