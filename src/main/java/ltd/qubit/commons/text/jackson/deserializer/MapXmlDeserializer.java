////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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

  public static final String ENTRY_FIELD_NAME = "entry";

  public static final String KEY_FIELD_NAME = "key";

  public static final String VALUE_FIELD_NAME = "value";

  private final JavaType mapType;
  private final JavaType keyType;
  private final JavaType valueType;

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
    // construct a map object with the same type as the mapType
    final Map map = CollectionUtils.constructSameTypeOfMap(mapClass);
    final JsonToken token = jp.currentToken();
    if (token == JsonToken.VALUE_NULL) {
      return null;
    } else if (token == JsonToken.VALUE_STRING && jp.getText().isEmpty()) {
      // Treat empty string as an empty map
      return map;
    } else if (token != JsonToken.START_OBJECT) {
      // Ensure we're at the start of the map field
      throw new IOException("The map must be represented as an object");
    }
    // move to the start of entry array
    while (jp.nextToken() != JsonToken.END_OBJECT) {
      ensureCurrentName(jp, ENTRY_FIELD_NAME);
      ensureNextToken(jp, JsonToken.START_OBJECT);
      ensureNextToken(jp, JsonToken.FIELD_NAME);
      ensureCurrentName(jp, KEY_FIELD_NAME);
      jp.nextToken();
      final Object key = mapper.readValue(jp, keyType);
      ensureNextToken(jp, JsonToken.FIELD_NAME);
      ensureCurrentName(jp, VALUE_FIELD_NAME);
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