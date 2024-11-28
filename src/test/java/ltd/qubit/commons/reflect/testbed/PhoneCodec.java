////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import ltd.qubit.commons.text.Stripper;
import ltd.qubit.commons.util.codec.Codec;
import ltd.qubit.commons.util.codec.DecodingException;

import static ltd.qubit.commons.lang.StringUtils.isEmpty;
import static ltd.qubit.commons.reflect.testbed.Phone.COUNTRY_CODE_PREFIX;
import static ltd.qubit.commons.reflect.testbed.Phone.PART_SEPARATOR;

public class PhoneCodec implements Codec<Phone, String> {

  @Override
  public Phone decode(final String source) throws DecodingException {
    final String theSource = new Stripper().strip(source);
    if (isEmpty(theSource)) {
      return null;
    }
    final String[] parts = theSource.split(PART_SEPARATOR);
    switch (parts.length) {
      case 1:
        return new Phone()
                .setNumber(theSource);
      case 2:
        if (parts[0].startsWith(COUNTRY_CODE_PREFIX)) {
          return new Phone()
                  .setCountryArea(parts[0].substring(COUNTRY_CODE_PREFIX.length()))
                  .setNumber(parts[1]);
        } else {
          return new Phone()
                  .setCityArea(parts[0])
                  .setNumber(parts[1]);
        }
      case 3:
        if (parts[0].startsWith(COUNTRY_CODE_PREFIX)) {
          return new Phone()
                  .setCountryArea(parts[0].substring(COUNTRY_CODE_PREFIX.length()))
                  .setCityArea(parts[1])
                  .setNumber(parts[2]);
        } else {
          throw new DecodingException("Invalid phone number format: " + theSource);
        }
      default:
        throw new DecodingException("Invalid phone number format: " + theSource);
    }
  }

  public Phone decodeWithoutThrow(final String source) {
    try {
      return decode(source);
    } catch (final DecodingException e) {
      return null;
    }
  }

  @Override
  public String encode(final Phone phone) {
    if (phone == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    if (! isEmpty(phone.getCountryArea())) {
      builder.append(COUNTRY_CODE_PREFIX)
             .append(phone.getCountryArea())
             .append(PART_SEPARATOR);
    }
    if (! isEmpty(phone.getCityArea())) {
      builder.append(phone.getCityArea())
             .append(PART_SEPARATOR);
    }
    builder.append(phone.getNumber());
    return builder.toString();
  }
}
