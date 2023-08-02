////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.converter;

import java.time.Instant;

import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.IsoInstantCodec;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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
