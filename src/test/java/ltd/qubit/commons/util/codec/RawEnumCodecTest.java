////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonValue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RawEnumCodecTest {

  enum TestEnum {
    VALUE_1("x1"),
    VALUE_2("x2"),
    VALUE_3("x3");

    private final String value;

    TestEnum(final String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }
  }

  @Test
  void encodeEnumWithJsonValue() throws EncodingException {
    final RawEnumCodec codec = new RawEnumCodec(TestEnum.class, true);
    assertEquals("x1", codec.encode(TestEnum.VALUE_1));
  }

  @Test
  void encodeEnumWithoutJsonValue() throws EncodingException {
    final RawEnumCodec codec = new RawEnumCodec(TestEnum.class, false);
    assertEquals("VALUE_1", codec.encode(TestEnum.VALUE_1));
  }

  @Test
  void decodeEnumWithJsonValue() throws DecodingException {
    final RawEnumCodec codec = new RawEnumCodec(TestEnum.class, true);
    assertEquals(TestEnum.VALUE_1, codec.decode("x1"));
    assertThrows(DecodingException.class, () -> codec.decode("VALUE_1"));
  }

  @Test
  void decodeEnumWithoutJsonValue() throws DecodingException {
    final RawEnumCodec codec = new RawEnumCodec(TestEnum.class, false);
    assertEquals(TestEnum.VALUE_1, codec.decode("VALUE_1"));
    assertThrows(DecodingException.class, () -> codec.decode("x1"));
  }

  @Test
  void decodeEnumWithInvalidValue() {
    final RawEnumCodec codec = new RawEnumCodec(TestEnum.class, true);
    assertThrows(DecodingException.class, () -> codec.decode("invalid_value"));
  }

  @Test
  void decodeEnumWithEmptyString() throws DecodingException {
    final RawEnumCodec codec = new RawEnumCodec(TestEnum.class, false);
    assertNull(codec.decode(""));
  }

  @Test
  void decodeEnumWithNull() throws DecodingException {
    final RawEnumCodec codec = new RawEnumCodec(TestEnum.class, false);
    assertNull(codec.decode(null));
  }

  @Test
  void encodeEnumWithNull() throws EncodingException {
    final RawEnumCodec codec = new RawEnumCodec(TestEnum.class, false);
    assertNull(codec.encode(null));
  }
}