////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import javax.annotation.Nullable;

import ltd.qubit.commons.reflect.testbed.Foo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassUtilsTest {

  @Test
  public void testIsJdkBuiltin() {
    assertTrue(ClassUtils.isJdkBuiltIn(String.class));
    assertTrue(ClassUtils.isJdkBuiltIn(Nullable.class));
    assertFalse(ClassUtils.isJdkBuiltIn(Foo.class));
  }
}
