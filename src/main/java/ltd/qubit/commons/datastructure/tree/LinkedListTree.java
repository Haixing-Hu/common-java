////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.tree;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * An implementation of tree using a linked list to store the children.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class LinkedListTree<KEY, VALUE> extends AbstractListTree<KEY, VALUE> {

  @Override
  protected List<Tree<KEY, VALUE>> makeTreeList() {
    return new LinkedList<>();
  }

  public LinkedListTree(final KEY key, final VALUE value) {
    super(key, value);
  }

}
