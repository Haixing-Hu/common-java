////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import java.util.Properties;

/**
 * Bean with inner bean.
 *
 * @author Haixing Hu
 */
public class BeanWithInnerBean {
  private final InnerBean innerBean = new InnerBean();

  public BeanWithInnerBean() {
  }

  public InnerBean getInnerBean() {
    return innerBean;
  }

  public class InnerBean {
    private final Properties fish = new Properties();

    public String getFish(final String key) {
      return fish.getProperty(key);
    }

    public void setFish(final String key, final String value) {
      fish.setProperty(key, value);
    }
  }
}
