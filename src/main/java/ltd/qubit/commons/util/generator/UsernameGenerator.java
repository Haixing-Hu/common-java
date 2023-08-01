////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

/**
 * The interface of generators generate the usernames.
 *
 * @author Haixing Hu
 */
public interface UsernameGenerator {

  /**
   * Generates a username for a phone number.
   *
   * @param phone
   *     the phone number.
   * @return the generated username.
   */
  String generate(String phone);
}
