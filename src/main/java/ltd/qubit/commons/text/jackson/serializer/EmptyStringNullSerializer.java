////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import ltd.qubit.commons.lang.StringUtils;

/**
 * A serializer which serialize null values to empty strings.
 *
 * @author Haixing Hu
 */
public class EmptyStringNullSerializer extends JsonSerializer<Object> {

  @Override
  public void serialize(final Object value, final JsonGenerator generator,
          final SerializerProvider provider) throws IOException {
    generator.writeString(StringUtils.EMPTY);
  }
}
