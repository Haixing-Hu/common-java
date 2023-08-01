////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * An implementation of map using an array list to store the keys and values.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class ArrayListMap<KEY, VALUE> extends AbstractListMap<KEY, VALUE> {

  @Override
  protected List<AbstractListMap.Entry<KEY, VALUE>> makeList() {
    return new ArrayList<>();
  }

  public ArrayListMap() {
  }

  public ArrayListMap(final Map<? extends KEY, ? extends VALUE> map) {
    this.putAll(map);
  }

}
