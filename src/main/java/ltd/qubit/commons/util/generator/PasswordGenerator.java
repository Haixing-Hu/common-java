////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

/**
 * The interface of generators generates the passwords.
 *
 * @author Haixing Hu
 */
public interface PasswordGenerator {

  /**
   * Generates a passwords for username and a phone number.
   *
   * @param username
   *     the username.
   * @param phone
   *     the phone number.
   * @return the generated password.
   */
  String generate(String username, String phone);

}