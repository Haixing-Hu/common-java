////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of ResourceUtils class.
 *
 * @author Haixing Hu
 */
public class ResourceUtilsTest {

  static final String BUNDLE_NAME = "ltd.qubit.commons.util.TestBundle";

  @Test
  public void testOverridden() {
    assertEquals("The class name \"ResourceHelper\" is invalid.",
        ResourceUtils.getMessage(BUNDLE_NAME, "ClassValidator.bad.classname",
            "ResourceHelper"), "wrong message");
  }

  @Test
  public void testNewMessage1Param() {
    assertEquals("Some might say we will find a brighter day.",
        ResourceUtils.getMessage(BUNDLE_NAME, "test.message"), "wrong message");
  }

  @Test
  public void testNewMessage2Params() {
    assertEquals("Some might say we will find a brighter day.",
        ResourceUtils.getMessage(BUNDLE_NAME, "test.message", "Some"), "wrong message");
  }

  @Test
  public void testNewMessage3Params() {
    assertEquals(
        "Some might say we will find a brighter day.",
        ResourceUtils.getMessage(BUNDLE_NAME, "test.message", "Some", "might"), "wrong message");
  }

  @Test
  public void testNewMessage4Params() {
    assertEquals("Some might say we will find a brighter day.",
        ResourceUtils.getMessage(BUNDLE_NAME, "test.message", "Some",
            "might", "say"), "wrong message");
  }

  @Test
  public void testDefaultBundle() {
    assertEquals("The class name \"ResourceHelper\" is invalid.",
        ResourceUtils.getMessage(BUNDLE_NAME, "ClassValidator.bad.classname",
            "ResourceHelper"), "wrong message");
  }
}
