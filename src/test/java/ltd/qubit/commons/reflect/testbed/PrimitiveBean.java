////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

/**
 * Bean that has primitive properties.
 *
 * @author Haixing Hu
 */
public class PrimitiveBean {

  private boolean booleanProperty;
  private char charProperty;
  private byte byteProperty;
  private short shortProperty;
  private int intProperty;
  private long longProperty;
  private float floatProperty;
  private double doubleProperty;

  public boolean isBooleanProperty() {
    return booleanProperty;
  }

  public void setBooleanProperty(final boolean booleanProperty) {
    this.booleanProperty = booleanProperty;
  }

  public char getCharProperty() {
    return charProperty;
  }

  public void setCharProperty(final char charProperty) {
    this.charProperty = charProperty;
  }

  public byte getByteProperty() {
    return byteProperty;
  }

  public void setByteProperty(final byte byteProperty) {
    this.byteProperty = byteProperty;
  }

  public short getShortProperty() {
    return shortProperty;
  }

  public void setShortProperty(final short shortProperty) {
    this.shortProperty = shortProperty;
  }

  public int getIntProperty() {
    return intProperty;
  }

  public void setIntProperty(final int intProperty) {
    this.intProperty = intProperty;
  }

  public long getLongProperty() {
    return longProperty;
  }

  public void setLongProperty(final long longProperty) {
    this.longProperty = longProperty;
  }

  public float getFloatProperty() {
    return floatProperty;
  }

  public void setFloatProperty(final float floatProperty) {
    this.floatProperty = floatProperty;
  }

  public double getDoubleProperty() {
    return doubleProperty;
  }

  public void setDoubleProperty(final double doubleProperty) {
    this.doubleProperty = doubleProperty;
  }
}
