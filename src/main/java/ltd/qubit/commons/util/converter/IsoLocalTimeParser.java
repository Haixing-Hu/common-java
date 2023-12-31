////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.converter;

import java.time.LocalTime;

import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.IsoLocalTimeCodec;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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
