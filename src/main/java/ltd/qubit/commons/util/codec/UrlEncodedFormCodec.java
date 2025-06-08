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

import javax.annotation.concurrent.Immutable;

/**
 * 将一个 Map 编码为 WWW 表单字符串的编码解码器。
 *
 * @author 胡海星
 */
@Immutable
public class UrlEncodedFormCodec implements Codec<Map<String, String>, String> {

  public static final UrlEncodedFormCodec INSTANCE = new UrlEncodedFormCodec();

  @Override
  public String encode(final Map<String, String> source) {
    if (source == null || source.isEmpty()) {
      return "";
    }
    final UrlEncodingCodec urlEncodingCodec = UrlEncodingCodec.INSTANCE;
    final StringBuilder builder = new StringBuilder();
    boolean first = true;
    for (final Map.Entry<String, String> entry : source.entrySet()) {
      if (!first) {
        builder.append('&');
      }
      final String key = entry.getKey();
      final String value = entry.getValue();
      if (key != null) {
        builder.append(urlEncodingCodec.encode(key));
      }
      if (value != null) {
        builder.append('=')
               .append(urlEncodingCodec.encode(value));
      }
      first = false;
    }
    return builder.toString();
  }

  @Override
  public Map<String, String> decode(final String source) {
    if (source == null || source.isEmpty()) {
      return Map.of();
    }
    final UrlEncodingCodec urlEncodingCodec = UrlEncodingCodec.INSTANCE;
    final Map<String, String> result = new HashMap<>();
    final String[] pairs = source.split("&");
    for (final String pair : pairs) {
      final int pos = pair.indexOf('=');
      if (pos < 0) {
        final String key = urlEncodingCodec.decode(pair);
        result.put(key, null);
      } else {
        final String key = urlEncodingCodec.decode(pair.substring(0, pos));
        final String value = urlEncodingCodec.decode(pair.substring(pos + 1));
        result.put(key, value);
      }
    }
    return result;
  }
}