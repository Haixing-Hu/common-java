////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.keydeserializer;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import ltd.qubit.commons.text.Stripper;

/**
 * The deserializer of String class for XML serializations.
 *
 * @author Haixing Hu
 */
@Immutable
public class StripStringKeyDeserializer extends KeyDeserializer implements Serializable {

  @Serial
  private static final long serialVersionUID = -2775703888645453983L;

  public static final StripStringKeyDeserializer INSTANCE = new StripStringKeyDeserializer();

  public StripStringKeyDeserializer() {}

  @Override
  public Object deserializeKey(final String key, final DeserializationContext ctxt) throws IOException {
    return new Stripper().strip(key);
  }
}