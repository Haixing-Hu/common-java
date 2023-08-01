////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml;

import java.util.Stack;

import ltd.qubit.commons.lang.Argument;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A utility class that iterates through the element nodes of a DOM tree.
 *
 * <p>It differs from the {@link DomNodeIterator}, since it only iterates throw
 * element nodes.
 *
 * <p>Note that this class is not thread safe.
 *
 * @author Haixing Hu
 * @see DomNodeIterator
 */
public final class DomElementIterator {

  private Element           current;
  private NodeList          children;
  private final Stack<Node> nodes;

  public DomElementIterator(final Node root) {
    nodes = new Stack<>();
    nodes.add(Argument.requireNonNull("root", root));
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
  public Element next() {
    Element next = null;
    while (! nodes.isEmpty()) {
      final Node node = nodes.pop();
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        next = (Element) node;
        break;
      }
    }
    if (next == null) {
      return null;
    }
    current = next;
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
