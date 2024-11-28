////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

public class AbstractChild implements Child {

  private String name;

  protected void setName(final String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

}
