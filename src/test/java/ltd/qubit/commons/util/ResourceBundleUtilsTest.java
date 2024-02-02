////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.util.bundle.ResourceBundleUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of ResourceUtils class.
 *
 * @author Haixing Hu
 */
public class ResourceBundleUtilsTest {

  static final String BUNDLE_NAME = "ltd.qubit.commons.util.TestBundle";

  @Test
  public void testOverridden() {
    assertEquals("The class name \"ResourceHelper\" is invalid.",
        ResourceBundleUtils.getMessage(BUNDLE_NAME, "ClassValidator.bad.classname",
            "ResourceHelper"), "wrong message");
  }

  @Test
  public void testNewMessage1Param() {
    assertEquals("Some might say we will find a brighter day.",
        ResourceBundleUtils.getMessage(BUNDLE_NAME, "test.message"), "wrong message");
  }

  @Test
  public void testNewMessage2Params() {
    assertEquals("Some might say we will find a brighter day.",
        ResourceBundleUtils.getMessage(BUNDLE_NAME, "test.message", "Some"), "wrong message");
  }

  @Test
  public void testNewMessage3Params() {
    assertEquals(
        "Some might say we will find a brighter day.",
        ResourceBundleUtils.getMessage(BUNDLE_NAME, "test.message", "Some", "might"), "wrong message");
  }

  @Test
  public void testNewMessage4Params() {
    assertEquals("Some might say we will find a brighter day.",
        ResourceBundleUtils.getMessage(BUNDLE_NAME, "test.message", "Some",
            "might", "say"), "wrong message");
  }

  @Test
  public void testDefaultBundle() {
    assertEquals("The class name \"ResourceHelper\" is invalid.",
        ResourceBundleUtils.getMessage(BUNDLE_NAME, "ClassValidator.bad.classname",
            "ResourceHelper"), "wrong message");
  }
}
