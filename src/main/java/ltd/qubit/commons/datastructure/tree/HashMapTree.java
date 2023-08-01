////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.tree;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * An implementation of tree using an hash map to store the children.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class HashMapTree<KEY, VALUE> extends AbstractMapTree<KEY, VALUE> {

  @Override
  protected Map<KEY, Tree<KEY, VALUE>> makeTreeMap() {
    return new HashMap<>();
  }

  public HashMapTree(final KEY key, final VALUE value) {
    super(key, value);
  }
}
