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
 * Default implementation of {@link UsernameGenerator}.
 *
 * @author Haixing Hu
 */
public class DefaultUsernameGenerator implements UsernameGenerator {

  public static final String PREFIX = "user-";

  @Override
  public String generate(final String mobile) {
    return PREFIX + mobile;
  }
}