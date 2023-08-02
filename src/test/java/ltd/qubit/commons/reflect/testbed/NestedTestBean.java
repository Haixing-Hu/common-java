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
 * Specialist test bean for complex nested properties.
 *
 * @author Haixing Hu
 */
public class NestedTestBean {

  public NestedTestBean(final String name) {
    setName(name);
  }

  private String name;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  private String testString = "NOT SET";

  public String getTestString() {
    return testString;
  }

  public void setTestString(final String testString) {
    this.testString = testString;
  }

  private boolean testBoolean = false;

  public boolean getTestBoolean() {
    return testBoolean;
  }

  public void setTestBoolean(final boolean testBoolean) {
    this.testBoolean = testBoolean;
  }

  private NestedTestBean[] indexedBeans;

  public void init() {
    indexedBeans = new NestedTestBean[5];
    indexedBeans[0] = new NestedTestBean("Bean@0");
    indexedBeans[1] = new NestedTestBean("Bean@1");
    indexedBeans[2] = new NestedTestBean("Bean@2");
    indexedBeans[3] = new NestedTestBean("Bean@3");
    indexedBeans[4] = new NestedTestBean("Bean@4");

    simpleBean = new NestedTestBean("Simple Property Bean");
  }

  public NestedTestBean getIndexedProperty(final int index) {
    return (this.indexedBeans[index]);
  }

  public void setIndexedProperty(final int index, final NestedTestBean value) {
    this.indexedBeans[index] = value;
  }

  private NestedTestBean simpleBean;

  public NestedTestBean getSimpleBeanProperty() {
    return simpleBean;
  }
}
