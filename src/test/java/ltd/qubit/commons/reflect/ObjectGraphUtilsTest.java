////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.reflect.testbed.App;
import ltd.qubit.commons.reflect.testbed.City;
import ltd.qubit.commons.reflect.testbed.Country;
import ltd.qubit.commons.reflect.testbed.Info;
import ltd.qubit.commons.reflect.testbed.MyRecord;
import ltd.qubit.commons.reflect.testbed.Province;
import ltd.qubit.commons.reflect.testbed.State;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyPath;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyType;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.getPropertyValue;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.hasProperty;
import static ltd.qubit.commons.reflect.ObjectGraphUtils.setPropertyValue;

public class ObjectGraphUtilsTest {

  @Test
  public void testHasProperty() throws Exception {
    assertTrue(hasProperty(City.class, "name"));
    assertTrue(hasProperty(City.class, "province"));
    assertTrue(hasProperty(City.class, "province.id"));
    assertTrue(hasProperty(City.class, "province.code"));
    assertFalse(hasProperty(City.class, "xx.code"));
    assertFalse(hasProperty(City.class, "province.xx"));
    assertFalse(hasProperty(City.class, "province.xx"));
    // 应支持计算属性
    assertTrue(hasProperty(City.class, "info"));
    assertTrue(hasProperty(City.class, "info.code"));
  }

