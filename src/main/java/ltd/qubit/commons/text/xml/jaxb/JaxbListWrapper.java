////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml.jaxb;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAnyElement;

/**
 * A wrapper class for serialize/deserialize list of object with JAXB.
 *
 * @author Haixing Hu
 */
public class JaxbListWrapper<T> {

  private final List<T> list;

  public JaxbListWrapper() {
    list = new ArrayList<>();
  }

  public JaxbListWrapper(final List<T> list) {
    this.list = list;
  }

  @XmlAnyElement(lax = true)
  public List<T> getList() {
    return list;
  }
}
