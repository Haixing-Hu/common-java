////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.DecodingException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import javax.annotation.concurrent.Immutable;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;

/**
 * The deserializer which use a decoder to decode string to the specified value.
 *
 * @param <T>
 *     the type of values to be deserialized.
 */
@Immutable
public class DecoderDeserializer<T> extends StdDeserializer<T> {

  private static final long serialVersionUID = 2275892683881205502L;

  protected final Decoder<String, T> decoder;

  protected DecoderDeserializer(final Class<T> valueClass,
      final Decoder<String, T> decoder) {
    super(valueClass);
    this.decoder = requireNonNull("decoder", decoder);
  }

  @Override
  public T deserialize(final JsonParser parser,
      final DeserializationContext context) throws IOException {
    final String text = getValueText(parser);
    final String str = new Stripper().strip(text);
    if (isEmpty(str)) {
      return null;
    } else {
      try {
        return decoder.decode(str);
      } catch (final DecodingException e) {
        throw context.weirdStringException(text, _valueClass, e.getMessage());
      }
    }
  }

  private String getValueText(final JsonParser parser) throws IOException {
    final JsonToken token = parser.currentToken();
    switch (token) {
      case START_OBJECT:
        parser.nextToken();
        ensureToken(parser, JsonToken.FIELD_NAME);
        parser.nextToken();
        ensureValueToken(parser);
        return parser.getText();
      case FIELD_NAME:
        parser.nextToken();
        ensureValueToken(parser);
        return parser.getText();
      default:
        ensureValueToken(parser);
        return parser.getText();
    }
  }

  private void ensureToken(final JsonParser parser, final JsonToken expected)
      throws IOException {
    final JsonToken token = parser.currentToken();
    if (token != expected) {
      throw new IllegalStateException("Invalid token of the parser: " + token.name()
        + ". Expected token is: " + expected.name());
    }
  }

  private void ensureValueToken(final JsonParser parser)
      throws IOException {
    final JsonToken token = parser.currentToken();
    switch (token) {
      case VALUE_STRING:
      case VALUE_NUMBER_INT:
      case VALUE_TRUE:
      case VALUE_FALSE:
      case VALUE_NULL:
        break;
      default:
        throw new IllegalStateException("Invalid token of the parser: " + token);
    }
  }
}
