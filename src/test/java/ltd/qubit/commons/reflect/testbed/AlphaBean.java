////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

public class AlphaBean extends AbstractParent implements Child {

  private String name;

  public AlphaBean() {
  }

  public AlphaBean(final String name) {
    setName(name);
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Used for testing that correct exception is thrown.
   */
  public void bogus(final String badParameter) {
  }
}
