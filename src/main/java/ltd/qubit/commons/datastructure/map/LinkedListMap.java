////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.map;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * An implementation of map using an linked list to store the keys and values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class LinkedListMap<KEY, VALUE> extends AbstractListMap<KEY, VALUE> {

  @Override
  protected List<AbstractListMap.Entry<KEY, VALUE>> makeList() {
    return new LinkedList<>();
  }

  public LinkedListMap() {
  }

  public LinkedListMap(final Map<? extends KEY, ? extends VALUE> map) {
    putAll(map);
  }
}
