////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import ltd.qubit.commons.reflect.testbed.City;
import ltd.qubit.commons.reflect.testbed.Country;
import ltd.qubit.commons.reflect.testbed.Province;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyType;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyValue;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.hasProperty;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.setPropertyValue;
import static ltd.qubit.commons.reflect.Option.BEAN_FIELD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ObjectGraphUtilsTest {

  @Test
  public void testHasProperty() throws Exception {
    assertTrue(hasProperty(City.class, "name"));
    assertTrue(hasProperty(City.class, "province"));
    assertTrue(hasProperty(City.class, "province.id"));
    assertTrue(hasProperty(City.class, "province.code"));
    assertFalse(hasProperty(City.class, "xx.code"));
    assertFalse(hasProperty(City.class, "province.xx"));
  }

  @Test
  public void testGetPropertyValue() throws Exception {
    final Country country = new Country();
    country.setId(10001l);
    country.setCode("CN");
    country.setName("China");
    country.setDescription("The People's Republic of China");
    final Province province = new Province();
    province.setId(10002l);
    province.setCode("JS");
    province.setName("Jiangsu");
    province.setCountry(country.getInfo());
    province.setDescription("Chinese Jiangsu province");

    assertEquals("JS", getPropertyValue(province, "code"));
    assertEquals(null, getPropertyValue(province, "url"));
    assertEquals("CN", getPropertyValue(province, "country.code"));
    try {
      getPropertyValue(province, "country.description");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(BEAN_FIELD, e.getOptions());
      assertEquals("country.description", e.getFieldName());
    }
    try {
      getPropertyValue(province, "country.code.xx");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(BEAN_FIELD, e.getOptions());
      assertEquals("country.code.xx", e.getFieldName());
    }
    try {
      getPropertyValue(province, "country.xx.yy");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(BEAN_FIELD, e.getOptions());
      assertEquals("country.xx", e.getFieldName());
    }

    assertEquals(null, getPropertyValue(null, "code"));
  }

  @Test
  public void testGetPropertyType() throws Exception {
    assertEquals(String.class, getPropertyType(Province.class, "code"));
    assertEquals(String.class, getPropertyType(Province.class, "url"));
    assertEquals(String.class, getPropertyType(Province.class, "country.code"));
    assertEquals(Long.class, getPropertyType(Province.class, "country.id"));

    try {
      getPropertyType(Province.class, "country.description");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(BEAN_FIELD, e.getOptions());
      assertEquals("country.description", e.getFieldName());
    }
    try {
      getPropertyType(Province.class, "country.code.xx");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(BEAN_FIELD, e.getOptions());
      assertEquals("country.code.xx", e.getFieldName());
    }
    try {
      getPropertyType(Province.class, "country.xx.yy");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(BEAN_FIELD, e.getOptions());
      assertEquals("country.xx", e.getFieldName());
    }
  }

  @Test
  public void testSetPropertyValue() throws Exception {
    final Country country = new Country();
    country.setId(10001l);
    country.setCode("CN");
    country.setName("China");
    country.setDescription("The People's Republic of China");
    final Province province = new Province();
    province.setId(10002l);
    province.setCode("JS");
    province.setName("Jiangsu");
    province.setCountry(country.getInfo());
    province.setDescription("Chinese Jiangsu province");

    assertEquals("JS", province.getCode());
    setPropertyValue(province, "code", "XX");
    assertEquals("XX", province.getCode());

    assertEquals(null, province.getUrl());
    setPropertyValue(province, "url", "https://www.google.com");
    assertEquals("https://www.google.com", province.getUrl());

    assertEquals("CN", province.getCountry().getCode());
    setPropertyValue(province, "country.code", "XX");
    assertEquals("XX", province.getCountry().getCode());
    setPropertyValue(province, "country.code", null);
    assertEquals(null, province.getCountry().getCode());
    setPropertyValue(province, "country.code", "CN");
    assertEquals("CN", province.getCountry().getCode());

    try {
      setPropertyValue(province, "country.description", "xx");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(BEAN_FIELD, e.getOptions());
      assertEquals("country.description", e.getFieldName());
    }
    try {
      setPropertyValue(province, "country.code.xx", "xx");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(BEAN_FIELD, e.getOptions());
      assertEquals("country.code.xx", e.getFieldName());
    }
    try {
      setPropertyValue(province, "country.xx.yy", "xx");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(BEAN_FIELD, e.getOptions());
      assertEquals("country.xx", e.getFieldName());
    }

    setPropertyValue(province, "country.code", null);
    assertEquals(null, province.getCountry().getCode());
    try {
      setPropertyValue(province, "country.code.xx", "xx");
      fail("Should throw FieldNotExistException");
    } catch (final NullPointerException e) {
      assertEquals("The country.code of the specified object is null.", e.getMessage());
    }

  }
}
