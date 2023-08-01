////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.converter;

import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.IsoInstantCodec;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@CustomizedConverter
public class IsoInstantParser implements Converter<String, Instant> {

  private final IsoInstantCodec codec = new IsoInstantCodec();

  @Override
  public Instant convert(final String s) {
    try {
      return codec.decode(s);
    } catch (final DecodingException e) {
      throw new RuntimeException(e);
    }
  }
}
