////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * An implementation of the {@link PasswordGenerator} which generates passwords
 * with the phone number of the user.
 *
 * @author Haixing Hu
 */
public class PhoneNumberPasswordGenerator implements PasswordGenerator {

  @Override
  public String generate(final String username, final String phone) {
    return phone;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final PhoneNumberPasswordGenerator other = (PhoneNumberPasswordGenerator) o;
    return true;
  }

  @Override
  public int hashCode() {
    return 3;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).toString();
  }
}
