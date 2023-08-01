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

public class AbstractIdentifiable<ID, T> implements Serializable {

  private static final long serialVersionUID = -6505619152724793822L;
  private ID id;

  public ID getId() {
    return null;
  }

  public T setId(final ID id) {
    return null;
  }
}