  @Test
  public void testGetPropertyValue() throws Exception {
    final Country country = new Country();
    country.setId(10001L);
    country.setCode("CN");
    country.setName("China");
    country.setDescription("The People's Republic of China");
    final Province province = new Province();
    province.setId(10002L);
    province.setCode("JS");
    province.setName("Jiangsu");
    province.setCountry(country.getInfo());
    province.setDescription("Chinese Jiangsu province");

    assertEquals("JS", getPropertyValue(province, "code"));
    assertNull(getPropertyValue(province, "url"));
    assertEquals("CN", getPropertyValue(province, "country.code"));
    try {
      getPropertyValue(province, "country.description");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(0, e.getOptions());
      assertEquals("country.description", e.getFieldName());
    }
    try {
      getPropertyValue(province, "country.code.xx");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(0, e.getOptions());
      assertEquals("country.code.xx", e.getFieldName());
    }
    try {
      getPropertyValue(province, "country.xx.yy");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(0, e.getOptions());
      assertEquals("country.xx", e.getFieldName());
    }

    assertNull(getPropertyValue(null, "code"));

    // 应支持计算属性
    assertEquals(province.getInfo(), getPropertyValue(province, "info"));
    assertEquals(province.getCode(), getPropertyValue(province, "info.code"));
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
      assertEquals(0, e.getOptions());
      assertEquals("country.description", e.getFieldName());
    }
    try {
      getPropertyType(Province.class, "country.code.xx");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(0, e.getOptions());
      assertEquals("country.code.xx", e.getFieldName());
    }
    try {
      getPropertyType(Province.class, "country.xx.yy");
      fail("Should throw FieldNotExistException");
    } catch (final FieldNotExistException e) {
      assertEquals(Province.class, e.getOwnerClass());
      assertEquals(0, e.getOptions());
      assertEquals("country.xx", e.getFieldName());
    }
    // 应支持计算属性
    assertEquals(Info.class, getPropertyType(Province.class, "info"));
    assertEquals(Long.class, getPropertyType(Province.class, "info.id"));
    assertEquals(String.class, getPropertyType(Province.class, "info.code"));
  }

  @Test
  public void testSetPropertyValue() throws Exception {
    final Country country = new Country();
    country.setId(10001L);
    country.setCode("CN");
    country.setName("China");
    country.setDescription("The People's Republic of China");
    final Province province = new Province();
    province.setId(10002L);
    province.setCode("JS");
    province.setName("Jiangsu");
    province.setCountry(country.getInfo());
    province.setDescription("Chinese Jiangsu province");

    assertEquals("JS", province.getCode());
    setPropertyValue(province, "code", "XX");
    assertEquals("XX", province.getCode());

    assertNull(province.getUrl());
    setPropertyValue(province, "url", "https://www.google.com");
    assertEquals("https://www.google.com", province.getUrl());

    assertEquals("CN", province.getCountry().getCode());
    setPropertyValue(province, "country.code", "XX");
    assertEquals("XX", province.getCountry().getCode());
    setPropertyValue(province, "country.code", null);
    assertNull(province.getCountry().getCode());
    setPropertyValue(province, "country.code", "CN");
    assertEquals("CN", province.getCountry().getCode());

    final FieldNotExistException e1 = assertThrows(FieldNotExistException.class,
        () -> setPropertyValue(province, "country.description", "xx"));
    assertEquals(Province.class, e1.getOwnerClass());
    assertEquals("country.description", e1.getFieldName());

    final FieldNotExistException e2 = assertThrows(FieldNotExistException.class,
        () -> setPropertyValue(province, "country.code.xx", "xx"));
    assertEquals(Province.class, e2.getOwnerClass());
    assertEquals("country.code.xx", e2.getFieldName());

    final FieldNotExistException e3 = assertThrows(FieldNotExistException.class,
        () -> setPropertyValue(province, "country.xx.yy", "xx"));
    assertEquals(Province.class, e3.getOwnerClass());
    assertEquals("country.xx", e3.getFieldName());

    setPropertyValue(province, "country.code", null);
    assertNull(province.getCountry().getCode());

    final NullPointerException e4 = assertThrows(NullPointerException.class,
        () -> setPropertyValue(province, "country.code.xx", "xx"));
    assertEquals("The country.code of the specified object is null.", e4.getMessage());

    // 不支持计算属性
    final ReflectionException e5 = assertThrows(ReflectionException.class,
        () -> setPropertyValue(province, "info", "xx"));
    assertTrue(e5.getMessage().startsWith("Cannot write a read-only or computed property"));

    final ReflectionException e6 = assertThrows(ReflectionException.class,
        () -> setPropertyValue(province, "info.code", "xx"));
    assertTrue(e5.getMessage().startsWith("Cannot write a read-only or computed property"));
  }

  @Test
  public void testSetPropertyValueWithCreateIntermediateFalse() {
    final Country country = new Country();
    country.setId(10001L);
    country.setCode("CN");
    country.setName("China");
    country.setDescription("The People's Republic of China");
    final Province province = new Province();
    province.setId(10002L);
    province.setCode("JS");
    province.setName("Jiangsu");
    province.setCountry(country.getInfo());
    province.setDescription("Chinese Jiangsu province");

    assertEquals("JS", province.getCode());
    setPropertyValue(province, "code", "XX", false);
    assertEquals("XX", province.getCode());

    assertNull(province.getUrl());
    setPropertyValue(province, "url", "https://www.google.com", false);
    assertEquals("https://www.google.com", province.getUrl());

    assertEquals("CN", province.getCountry().getCode());
    setPropertyValue(province, "country.code", "XX", false);
    assertEquals("XX", province.getCountry().getCode());
    setPropertyValue(province, "country.code", null, false);
    assertNull(province.getCountry().getCode());
    setPropertyValue(province, "country.code", "CN", false);
    assertEquals("CN", province.getCountry().getCode());

    final FieldNotExistException e1 = assertThrows(FieldNotExistException.class,
        () -> setPropertyValue(province, "country.description", "xx", false));
    assertEquals(Province.class, e1.getOwnerClass());
    assertEquals("country.description", e1.getFieldName());

    final FieldNotExistException e2 = assertThrows(FieldNotExistException.class,
        () -> setPropertyValue(province, "country.code.xx", "xx", false));
    assertEquals(Province.class, e2.getOwnerClass());
    assertEquals("country.code.xx", e2.getFieldName());

    final FieldNotExistException e3 = assertThrows(FieldNotExistException.class,
        () -> setPropertyValue(province, "country.xx.yy", "xx", false));
    assertEquals(Province.class, e3.getOwnerClass());
    assertEquals("country.xx", e3.getFieldName());

    setPropertyValue(province, "country.code", null, false);
    assertNull(province.getCountry().getCode());

    final NullPointerException e4 = assertThrows(NullPointerException.class,
        () -> setPropertyValue(province, "country.code.xx", "xx", false));
    assertEquals("The country.code of the specified object is null.", e4.getMessage());

    province.setCountry(null);
    final NullPointerException e5 = assertThrows(NullPointerException.class,
        () -> setPropertyValue(province, "country.code", "CN", false));
    assertEquals("The country.code of the specified object is null.", e4.getMessage());
  }

  @Test
  public void testSetPropertyValueWithCreateIntermediateTrue() {
    final Country country = new Country();
    country.setId(10001L);
    country.setCode("CN");
    country.setName("China");
    country.setDescription("The People's Republic of China");
    final Province province = new Province();
    province.setId(10002L);
    province.setCode("JS");
    province.setName("Jiangsu");
    province.setCountry(country.getInfo());
    province.setDescription("Chinese Jiangsu province");

    assertEquals("JS", province.getCode());
    setPropertyValue(province, "code", "XX", true);
    assertEquals("XX", province.getCode());

    assertNull(province.getUrl());
    setPropertyValue(province, "url", "https://www.google.com", true);
    assertEquals("https://www.google.com", province.getUrl());

    assertEquals("CN", province.getCountry().getCode());
    setPropertyValue(province, "country.code", "XX", true);
    assertEquals("XX", province.getCountry().getCode());
    setPropertyValue(province, "country.code", null, true);
    assertNull(province.getCountry().getCode());
    setPropertyValue(province, "country.code", "CN", true);
    assertEquals("CN", province.getCountry().getCode());

    final FieldNotExistException e1 = assertThrows(FieldNotExistException.class,
        () -> setPropertyValue(province, "country.description", "xx", true));
    assertEquals(Province.class, e1.getOwnerClass());
    assertEquals("country.description", e1.getFieldName());

    final FieldNotExistException e2 = assertThrows(FieldNotExistException.class,
        () -> setPropertyValue(province, "country.code.xx", "xx", true));
    assertEquals(Province.class, e2.getOwnerClass());
    assertEquals("country.code.xx", e2.getFieldName());

    final FieldNotExistException e3 = assertThrows(FieldNotExistException.class,
        () -> setPropertyValue(province, "country.xx.yy", "xx", true));
    assertEquals(Province.class, e3.getOwnerClass());
    assertEquals("country.xx", e3.getFieldName());

    setPropertyValue(province, "country.code", null, false);
    assertNull(province.getCountry().getCode());

    final FieldNotExistException e4 = assertThrows(FieldNotExistException.class,
        () -> setPropertyValue(province, "country.code.xx", "xx", true));
    assertEquals(Province.class, e4.getOwnerClass());
    assertEquals("country.code.xx", e4.getFieldName());

    province.setCountry(null);
    setPropertyValue(province, "country.code", "CN", true);
    assertNotNull(province.getCountry());
    assertEquals("CN", province.getCountry().getCode());
    assertNull(province.getCountry().getId());
    assertNull(province.getCountry().getName());
  }

  @Test
  public void getPropertyPathOfRecord() throws NoSuchMethodException {
    final String path = getPropertyPath(MyRecord.class, MyRecord::name);
    assertEquals("name", path);
  }

  @Test
  public void getPropertyPathOfEnumClass() {
    final String path = getPropertyPath(State.class, State::getLocalizedName);
    assertEquals("localizedName", path);
  }

  @Test
  public void getPropertyPathOfEnumClass2() {
    final String path = getPropertyPath(App.class, App::getState, State::getLocalizedName);
    assertEquals("state.localizedName", path);
  }

  @Test
  public void testHasPropertyForEnumClass() {
    final boolean result = hasProperty(App.class, "state.localizedName");
    assertTrue(result);
  }

  @Test
  public void testHasPropertyForEnumClass1() {
    final boolean result = hasProperty(App.class, "state.localizedName.length");
    assertTrue(result);
  }

  @Test
  public void testGetPropertyValueForEnumClass1() {
    final App app = new App();
    app.setState(State.NORMAL);
    final Object v1 = getPropertyValue(app, "state.localizedName");
    assertEquals("正常", v1);
    final Object v2 = getPropertyValue(app, "state.localizedName.length");
    assertEquals(2, v2);
  }
}