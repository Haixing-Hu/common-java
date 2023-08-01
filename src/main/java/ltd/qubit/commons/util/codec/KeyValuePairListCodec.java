////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.util.pair.KeyValuePairList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;

public class KeyValuePairListCodec implements Codec<KeyValuePairList, String> {

  private final JsonMapper mapper = new JsonMapper();

  @Override
  public KeyValuePairList decode(final String str) throws DecodingException {
    final String text = new Stripper().strip(str);
    if (text == null || text.isEmpty()) {
      return null;
    }
    try {
      return mapper.readValue(text, KeyValuePairList.class);
    } catch (final IOException e) {
      throw new DecodingException(e);
    }
  }

  @Override
  public String encode(final KeyValuePairList source) throws EncodingException {
    try {
      return mapper.writeValueAsString(source);
    } catch (final JsonProcessingException e) {
      throw new EncodingException(e);
    }
  }
}
