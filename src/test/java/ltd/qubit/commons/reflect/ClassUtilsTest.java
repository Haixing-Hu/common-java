////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import ltd.qubit.commons.reflect.testbed.Foo;

import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

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
