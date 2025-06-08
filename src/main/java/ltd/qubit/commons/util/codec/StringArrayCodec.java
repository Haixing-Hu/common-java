////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.text.Stripper;

import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_STRING_ARRAY;

/**
 * 字符串数组的编码解码器。
 *
 * <p>此编码解码器将字符串数组以JSON方式进行编码和解码，从而可将字符串数组存入数据库的某个
 * 单独字段中。</p>
 *
 * @author 胡海星
 */
public class StringArrayCodec implements Codec<String[], String> {

  // 这里不要使用 CustomizedJsonMapper，因为没必要且 CustomizedJsonMapper 初始化时可能会
  // 创建此 Codec 实例从而造成递归引用
  private final JsonMapper mapper = new JsonMapper();

  @Override
  public String[] decode(final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (text == null) {
      return null;
    }
    if (text.isEmpty() || "[]".equals(text)) {
      return EMPTY_STRING_ARRAY;
    }
    try {
      return mapper.readValue(text, String[].class);
    } catch (final JsonProcessingException e) {
      throw new DecodingException("Invalid string array JSON format: " + text, e);
    }
  }

  @Override
  public String encode(final String[] array) throws EncodingException {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return "[]";
    } else {
      try {
        return mapper.writeValueAsString(array);
      } catch (final JsonProcessingException e) {
        throw new EncodingException("Failed to encode the string list: "
            + Arrays.toString(array), e);
      }
    }
  }
}