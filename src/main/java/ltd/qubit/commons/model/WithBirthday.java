////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.time.LocalDate;
import java.time.Period;

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Computed;

/**
 * This interface represents an entity class with a date of birth property.
 *
 * @author Haixing Hu
 */
public interface WithBirthday {

  /**
   * Gets this person&#39;s date of birth.
   *
   * @return
   *     The date of birth of this person; or {@code null} if the date of birth
   *     is not set.
   */
  @Nullable
  LocalDate getBirthday();

  /**
   * Sets the date of birth for this person.
   *
   * @param birthday
   *     The new date of birth of this person; or {@code null} to clear the
   *     original date of birth.
   */
  void setBirthday(@Nullable final LocalDate birthday);

  /**
   * Gets the person&#39;s age up to (but not including) today.
   *
   * @return
   *     Calculated age.
   */
  @Computed("birthday")
  default Period getAge() {
    return getAge(LocalDate.now());
  }

  /**
   * Gets the person&#39;s age.
   *
   * @param until
   *     Calculates the age up to (but not including) this date.
   * @return
   *     Calculated age.
   */
  @Computed("birthday")
  default Period getAge(final LocalDate until) {
    final LocalDate birthday = getBirthday();
    if (birthday == null) {
      return null;
    } else {
      return Period.between(birthday, until);
    }
  }

  /**
   * Determine if the person is an adult.
   *
   * @param ageUntil
   *     Calculate age up to (but not including) this date.
   * @param adultAge
   *     Age of adult, inclusive.
   * @return
   *     Returns {@code true} if the person is an adult; otherwise returns
   *     {@code false}.
   */
  @Computed("birthday")
  default Boolean isAdult(final LocalDate ageUntil, final int adultAge) {
    final Period age = getAge(ageUntil);
    if (age == null) {
      return null;
    } else {
      return (age.getYears() >= adultAge);
    }
  }
}
