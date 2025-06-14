////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml;

import java.util.Stack;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A utility class that allows the walking of any DOM tree using a stack instead
 * of recursion. As the node tree is walked the next node is popped off of the
 * stack and all of its children are automatically added to the stack to be
 * called in tree order.
 *
 * <p>Note that this class is not thread safe.
 *
 * @author Haixing Hu
 */
public final class DomNodeIterator {

  private Node              current;
  private NodeList          children;
  private final Stack<Node> nodes;

  public DomNodeIterator(final Node root) {
    nodes = new Stack<>();
    nodes.add(requireNonNull("root", root));
    current = null;
    children = null;
  }

  /**
   * Returns true if there are more nodes on the current stack.
   *
   * @return true if there are more nodes on the current stack.
   */
  public boolean hasNext() {
    return (nodes.size() > 0);
  }

  /**
   * Returns the next {@link Node} on the stack and pushes all of its children
   * onto the stack, allowing us to walk the node tree without the use of
   * recursion. If there are no more nodes on the stack then null is returned.
   *
   * @return the next {@link Node} on the stack or null if there isn't a next
   *         node.
   */
  public Node next() {
    // if no next node return null
    if (nodes.isEmpty()) {
      return null;
    }
    // pop the next node off of the stack and push all of its children onto
    // the stack
    current = nodes.pop();
    children = current.getChildNodes();
    if (children != null) {
      // put the children node on the stack in first to last order
      for (int i = children.getLength() - 1; i >= 0; --i) {
        nodes.add(children.item(i));
      }
    }
    return current;
  }

  /**
   * Skips over and removes from the node stack the children of the last node.
   * When getting a next node from the walker, that node's children are
   * automatically added to the stack. You can call this method to remove those
   * children from the stack.
   *
   * <p>This is useful when you don't want to process deeper into the current
   * path of the node tree but you want to continue processing sibling nodes.
   */
  public void skipChildren() {
    if (children != null) {
      final int n = children.getLength();
      for (int i = 0; i < n; ++i) {
        final Node child = nodes.peek();
        if (child.equals(children.item(i))) {
          nodes.pop();
        }
      }
    }
  }
}