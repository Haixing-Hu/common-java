////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.util.pair.KeyValuePairList;

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
