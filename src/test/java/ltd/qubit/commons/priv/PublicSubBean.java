////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.priv;

/**
 * This class is designed to test the default access jvm problem workaround. The
 * issue is that public methods of a public subclass contained in a default
 * access superclass are returned by reflection but an IllegalAccessException is
 * thrown when they are invoked.
 *
 * <p>This is the default access superclass
 *
 * @author Haixing Hu
 */
public class PublicSubBean extends PackageBean {

  /**
   * Package private constructor - can only use factory method to create beans.
   */
  public PublicSubBean() {
  }

  /**
   * A directly implemented property.
   */
  private String foo = "This is foo";

  public String getFoo() {
    return this.foo;
  }

  public void setFoo(final String foo) {
    this.foo = foo;
  }
}
