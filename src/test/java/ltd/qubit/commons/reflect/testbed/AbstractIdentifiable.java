////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
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