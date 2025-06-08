////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

/**
 * A public derived class.
 *
 * @author Haixing Hu
 */
public class PublicDerived extends PublicBase {

  public static final String VALUE5 = "PublicDerived.VALUE5";

  protected static String value6 = "PublicDerived.value6";

  @SuppressWarnings("unused")
  private static final String VALUE7 = "PublicDerived.VALUE7";

  public String field6;

  private String field7;

  private boolean field8;

  protected String field9;

  String field10;

  private String field1;

  public PublicDerived() {
    System.out.println("PublicDerived()");
    field6 = "PublicDerived.field6";
    field7 = "PublicDerived.field7";
    field8 = true;
    field9 = "PublicDerived.field9";
    field10 = "PublicDerived.field10";
    field1 = "PublicDerived.field1";
  }

  public String getField6() {
    return field6;
  }

  public void setField6(final String field6) {
    this.field6 = field6;
  }

  public String getField7() {
    return field7;
  }

  public void setField7(final String field7) {
    this.field7 = field7;
  }

  public boolean isField8() {
    return field8;
  }

  public void setField8(final boolean field8) {
    this.field8 = field8;
  }

  public String getField9() {
    return field9;
  }

  public void setField9(final String field9) {
    this.field9 = field9;
  }

  public String getField10() {
    return field10;
  }

  public void setField10(final String field10) {
    this.field10 = field10;
  }

  @Override
  public String getField1() {
    return field1;
  }

  @Override
  public void setField1(final String field1) {
    this.field1 = field1;
  }
}