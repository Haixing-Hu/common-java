////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.beans.PropertyDescriptor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ltd.qubit.commons.priv.PrivateBeanFactory;
import ltd.qubit.commons.priv.PrivateDirect;
import ltd.qubit.commons.reflect.testbed.App;
import ltd.qubit.commons.reflect.testbed.Bean;
import ltd.qubit.commons.reflect.testbed.BeanPublicSubclass;
import ltd.qubit.commons.reflect.testbed.State;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import static ltd.qubit.commons.reflect.MethodUtils.getMethodByName;
import static ltd.qubit.commons.reflect.PropertyUtils.getPropertyNameFromGetter;
import static ltd.qubit.commons.reflect.PropertyUtils.getPropertyNameFromSetter;

/**
 * Unit test of the {@link ltd.qubit.commons.reflect.PropertyUtils} class.
 *
 * @author Haixing Hu
 */
public class PropertyUtilTest {

  /**
   * The basic test bean for each test.
   */
  protected Bean bean = null;

  /**
   * The "package private subclass" test bean for each test.
   */
  protected BeanPackageSubclass beanPackageSubclass = null;

  /**
   * The test bean for private access tests.
   */
  protected PrivateDirect beanPrivate = null;

  /**
   * The test bean for private access tests of subclasses.
   */
  protected PrivateDirect beanPrivateSubclass = null;

  /**
   * The "public subclass" test bean for each test.
   */
  protected BeanPublicSubclass beanPublicSubclass = null;

  /**
   * Set up instance variables required by this test case.
   */
  @BeforeEach
  public void setUp() {
    bean = new Bean();
    beanPackageSubclass = new BeanPackageSubclass();
    beanPrivate = PrivateBeanFactory.create();
    beanPrivateSubclass = PrivateBeanFactory.createSubclass();
    beanPublicSubclass = new BeanPublicSubclass();
  }

  /**
   * Tear down instance variables required by this test case.
   */
  @AfterEach
  public void tearDown() {
    bean = null;
    beanPackageSubclass = null;
    beanPrivate = null;
    beanPrivateSubclass = null;
    beanPublicSubclass = null;
  }

  @Test
  public void testInvokeMethod() {
    // fail("Not yet implemented");
  }

  @Test
  public void testGetPropertyDescriptors() {

    try {
      PropertyUtils.getDescriptors(null);
      fail("Should throw IllegalArgumentException");
    } catch (final NullPointerException e) {
      // Expected response
    } catch (final Throwable t) {
      fail("Threw " + t + " instead of NullPointerException");
    }

    final PropertyDescriptor[] pd = PropertyUtils.getDescriptors(bean.getClass());
    assertNotNull(pd, "Got descriptors");

    final int[] count = new int[Bean.PROPERTIES.length];
    for (final PropertyDescriptor element : pd) {
      final String name = element.getName();
      boolean found = false;
      for (int j = 0; j < Bean.PROPERTIES.length; j++) {
        if (name.equals(Bean.PROPERTIES[j])) {
          ++count[j];
          found = true;
          break;
        }
      }
      if (!found) {
        fail("Undesired property '" + name + "'");
      }
    }

    for (int j = 0; j < Bean.PROPERTIES.length; j++) {
      if (count[j] <= 0) {
        fail("Missing property " + Bean.PROPERTIES[j]);
      } else if (count[j] > 1) {
        fail("Duplicate property " + Bean.PROPERTIES[j]);
      }
    }

    assertEquals(Bean.PROPERTIES.length, pd.length);
  }

  @Test
  public void testGetPropertyDescriptor() {
    // fail("Not yet implemented");
  }

  @Test
  public void testGetPropertyNameFromGetter() {
    assertThat(getPropertyNameFromGetter(getMethodByName(App.class, "getId")))
        .isEqualTo("id");
    assertThat(getPropertyNameFromGetter(getMethodByName(App.class, "getCode")))
        .isEqualTo("code");
    assertThat(getPropertyNameFromGetter(getMethodByName(App.class, "isPredefined")))
        .isEqualTo("predefined");
    assertThat(getPropertyNameFromGetter(getMethodByName(State.class, "name")))
        .isEqualTo("name");
  }

  @Test
  public void testGetPropertyNameFromSetter() {
    assertThat(getPropertyNameFromSetter(getMethodByName(App.class, "setId")))
        .isEqualTo("id");
    assertThat(getPropertyNameFromSetter(getMethodByName(App.class, "setCode")))
        .isEqualTo("code");
    assertThat(getPropertyNameFromSetter(getMethodByName(App.class, "setPredefined")))
        .isEqualTo("predefined");
  }
}
