////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * General purpose test bean for the unit test.
 *
 * @author Haixing Hu
 */
public class Bean implements Serializable {

  //  stop checkstyle: MagicNumberCheck

  private static final long serialVersionUID = - 6708163537454039977L;

  /*
   * Another nested reference to a bean containing mapp properties
   */
  public class MappedTestBean {
    public void setValue(final String key, final String val) {
    }

    public String getValue(final String key) {
      return "Mapped Value";
    }
  }

  /**
   * The set of property names we expect to have returned when calling
   * {@code getPropertyDescriptors()}.  You should update this list
   * when new properties are added to TestBean.
   */
  public static final String[] PROPERTIES = {
      "booleanProperty",
      "booleanSecond",
      "charProperty",
      "byteProperty",
      "shortProperty",
      "intProperty",
      "longProperty",
      "floatProperty",
      "doubleProperty",
      "stringProperty",
      "dateProperty",
      "nullProperty",
      "readOnlyProperty",
      "writeOnlyProperty",
      "intArray",
      "intIndexed",
      "dateArrayProperty",
      "stringArray",
      "stringIndexed",
      "dupProperty",
      "string2dArray",
      "listIndexed",
      "mapProperty",
      // "mappedObjects",
      // "mappedProperty",
      // "mappedIntProperty",
      "nested",
      "anotherNested",
      "mappedNested",
      "invalidBoolean",
      // NOTE that EVERY class has a implicit property: class, since the Object
      // has a getClass() method.
      "class",
  };

  /**
   * A boolean property.
   */
  private boolean booleanProperty = true;

  /**
   * A boolean property that uses an "is" method for the getter.
   */
  private boolean booleanSecond = true;

  /**
   * A char property.
   */
  private char charProperty = 'x';

  /**
   * A byte property.
   */
  private byte byteProperty = (byte) 121;

  /**
   * A short property.
   */
  private short shortProperty = (short) 987;

  /**
   * A int property.
   */
  private int intProperty = 123;

  /**
   * A long property.
   */
  private long longProperty = 321;

  /**
   * A float property.
   */
  private float floatProperty = (float) 123.0;

  /**
   * A double property.
   */
  private double doubleProperty = 321.0;

  /**
   * A String property.
   */
  private String stringProperty = "This is a string";

  /**
   * A {@link Date} property.
   */
  private Date dateProperty;

  /**
   * A String property with an initial value of null.
   */
  private String nullProperty = null;

  /**
   * A read-only String property.
   */
  private final String readOnlyProperty = "Read Only String Property";

  /**
   * A write-only String property.
   */
  @SuppressWarnings("unused")
  private String writeOnlyProperty = "Write Only String Property";

  /**
   * An integer array property accessed as an array.
   */
  private int[] intArray = {0, 10, 20, 30, 40};

  /**
   * An integer array property accessed as an indexed property.
   */
  private final int[] intIndexed = {0, 10, 20, 30, 40};

  /**
   * A {@link Date} array property.
   */
  private Date[] dateArrayProperty;

  /**
   * A String array property accessed as a String array.
   */
  private String[] stringArray = {
      "String 0", "String 1", "String 2", "String 3", "String 4"
  };

  /**
   * A String array property accessed as an indexed property.
   */
  private final String[] stringIndexed = {
      "String 0", "String 1", "StriinvalidBooleanng 2", "String 3", "String 4"
  };

  /**
   * An "indexed property" accessible via both array and subscript
   * based getters and setters.
   */
  private String[] dupProperty = {
      "Dup 0", "Dup 1", "Dup 2", "Dup 3", "Dup 4"
  };

  /**
   * A two-dimension string array proeprty.
   */
  private String[][] string2dArray = new String[][] {
      new String[] { "1", "2", "3" },
      new String[] { "4", "5", "6" }
  };

  /**
   * A List property accessed as an indexed property.
   */
  private List<String> listIndexed = new ArrayList<String>();

  /**
   * A mapped property with only a getter and setter for a Map.
   */
  private Map<String, String> mapProperty = null;

  /**
   * A mapped property that has String keys and Object values.
   */
  private HashMap<String, Object> mappedObjects = null;

  /**
   * A mapped property that has String keys and String values.
   */
  private HashMap<String, String> mappedProperty = null;

  /**
   * A mapped property that has String keys and int values.
   */
  private HashMap<String, Integer> mappedIntProperty = null;

  /**
   * A nested reference to another test bean (populated as needed).
   */
  private Bean nested = null;

