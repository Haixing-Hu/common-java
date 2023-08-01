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
 * Bean that has a private constructor that exposes properties via various
 * mechanisms (based on property name):
 *
 * <ul>
 * <li><strong>foo</strong> - Via direct public method
 * <li><strong>bar</strong> - Via directly implemented interface
 * <li><strong>baz</strong> - Via indirectly implemented interface
 * </ul>
 *
 * @author Haixing Hu
 */
class PrivateBean implements PrivateDirect {

  /**
   * Package private constructor - can only use factory method to create beans.
   */
  PrivateBean() {
  }

  /**
   * A directly implemented property.
   */
  private final String foo = "This is foo";

  public String getFoo() {
    return (this.foo);
  }

  /**
   * A property accessible via a directly implemented interface.
   */
  private final String bar = "This is bar";

  @Override
  public String getBar() {
    return this.bar;
  }

  /**
   * A method accessible via a directly implemented interface.
   */
  @Override
  public String methodBar(final String in) {
    return in;
  }

  /**
   * A property accessible via an indirectly implemented interface.
   */
  private final String baz = "This is baz";

  @Override
  public String getBaz() {
    return this.baz;
  }

  /**
   * A method accessible via an indirectly implemented interface.
   */
  @Override
  public String methodBaz(final String in) {
    return in;
  }

}
