////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.annotation.concurrent.Immutable;

/**
 * 实现 URL encoding 的编码解码器。
 *
 * @author 胡海星
 */
@Immutable
public class UrlCodec implements Codec<String, String> {

  public static final UrlCodec INSTANCE = new UrlCodec();

  @Override
  public String decode(final String source) {
    return URLDecoder.decode(source, StandardCharsets.UTF_8);
  }

  @Override
  public String encode(final String source) {
    return URLEncoder.encode(source, StandardCharsets.UTF_8);
  }
}
