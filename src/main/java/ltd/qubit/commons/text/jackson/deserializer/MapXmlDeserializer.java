////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ltd.qubit.commons.datastructure.CollectionUtils;

import static ltd.qubit.commons.text.jackson.JacksonUtils.ensureCurrentName;
import static ltd.qubit.commons.text.jackson.JacksonUtils.ensureCurrentToken;
import static ltd.qubit.commons.text.jackson.JacksonUtils.ensureNextToken;

/**
 * The {@link JsonDeserializer} for deserializing {@link Map} objects in XML format.
 * <p>
 * FIXME: there is bugs
 *
 * @author Haixing Hu
 */
@SuppressWarnings("rawtypes")
public class MapXmlDeserializer extends JsonDeserializer<Map> implements ContextualDeserializer {

  public static final MapXmlDeserializer INSTANCE = new MapXmlDeserializer();

  private final JavaType mapType;
  private final JavaType keyType;
  private final JavaType valueType;

  public MapXmlDeserializer() {
    this(null);
  }

  public MapXmlDeserializer(final JavaType mapType) {
    this.mapType = mapType;
    if (mapType != null) {
      keyType = mapType.getKeyType();
      valueType = mapType.getContentType();
    } else {
      keyType = null;
      valueType = null;
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public Map deserialize(final JsonParser jp, final DeserializationContext context)
      throws IOException {
    if (mapType == null) {
      throw new IllegalStateException("The name and type must be set before deserialization");
    }
    final XmlMapper mapper = (XmlMapper) jp.getCodec();
    final Class<? extends Map> mapClass = (Class<? extends Map>) mapType.getRawClass();
    final Map map = CollectionUtils.constructSameTypeOfMap(mapClass);
    // final Map map = ConstructorUtils.newInstance(mapClass);
    // Ensure we're at the start of the map field
    ensureCurrentToken(jp, JsonToken.START_OBJECT);
    // move to the start of entry array
    while (jp.nextToken() != JsonToken.END_OBJECT) {
      ensureCurrentName(jp, "entry");
      ensureNextToken(jp, JsonToken.START_OBJECT);
      ensureNextToken(jp, JsonToken.FIELD_NAME);
      ensureCurrentName(jp, "key");
      jp.nextToken();
      final Object key = mapper.readValue(jp, keyType);
      ensureNextToken(jp, JsonToken.FIELD_NAME);
      ensureCurrentName(jp, "value");
      jp.nextToken();
      final Object value = mapper.readValue(jp, valueType);
      ensureNextToken(jp, JsonToken.END_OBJECT);
      map.put(key, value);
    }
    return map;
  }

  @Override
  public JsonDeserializer<?> createContextual(final DeserializationContext context,
      final BeanProperty property) throws JsonMappingException {
    return new MapXmlDeserializer(property.getType());
  }
}
