////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperConfig;

/**
 * Enumeration of operations of serialization/deserialization.
 *
 * @author Haixing Hu
 */
public enum Operation {

  SERIALIZE,

  DESERIALIZE;

  public MapperConfig<?> getConfig(final ObjectMapper mapper) {
    switch (this) {
      case SERIALIZE:
        return mapper.getSerializationConfig();
      case DESERIALIZE:
        return mapper.getDeserializationConfig();
      default:
        throw new IllegalArgumentException("Unsupported operation: " + name());
    }
  }
}
