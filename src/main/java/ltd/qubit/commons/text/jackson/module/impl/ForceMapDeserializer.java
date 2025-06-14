////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.module.impl;

import java.io.IOException;
import java.io.Serial;
import java.lang.reflect.Constructor;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.NullValueProvider;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class ForceMapDeserializer extends MapDeserializer {

  @Serial
  private static final long serialVersionUID = -7548120101370175657L;

  public ForceMapDeserializer(final JavaType mapType,
      final ValueInstantiator valueInstantiator,
      final KeyDeserializer keyDeserializer,
      final JsonDeserializer<Object> valueDeserializer,
      final TypeDeserializer valueTypeDeserializer) {
    super(mapType, valueInstantiator, keyDeserializer, valueDeserializer, valueTypeDeserializer);
  }

  public ForceMapDeserializer(final MapDeserializer src) {
    super(src);
  }

  protected ForceMapDeserializer(final MapDeserializer src,
      final KeyDeserializer keyDeserializer,
      final JsonDeserializer<Object> valueDeserializer,
      final TypeDeserializer valueTypeDeserializer,
      final NullValueProvider nullValueProvider,
      final Set<String> ignorable) {
    super(src, keyDeserializer, valueDeserializer, valueTypeDeserializer, nullValueProvider, ignorable);
  }


  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public Map<Object, Object> deserialize(final JsonParser parser,
      final DeserializationContext context) throws IOException {
    if (!_valueInstantiator.canInstantiate()) {
      // Usually, readonly type is wrapped super class
      for (final Constructor<?> constructor : this._valueClass.getDeclaredConstructors()) {
        final Class<? extends Map> superMapClass = (Class<? extends Map>) _valueClass.getSuperclass();
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        if ((parameterTypes.length == 1) && (parameterTypes[0] == superMapClass)) {
          final TypeFactory typeFactory = context.getTypeFactory();
          final JavaType keyType = _containerType.getKeyType();
          final JavaType contentType = _containerType.getContentType();
          final MapType superMapType = typeFactory.constructMapType(superMapClass, keyType, contentType);
          final DeserializationConfig config = context.getConfig();
          final JavaType superType = typeFactory.constructType(superMapClass);
          final BeanDescription superTypeBeanDescription = config.getClassIntrospector().forDeserialization(config, superType, config);
          final DeserializerFactory factory = context.getFactory();
          final Type[] typeArguments = superMapClass.getGenericInterfaces();
          final JavaType superKeyType = typeFactory.constructType(typeArguments[0]);
          final JavaType superValueType = typeFactory.constructType(typeArguments[1]);
          final Map<Object, Object> superTypeDeser = new MapDeserializer(superMapType,
              factory.findValueInstantiator(context, superTypeBeanDescription),
              StdKeyDeserializer.forType(superKeyType.getRawClass()),
              context.findNonContextualValueDeserializer(superValueType),
              factory.findTypeDeserializer(config, superValueType)).deserialize(parser, context);
          try {
            // 检查构造函数是否可访问
            if (!constructor.canAccess(null)) {
              constructor.setAccessible(true); // 如果不兼容仍可能抛出异常
            }
            try {
              return (Map<Object, Object>) constructor.newInstance(superTypeDeser);
            } finally {
              // 不需要显式恢复 setAccessible(false)，因为它会保留状态。
            }
          } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeanCreationException("Create instance \"" + this._valueClass.getName() + "\" failed", e);
          } catch (final InaccessibleObjectException e) {
            throw new BeanCreationException("Constructor is inaccessible. JDK 9+ requires opening modules explicitly.", e);
          }
        }
      }
    }
    return super.deserialize(parser, context);
  }

  @Override
  public JsonDeserializer<?> createContextual(final DeserializationContext context,
      final BeanProperty property) throws JsonMappingException {
    final MapDeserializer src = (MapDeserializer) super.createContextual(context, property);
    return new ForceMapDeserializer(src);
  }
}