////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.testbed.model.CredentialType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnumUtilsTest {

  private enum TestEnum {
    VALUE_ONE, VALUE_TWO, VALUE_THREE
  }

  @Test
  void getShortName_shouldReturnCorrectShortName() {
    assertEquals("value-one", EnumUtils.getShortName(TestEnum.VALUE_ONE));
  }

  @Test
  void getFullName_shouldReturnCorrectFullName() {
    assertEquals("VALUE_ONE", EnumUtils.getFullName("value-one"));
  }

  @Test
  void forName_shouldReturnEnumValue_whenNameIsFullName() {
    assertEquals(TestEnum.VALUE_ONE, EnumUtils.forName("VALUE_ONE", false, false, TestEnum.class));
  }

  @Test
  void forName_shouldReturnEnumValue_whenNameIsShortName() {
    assertEquals(TestEnum.VALUE_ONE, EnumUtils.forName("value-one", true, false, TestEnum.class));
  }

  @Test
  void forName_shouldReturnEnumValueIgnoringCase() {
    assertEquals(TestEnum.VALUE_ONE, EnumUtils.forName("value_one", false, true, TestEnum.class));
  }

  @Test
  void forName_shouldReturnNull_whenNameDoesNotExist() {
    assertNull(EnumUtils.forName("NON_EXISTENT", false, false, TestEnum.class));
  }

  @Test
  void toString_shouldReturnShortName_whenUseShortNameIsTrue() {
    assertEquals("value-one", EnumUtils.toString(TestEnum.VALUE_ONE, true));
  }

  @Test
  void toString_shouldReturnFullName_whenUseShortNameIsFalse() {
    assertEquals("VALUE_ONE", EnumUtils.toString(TestEnum.VALUE_ONE, false));
  }

  @Test
  void next_shouldReturnNextEnumValue() {
    assertEquals(TestEnum.VALUE_TWO, EnumUtils.next(TestEnum.VALUE_ONE));
  }

  @Test
  void next_shouldReturnFirstEnumValue_whenCurrentIsLast() {
    assertEquals(TestEnum.VALUE_ONE, EnumUtils.next(TestEnum.VALUE_THREE));
  }

  @Test
  void isComparable_shouldReturnTrueForComparableTypes() {
    assertTrue(EnumUtils.isComparable(String.class));
    assertTrue(EnumUtils.isComparable(TestEnum.class));
  }

  @Test
  void isComparable_shouldReturnFalseForNonComparableTypes() {
    assertFalse(EnumUtils.isComparable(Object.class));
  }

  @Test
  void valueOf_shouldReturnEnumValue_whenNameExists() {
    assertEquals(TestEnum.VALUE_ONE, EnumUtils.valueOf(TestEnum.class, "VALUE_ONE"));
  }

  @Test
  void valueOf_shouldReturnNull_whenNameDoesNotExist() {
    assertNull(EnumUtils.valueOf(TestEnum.class, "NON_EXISTENT"));
  }

  @Test
  void getEnumClass_shouldReturnEnumClass_whenClassNameIsValid() {
    assertEquals(CredentialType.class, EnumUtils.getEnumClass("ltd.qubit.commons.testbed.model.CredentialType"));
  }

  @Test
  void getEnumClass_shouldReturnNull_whenClassNameIsInvalid() {
    assertNull(EnumUtils.getEnumClass("InvalidClassName"));
  }

  @Test
  void registerLocalizedNames_shouldThrowException_whenBasenameIsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> EnumUtils.registerLocalizedNames(TestEnum.class, "invalid/basename"));
  }

  @Test
  void getLocalizedName_shouldReturnLocalizedName_whenRegistered() {
    EnumUtils.registerLocalizedNames(TestEnum.class, "ltd.qubit.commons.lang.EnumUtils$TestEnum");
    assertEquals("VALUE_ONE", EnumUtils.getLocalizedName(Locale.ENGLISH, TestEnum.VALUE_ONE));
  }

  @Test
  void forLocalizedName_shouldReturnEnumValue_whenLocalizedNameExists() {
    EnumUtils.registerLocalizedNames(TestEnum.class, "ltd.qubit.commons.lang.EnumUtils$TestEnum");
    assertEquals(TestEnum.VALUE_ONE, EnumUtils.forLocalizedName(TestEnum.class, Locale.ENGLISH, "VALUE_ONE"));
  }

  @Test
  void forLocalizedName_shouldReturnNull_whenLocalizedNameDoesNotExist() {
    EnumUtils.registerLocalizedNames(TestEnum.class, "ltd.qubit.commons.lang.EnumUtils$TestEnum");
    assertNull(EnumUtils.forLocalizedName(TestEnum.class, Locale.ENGLISH, "NON_EXISTENT"));
  }
}