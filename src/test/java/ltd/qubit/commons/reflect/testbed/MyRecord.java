////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2024 - 2024.
//    Nanjing Xinglin Digital Technology Co., Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

public record MyRecord(String name, int age) {
  //  empty

  public String getClassName() {
    return "MyRecord";
  }
}
