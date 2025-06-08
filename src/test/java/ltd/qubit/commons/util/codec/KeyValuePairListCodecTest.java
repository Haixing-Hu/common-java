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

import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.math.RandomEx;
import ltd.qubit.commons.util.pair.KeyValuePair;
import ltd.qubit.commons.util.pair.KeyValuePairList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyValuePairListCodecTest {

  private static final int TEST_COUNT = 20;

  private static final int MAX_KEY_VALUE_PAIRS = 5;

  private final RandomEx random = new RandomEx();

  private final JsonMapper mapper = new JsonMapper();

  private KeyValuePair createKeyValuePair() {
    final String key = random.nextString();
    final String value = random.nextString();
    return new KeyValuePair(key, value);
  }

  private KeyValuePairList createKeyValuePairList() {
    final int n = random.nextInt(MAX_KEY_VALUE_PAIRS + 1);
    final KeyValuePairList params = new KeyValuePairList();
    for (int i = 0; i < n; ++i) {
      final KeyValuePair kv = createKeyValuePair();
      params.add(kv);
    }
    return normalizeFormat(params);
  }

  private static KeyValuePairList normalizeFormat(final KeyValuePairList obj) {
    if (obj != null) {
      if (obj.size() == 0) {
        return null;
      }
    }
    return obj;
  }

  @Test
  public void testEncodeDecode() throws Exception {
    final KeyValuePairListCodec codec = new KeyValuePairListCodec();
    for (int i = 0; i < TEST_COUNT; ++i) {
      final KeyValuePairList params = createKeyValuePairList();
      final String str = codec.encode(params);
      System.out.println("Encoded:\n" + str);
      final KeyValuePairList actual = codec.decode(str);
      System.out.println("Actual:\n" + actual);
      assertEquals(params, actual);
    }
  }
}