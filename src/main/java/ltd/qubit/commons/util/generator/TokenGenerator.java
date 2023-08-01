////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

import javax.annotation.Nullable;

/**
 * The interface of generators which generates a token.
 *
 * @author Haixing Hu
 */
public interface TokenGenerator {

  /**
   * Generate a token.
   *
   * @param key
   *     a key used to generate the token, which could be {@code null}.
   * @return the random token generated.
   */
  String generate(@Nullable String key);

  /**
   * Tests whether a token is a valid token generated by this generator.
   *
   * @param token
   *     the token to be test.
   * @return {@code true} if the token is a valid token generated by this
   *     generator; {@code false} otherwise.
   */
  boolean isValid(String token);
}
