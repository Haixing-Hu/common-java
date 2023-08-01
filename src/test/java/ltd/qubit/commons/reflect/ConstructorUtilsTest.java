////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import ltd.qubit.commons.reflect.testbed.NewInstanceBean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstructorUtilsTest {

  @Test
  public void testNewInstance() {
    final NewInstanceBean bean = ConstructorUtils.newInstance(NewInstanceBean.class);
    assertEquals("name", bean.getName());
    assertEquals("value", bean.getValue());
  }
}