  /**
   * Another nested reference to another test bean.
   */
  private Bean anotherNested = null;

  /**
   * Another nested reference to a bean containing mapped properties.
   */
  private transient MappedTestBean mappedNested = null;

  /**
   * An invalid property that has two boolean getters (getInvalidBoolean and
   * isInvalidBoolean) plus a String setter (setInvalidBoolean). By the rules
   * described in the JavaBeans Specification, this will be considered a
   * read-only boolean property, using isInvalidBoolean() as the getter.
   */
  private boolean invalidBoolean = false;

  public Bean() {
    listIndexed.add("String 0");
    listIndexed.add("String 1");
    listIndexed.add("String 2");
    listIndexed.add("String 3");
    listIndexed.add("String 4");
  }

  public Bean(final String stringProperty) {
    setStringProperty(stringProperty);
  }

  public Bean(final float floatProperty) {
    setFloatProperty(floatProperty);
  }

  public Bean(final boolean booleanProperty) {
    setBooleanProperty(booleanProperty);
  }

  public Bean(final Boolean booleanSecond) {
    setBooleanSecond(booleanSecond.booleanValue());
  }

  public Bean(final float floatProperty, final String stringProperty) {
    setFloatProperty(floatProperty);
    setStringProperty(stringProperty);
  }

  public Bean(final boolean booleanProperty, final String stringProperty) {
    setBooleanProperty(booleanProperty);
    setStringProperty(stringProperty);
  }

  public Bean(final Boolean booleanSecond, final String stringProperty) {
    setBooleanSecond(booleanSecond.booleanValue());
    setStringProperty(stringProperty);
  }

  public Bean(final Integer intProperty) {
    setIntProperty(intProperty.intValue());
  }

  public Bean(final double doubleProperty) {
    setDoubleProperty(doubleProperty);
  }

  Bean(final int intProperty) {
    setIntProperty(intProperty);
  }

  protected Bean(final boolean booleanProperty, final boolean booleanSecond,
      final String stringProperty) {
    setBooleanProperty(booleanProperty);
    setBooleanSecond(booleanSecond);
    setStringProperty(stringProperty);
  }

  public Bean(final List<String> listIndexed) {
    this.listIndexed = listIndexed;
  }

  public Bean(final String[][] string2dArray) {
    this.string2dArray = string2dArray;
  }

  public boolean getBooleanProperty() {
    return booleanProperty;
  }

  public void setBooleanProperty(final boolean booleanProperty) {
    this.booleanProperty = booleanProperty;
  }

  public boolean isBooleanSecond() {
    return booleanSecond;
  }

