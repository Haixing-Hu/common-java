////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.datastructure.list.EnumList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnumListCodecTest {

  private enum TestEnum {
    VALUE1, VALUE2, VALUE3
  }

  private final EnumListCodec<TestEnum> codec = new EnumListCodec<>(TestEnum.class);

  @Test
  void encodeNullList() throws EncodingException {
    assertNull(codec.encode(null));
  }

  @Test
  void encodeEmptyList() throws EncodingException {
    assertEquals("[]", codec.encode(new ArrayList<>()));
  }

  @Test
  void encodeNonEmptyList() throws EncodingException {
    final List<TestEnum> list = new ArrayList<>();
    list.add(TestEnum.VALUE1);
    list.add(TestEnum.VALUE2);
    assertEquals("[\"VALUE1\",\"VALUE2\"]", codec.encode(list));
  }

  @Test
  void decodeNullString() throws DecodingException {
    assertNull(codec.decode(null));
  }

  @Test
  void decodeEmptyString() throws DecodingException {
    assertEquals(new EnumList<>(), codec.decode(""));
  }

  @Test
  void decodeEmptyJsonArray() throws DecodingException {
    assertEquals(new EnumList<>(), codec.decode("[]"));
  }

  @Test
  void decodeNonEmptyJsonArray() throws DecodingException {
    final List<TestEnum> expectedList = new ArrayList<>();
    expectedList.add(TestEnum.VALUE1);
    expectedList.add(TestEnum.VALUE2);
    assertEquals(expectedList, codec.decode("[\"VALUE1\",\"VALUE2\"]"));
  }

  @Test
  void decodeNonEmptyJsonArrayWithSpace() throws DecodingException {
    final List<TestEnum> expectedList = new ArrayList<>();
    expectedList.add(TestEnum.VALUE1);
    expectedList.add(TestEnum.VALUE2);
    assertEquals(expectedList, codec.decode("[  \"VALUE1\",  \"VALUE2\"  ]"));
  }

  @Test
  void decodeInvalidJson() {
    assertThrows(DecodingException.class, () -> codec.decode("[\"INVALID\"]"));
  }
}
