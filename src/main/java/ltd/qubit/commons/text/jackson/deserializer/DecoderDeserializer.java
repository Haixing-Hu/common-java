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
import java.io.Serial;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.util.codec.Decoder;
import ltd.qubit.commons.util.codec.DecodingException;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.StringUtils.isEmpty;

/**
 * 使用解码器将字符串解码为指定值的Jackson反序列化器。
 *
 * @param <T>
 *     要反序列化的值的类型。
 * @author 胡海星
 */
@Immutable
public class DecoderDeserializer<T> extends StdDeserializer<T> {

  @Serial
  private static final long serialVersionUID = 2275892683881205502L;

  /**
   * 用于解码的解码器。
   */
  protected final Decoder<String, T> decoder;

  /**
   * 构造解码器反序列化器。
   *
   * @param valueClass
   *     值的类。
   * @param decoder
   *     用于解码的解码器。
   */
  protected DecoderDeserializer(final Class<T> valueClass,
      final Decoder<String, T> decoder) {
    super(valueClass);
    this.decoder = requireNonNull("decoder", decoder);
  }

  /**
   * {@inheritDoc}
   */
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
      case VALUE_NUMBER_FLOAT:
      case VALUE_TRUE:
      case VALUE_FALSE:
      case VALUE_NULL:
        break;
      default:
        throw new IllegalStateException("Invalid token of the parser: " + token);
    }
  }
}
