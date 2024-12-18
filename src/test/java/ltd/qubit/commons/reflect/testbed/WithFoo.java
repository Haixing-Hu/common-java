////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2024 - 2024.
//    Nanjing Xinglin Digital Technology Co., Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

public interface WithFoo {

  default String foo() {
    return "foo";
  }

  default String foo(final String name) {
    return name;
  }
}
