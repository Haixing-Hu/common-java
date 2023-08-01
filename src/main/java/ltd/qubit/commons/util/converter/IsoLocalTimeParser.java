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
import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@CustomizedConverter
public class IsoLocalTimeParser implements Converter<String, LocalTime> {

  private final IsoLocalTimeCodec codec = new IsoLocalTimeCodec();

  @Override
  public LocalTime convert(final String s) {
    try {
      return codec.decode(s);
    } catch (final DecodingException e) {
      throw new RuntimeException(e);
    }
  }
}
