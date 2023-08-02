////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

/**
 * A public base class.
 *
 * @author Haixing Hu
 */
public class PublicBase implements Interface {

  public static final String VALUE2 = "PublicBase.VALUE2";

  protected static String value3 = "PublicBase.value3";

  @SuppressWarnings("unused")
  private static final String VALUE4 = "PublicBase.VALUE4";

  public String field1;

  private String field2;

  private boolean field3;

  protected String field4;

  String field5;

  public PublicBase() {
    System.out.println("PublicBase()");
    field1 = "PublicBase.field1";
    field2 = "PublicBase.field2";
    field3 = true;
    field4 = "PublicBase.field4";
    field5 = "PublicBase.field5";
  }

  public String getField1() {
    return field1;
  }

  public void setField1(final String field1) {
    this.field1 = field1;
  }

  public String getField2() {
    return field2;
  }

  public void setField2(final String field2) {
    this.field2 = field2;
  }

  public boolean isField3() {
    return field3;
  }

  public void setField3(final boolean field3) {
    this.field3 = field3;
  }

  public String getField4() {
    return field4;
  }

  public void setField4(final String field4) {
    this.field4 = field4;
  }

  public String getField5() {
    return field5;
  }

  public void setField5(final String field5) {
    this.field5 = field5;
  }
}
