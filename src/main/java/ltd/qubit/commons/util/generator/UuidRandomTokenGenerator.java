////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

import ltd.qubit.commons.util.UuidUtils;

import java.util.UUID;

/**
 * An implementation of the {@link TokenGenerator} using the UUID.
 *
 * @author Haixing Hu
 */
public final class UuidRandomTokenGenerator implements TokenGenerator {

  @Override
  public String generate(final String key) {
    // return UUID.randomUUID().toString();
    return UuidUtils.getUuid();   //  use the faster algorithm
  }

  @Override
  public boolean isValid(final String token) {
    try {
      UUID.fromString(token);
      return true;
    } catch (final Exception e) {
      return false;
    }
  }
}