  public void setBooleanSecond(final boolean booleanSecond) {
    this.booleanSecond = booleanSecond;
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

  public String getStringProperty() {
    return stringProperty;
  }

  public void setStringProperty(final String stringProperty) {
    this.stringProperty = stringProperty;
  }

  public Date getDateProperty() {
    return dateProperty;
  }

  public void setDateProperty(final Date dateProperty) {
    this.dateProperty = dateProperty;
  }

  public String getNullProperty() {
    return nullProperty;
  }

  public void setNullProperty(final String nullProperty) {
    this.nullProperty = nullProperty;
  }

  public String getReadOnlyProperty() {
    return readOnlyProperty;
  }

  public void setWriteOnlyProperty(final String writeOnlyProperty) {
    this.writeOnlyProperty = writeOnlyProperty;
  }

  public int[] getIntArray() {
    return intArray;
  }

  public void setIntArray(final int[] intArray) {
    this.intArray = intArray;
  }

  public int getIntIndexed(final int index) {
    return (intIndexed[index]);
  }

  public void setIntIndexed(final int index, final int value) {
    intIndexed[index] = value;
  }

  public Date[] getDateArrayProperty() {
    return dateArrayProperty;
  }

  public void setDateArrayProperty(final Date[] dateArrayProperty) {
    this.dateArrayProperty = dateArrayProperty;
  }

  public String[] getStringArray() {
    return stringArray;
  }

  public void setStringArray(final String[] stringArray) {
    this.stringArray = stringArray;
  }

  public String getStringIndexed(final int index) {
    return (stringIndexed[index]);
  }

  public void setStringIndexed(final int index, final String value) {
    stringIndexed[index] = value;
  }

  public String[] getDupProperty() {
    return dupProperty;
  }

  public String getDupProperty(final int index) {
    return (dupProperty[index]);
  }

  public void setDupProperty(final int index, final String value) {
    dupProperty[index] = value;
  }

  public void setDupProperty(final String[] dupProperty) {
    this.dupProperty = dupProperty;
  }

  public String[] getString2dArray(final int index) {
    return string2dArray[index];
  }

  public List<String> getListIndexed() {
    return listIndexed;
  }

  public Map<String, String> getMapProperty() {
    // Create the map the very first time
    if (mapProperty == null) {
      mapProperty = new HashMap<String, String>();
      mapProperty.put("First Key", "First Value");
      mapProperty.put("Second Key", "Second Value");
    }
    return mapProperty;
  }

  public void setMapProperty(final Map<String, String> mapProperty) {
    // Create the map the very first time
    final Map<String, String> newValue;
    if (mapProperty == null) {
      newValue = new HashMap<>();
      newValue.put("First Key", "First Value");
      newValue.put("Second Key", "Second Value");
    } else {
      newValue = mapProperty;
    }
    this.mapProperty = newValue;
  }

  public Object getMappedObjects(final String key) {
    // Create the map the very first time
    if (mappedObjects == null) {
      mappedObjects = new HashMap<String, Object>();
      mappedObjects.put("First Key", "First Value");
      mappedObjects.put("Second Key", "Second Value");
    }
    return (mappedObjects.get(key));
  }

  public void setMappedObjects(final String key, final Object value) {
    // Create the map the very first time
    if (mappedObjects == null) {
      mappedObjects = new HashMap<String, Object>();
      mappedObjects.put("First Key", "First Value");
      mappedObjects.put("Second Key", "Second Value");
    }
    mappedObjects.put(key, value);
  }

  public String getMappedProperty(final String key) {
    // Create the map the very first time
    if (mappedProperty == null) {
      mappedProperty = new HashMap<String, String>();
      mappedProperty.put("First Key", "First Value");
      mappedProperty.put("Second Key", "Second Value");
    }
    return mappedProperty.get(key);
  }

  public void setMappedProperty(final String key, final String value) {
    // Create the map the very first time
    if (mappedProperty == null) {
      mappedProperty = new HashMap<String, String>();
      mappedProperty.put("First Key", "First Value");
      mappedProperty.put("Second Key", "Second Value");
    }
    mappedProperty.put(key, value);
  }

  public int getMappedIntProperty(final String key) {
    // Create the map the very first time
    if (mappedIntProperty == null) {
      mappedIntProperty = new HashMap<String, Integer>();
      mappedIntProperty.put("One", 1);
      mappedIntProperty.put("Two", 2);
    }
    final Integer x = mappedIntProperty.get(key);
    return ((x == null) ? 0 : x.intValue());
  }

  public void setMappedIntProperty(final String key, final int value) {
    mappedIntProperty.put(key, value);
  }

  public Bean getNested() {
    if (nested == null) {
      nested = new Bean();
    }
    return nested;
  }

  public Bean getAnotherNested() {
    return anotherNested;
  }

  public void setAnotherNested(final Bean anotherNested) {
    this.anotherNested = anotherNested;
  }

  public MappedTestBean getMappedNested() {
    if (mappedNested == null) {
      mappedNested = new MappedTestBean();
    }
    return mappedNested;
  }

  public boolean getInvalidBoolean() {
    return invalidBoolean;
  }

  public boolean isInvalidBoolean() {
    return invalidBoolean;
  }

  public void setInvalidBoolean(final String invalidBoolean) {
    this.invalidBoolean = "true".equalsIgnoreCase(invalidBoolean)
            || "yes".equalsIgnoreCase(invalidBoolean)
            || "1".equalsIgnoreCase(invalidBoolean);
  }

  // Static Variables

  // Static Variables

  /**
   * A static variable that is accessed and updated via static methods for
   * MethodUtils testing.
   */
  private static int counter = 0;

  /**
   * Return the current value of the counter.
   */
  public static int currentCounter() {
    return counter;
  }

  /**
   * Increment the current value of the counter by 1.
   */
  public static void incrementCounter() {
    incrementCounter(1);
  }

  /**
   * Increment the current value of the counter by the specified amount.
   *
   * @param amount
   *          Amount to be added to the current counter
   */
  public static void incrementCounter(final int amount) {
    counter += amount;
  }

  /**
   * Increments the current value of the count by the specified amount * 2. It
   * has the same name as the method above so as to test the looseness of
   * getMethod.
   */
  public static void incrementCounter(final Number amount) {
    counter += 2 * amount.intValue();
  }

  //  resume checkstyle: MagicNumberCheck
}
