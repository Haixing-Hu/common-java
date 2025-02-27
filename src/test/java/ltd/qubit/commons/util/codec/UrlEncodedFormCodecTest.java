////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link UrlEncodedFormCodec} 的单元测试。
 *
 * @author 胡海星
 */
public class UrlEncodedFormCodecTest {

  @Test
  public void testEncodeEmpty() {
    final UrlEncodedFormCodec codec = UrlEncodedFormCodec.INSTANCE;
    assertEquals("", codec.encode(null));
    assertEquals("", codec.encode(Map.of()));
  }

  @Test
  public void testEncodeSimple() {
    final UrlEncodedFormCodec codec = UrlEncodedFormCodec.INSTANCE;
    final Map<String, String> params = new HashMap<>();
    params.put("name", "John");
    params.put("age", "25");
    final String result = codec.encode(params);
    // 注意：由于Map的迭代顺序不确定，我们需要分别检查每个键值对是否存在
    assertTrue(result.contains("name=John"));
    assertTrue(result.contains("age=25"));
    assertTrue(result.contains("&"));
  }

  @Test
  public void testEncodeWithSpecialChars() {
    final UrlEncodedFormCodec codec = UrlEncodedFormCodec.INSTANCE;
    final Map<String, String> params = new HashMap<>();
    params.put("name", "张三");
    params.put("email", "test@example.com");
    params.put("url", "http://example.com/path?param=value");
    final String result = codec.encode(params);
    assertTrue(result.contains("name=" + UrlCodec.INSTANCE.encode("张三")));
    assertTrue(result.contains("email=" + UrlCodec.INSTANCE.encode("test@example.com")));
    assertTrue(result.contains("url=" + UrlCodec.INSTANCE.encode("http://example.com/path?param=value")));
  }

  @Test
  public void testEncodeWithNullValues() {
    final UrlEncodedFormCodec codec = UrlEncodedFormCodec.INSTANCE;
    final Map<String, String> params = new HashMap<>();
    params.put("key1", null);
    params.put("key2", "value2");
    final String result = codec.encode(params);
    assertTrue(result.contains("key1"));
    assertTrue(result.contains("key2=value2"));
  }

  @Test
  public void testDecodeEmpty() {
    final UrlEncodedFormCodec codec = UrlEncodedFormCodec.INSTANCE;
    assertEquals(Map.of(), codec.decode(null));
    assertEquals(Map.of(), codec.decode(""));
  }

  @Test
  public void testDecodeSimple() {
    final UrlEncodedFormCodec codec = UrlEncodedFormCodec.INSTANCE;
    final Map<String, String> result = codec.decode("name=John&age=25");
    assertEquals("John", result.get("name"));
    assertEquals("25", result.get("age"));
  }

  @Test
  public void testDecodeWithSpecialChars() {
    final UrlEncodedFormCodec codec = UrlEncodedFormCodec.INSTANCE;
    final String encoded = "name=%E5%BC%A0%E4%B8%89&email=test%40example.com"
        + "&url=http%3A%2F%2Fexample.com%2Fpath%3Fparam%3Dvalue";
    final Map<String, String> result = codec.decode(encoded);
    assertEquals("张三", result.get("name"));
    assertEquals("test@example.com", result.get("email"));
    assertEquals("http://example.com/path?param=value", result.get("url"));
  }

  @Test
  public void testDecodeWithNullValues() {
    final UrlEncodedFormCodec codec = UrlEncodedFormCodec.INSTANCE;
    final Map<String, String> result = codec.decode("key1&key2=value2");
    assertNull(result.get("key1"));
    assertEquals("value2", result.get("key2"));
  }

  @Test
  public void testRoundTrip() {
    final UrlEncodedFormCodec codec = UrlEncodedFormCodec.INSTANCE;
    final Map<String, String> original = new HashMap<>();
    original.put("name", "张三");
    original.put("email", "test@example.com");
    original.put("url", "http://example.com/path?param=value");
    original.put("empty", null);

    final String encoded = codec.encode(original);
    final Map<String, String> decoded = codec.decode(encoded);

    assertEquals(original, decoded);
  }
}
