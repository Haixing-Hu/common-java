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
 * 此接口表示实体类具有出生日期属性。
 *
 * @author 胡海星
 */
public interface WithBirthday {

  /**
   * 获取此对象的出生日期。
   *
   * @return
   *     此对象的出生日期，若为{@code null}则未设置出生日期。
   */
  @Nullable
  LocalDate getBirthday();

  /**
   * 设置此对象的出生日期。
   *
   * @param birthday
   *     此对象的新的出生日期，若为{@code null}则清空原来的出生日期。
   */
  void setBirthday(@Nullable final LocalDate birthday);

  /**
   * 获取此人的年龄。
   *
   * @param until
   *     计算到此日期为止（但不包括此日期）的年龄。
   * @return
   *     计算出的年龄。
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
   * 判断此人是否是成年人。
   *
   * @param ageUntil
   *     计算到此日期为止（但不包括此日期）的年龄计算到此日期为止（但不包括此日期）的年龄。
   * @param adultAge
   *     成年人的年龄（包含此年龄）。
   * @return
   *     若此人是成年人，返回{@code true}；否则返回{@code false}。
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
