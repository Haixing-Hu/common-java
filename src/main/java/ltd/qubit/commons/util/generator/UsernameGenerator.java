////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

/**
 * The interface of generators generates the usernames.
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
