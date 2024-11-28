////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.tree;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * An implementation of tree using a array list to store the children.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class ArrayListTree<KEY, VALUE> extends AbstractListTree<KEY, VALUE> {

  public ArrayListTree() {}

  public ArrayListTree(final KEY key, final VALUE value) {
    super(key, value);
  }

  @Override
  protected List<Tree<KEY, VALUE>> makeTreeList() {
    return new ArrayList<>();
  }
}
