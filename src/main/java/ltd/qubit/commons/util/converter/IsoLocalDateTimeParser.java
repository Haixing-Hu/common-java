////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.converter;

import java.time.LocalDateTime;

import ltd.qubit.commons.util.codec.DecodingException;
import ltd.qubit.commons.util.codec.LocalDateTimeCodec;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@CustomizedConverter
public class IsoLocalDateTimeParser implements Converter<String, LocalDateTime> {

  private final LocalDateTimeCodec codec = new LocalDateTimeCodec();

  @Override
  public LocalDateTime convert(final String s) {
    try {
      return codec.decode(s);
    } catch (final DecodingException e) {
      throw new RuntimeException(e);
    }
  }
}
