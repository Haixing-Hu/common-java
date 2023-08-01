////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import ltd.qubit.commons.datastructure.list.StringList;
import ltd.qubit.commons.text.Stripper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * {@link StringList}类的编码解码器。
 *
 * <p>此编码解码器将字符串列表以JSON方式进行编码和解码，从而可将字符串列表存入数据库的某个
 * 单独字段中。</p>
 *
 * @author 胡海星
 */
public class StringListCodec implements Codec<StringList, String> {

  // 这里不要使用 CustomizedJsonMapper，因为没必要且 CustomizedJsonMapper 初始化时可能会
  // 创建此 Codec 实例从而造成递归引用
  private final JsonMapper mapper = new JsonMapper();

  @Override
  public StringList decode(final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (text == null) {
      return null;
    }
    if (text.isEmpty() || "[]".equals(text)) {
      return new StringList();
    }
    try {
      return mapper.readValue(text, StringList.class);
    } catch (final JsonProcessingException e) {
      throw new DecodingException("Invalid string list JSON format: " + text, e);
    }
  }

  @Override
  public String encode(final StringList list) throws EncodingException {
    if (list == null) {
      return null;
    } else if (list.isEmpty()) {
      return "[]";
    } else {
      try {
        return mapper.writeValueAsString(list);
      } catch (final JsonProcessingException e) {
        throw new EncodingException("Failed to encode the string list: " + list, e);
      }
    }
  }
}
