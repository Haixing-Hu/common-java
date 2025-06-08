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

import ltd.qubit.commons.reflect.testbed.NewInstanceBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstructorUtilsTest {

  @Test
  public void testNewInstance() {
    final NewInstanceBean bean = ConstructorUtils.newInstance(NewInstanceBean.class);
    assertEquals("name", bean.getName());
    assertEquals("value", bean.getValue());
  }
}