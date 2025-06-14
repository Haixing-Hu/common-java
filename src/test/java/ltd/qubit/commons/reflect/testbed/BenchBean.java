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
 * Plain old java bean (POJO) for microbenchmarks.
 *
 * @author Haixing Hu
 */
public class BenchBean {
  //  stop checkstyle: MagicNumberCheck

  /**
   * A boolean property.
   */
  private boolean booleanProperty = true;

  public boolean getBooleanProperty() {
    return (booleanProperty);
  }

  public void setBooleanProperty(final boolean booleanProperty) {
    this.booleanProperty = booleanProperty;
  }

  /**
   * A byte property.
   */
  private byte byteProperty = (byte) 121;

  public byte getByteProperty() {
    return (this.byteProperty);
  }

  public void setByteProperty(final byte byteProperty) {
    this.byteProperty = byteProperty;
  }

  /**
   * A double property.
   */
  private double doubleProperty = 321.0;

  public double getDoubleProperty() {
    return (this.doubleProperty);
  }

  public void setDoubleProperty(final double doubleProperty) {
    this.doubleProperty = doubleProperty;
  }

  /**
   * A float property.
   */
  private float floatProperty = (float) 123.0;

  public float getFloatProperty() {
    return (this.floatProperty);
  }

  public void setFloatProperty(final float floatProperty) {
    this.floatProperty = floatProperty;
  }

  /**
   * An integer property.
   */
  private int intProperty = 123;

  public int getIntProperty() {
    return (this.intProperty);
  }

  public void setIntProperty(final int intProperty) {
    this.intProperty = intProperty;
  }

  /**
   * A long property.
   */
  private long longProperty = 321;

  public long getLongProperty() {
    return (this.longProperty);
  }

  public void setLongProperty(final long longProperty) {
    this.longProperty = longProperty;
  }

  /**
   * A short property.
   */
  private short shortProperty = (short) 987;

  public short getShortProperty() {
    return (this.shortProperty);
  }

  public void setShortProperty(final short shortProperty) {
    this.shortProperty = shortProperty;
  }

  /**
   * A String property.
   */
  private String stringProperty = "This is a string";

  public String getStringProperty() {
    return (this.stringProperty);
  }

  public void setStringProperty(final String stringProperty) {
    this.stringProperty = stringProperty;
  }
  //  resume checkstyle: MagicNumberCheck
}