////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list;

import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

import jakarta.validation.constraints.NotNull;

import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.util.expand.ExpansionPolicy;

import static ltd.qubit.commons.lang.Argument.requireGreater;
import static ltd.qubit.commons.lang.Argument.requireIndexInCloseRange;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * {@link List} 的一个实现，它使用内部数组缓冲区来存储节点，并使用数组索引来模拟节点之间的链接。
 *
 * <p>此实现将具有{@link LinkedList}的优点，并避免了频繁的内存分配。
 *
 * <p>此实现使用一个数组来存储列表的节点。 每个节点都包含前一个节点的位置和下一个节点的位置。
 * -1的位置表示一个空节点。 数组中的所有节点都包含两个列表：已分配列表和空闲列表。
 * 已分配列表是一个双向链接的循环列表，其中包含所有已分配的节点（即，用于存储元素的节点），
 * 空闲列表是包含所有空闲节点的单向链表。 该实现还包含已分配列表和空闲列表的头节点的位置。
 *
 * @author 胡海星
 */
public class ArrayLinkedList<E> extends AbstractSequentialList<E>
    implements Deque<E>, CloneableEx<ArrayLinkedList<E>>, Serializable {

  private static final long serialVersionUID = 7384854819145629005L;

  /**
   * {@link ArrayLinkedList} 的节点类。
   *
   * @author 胡海星
   */
  protected static class Node implements Serializable {

    private static final long serialVersionUID = 5992909288570428948L;

    Object element = null;
    int previous = -1;
    int next = -1;
  }

  /**
   * {@link ArrayLinkedList} 的迭代器类。
   *
   * @author 胡海星
   */
  protected static class ListIter<E> implements ListIterator<E> {

    protected final ArrayLinkedList<E> source;
    protected int expectedModCount;
    protected int lastReturned;
    protected int index;
    protected int pos;

    protected ListIter(final ArrayLinkedList<E> source, final int index) {
      this.source = source;
      expectedModCount = source.modCount;
      lastReturned = -1;
      this.index = requireIndexInCloseRange(index, 0, source.size);
      pos = source.getNodeAtIndex(index);
    }

    @Override
    public boolean hasNext() {
      return index < source.size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E next() {
      if (source.modCount != expectedModCount) {
        throw new ConcurrentModificationException();
      }
      if (index >= source.size) {
        throw new NoSuchElementException();
      }
      final Node node = source.nodes[pos];
      lastReturned = pos;
      pos = node.next;
      ++index;
      return (E) node.element;
    }

    @Override
    public boolean hasPrevious() {
      return (index > 0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E previous() {
      if (source.modCount != expectedModCount) {
        throw new ConcurrentModificationException();
      }
      if (index <= 0) {
        throw new NoSuchElementException();
      }
      pos = source.nodes[pos].previous;
      --index;
      final Node node = source.nodes[pos];
      lastReturned = pos;
      return (E) node.element;
    }

    @Override
    public int nextIndex() {
      return index;
    }

    @Override
    public int previousIndex() {
      return index - 1;
    }

    @Override
    public void remove() {
      if (source.modCount != expectedModCount) {
        throw new ConcurrentModificationException();
      }
      if (lastReturned < 0) {
        throw new IllegalStateException();
      }
      final int lastNext = source.removeNode(lastReturned);
      ++expectedModCount;
      if (source.size == 0) { // deal with removing the last node
        pos = -1;
        index = 0;
      } else if (pos == lastReturned) { // the last call is previous()
        pos = lastNext;
      } else { // the last call is next()
        --index;
      }
      lastReturned = -1;
    }

    @Override
    public void set(final E e) {
      if (source.modCount != expectedModCount) {
        throw new ConcurrentModificationException();
      }
      if (lastReturned < 0) {
        throw new IllegalStateException();
      }
      source.nodes[lastReturned].element = e;
    }

    @Override
    public void add(final E e) {
      if (source.modCount != expectedModCount) {
        throw new ConcurrentModificationException();
      }
      // add the new element
      if (source.size == source.capacity) {
        source.ensureCapacity(source.size + 1);
      }
      if (index < source.size) {
        assert ((source.size > 0) && (pos >= 0));
        source.insertNodeBefore(pos, e);
      } else { // index == size
        // note that size may be zero, so we need to update the pos
        int tail = source.getTail();
        tail = source.insertNodeAfter(tail, e);
        pos = source.nodes[tail].next;
      }
      ++index;
      ++expectedModCount;
      lastReturned = -1;
    }
  }

  /**
   * 通过{@link ListIter#previous()}提供降序迭代器的适配器。
   *
   * @author 胡海星
   */
  protected static class DescendingIter<E> implements Iterator<E> {

    final ArrayLinkedList<E> source;
    final ListIter<E> iter;

    public DescendingIter(final ArrayLinkedList<E> source) {
      this.source = source;
      this.iter = new ListIter<>(source, source.size);
    }

    @Override
    public boolean hasNext() {
      return iter.hasPrevious();
    }

    @Override
    public E next() {
      return iter.previous();
    }

    @Override
    public void remove() {
      iter.remove();
    }
  }

  protected ExpansionPolicy expansionPolicy;
  protected Node[] nodes;
  protected int capacity;
  protected int size;
  protected int head;
  protected int freeListHead;

  public ArrayLinkedList() {
    this(ExpansionPolicy.getInitialCapacity(),
        ExpansionPolicy.getDefault());
  }

  public ArrayLinkedList(final int initialCapacity) {
    this(initialCapacity, ExpansionPolicy.getDefault());
  }

  public ArrayLinkedList(final ExpansionPolicy expansionPolicy) {
    this(ExpansionPolicy.getInitialCapacity(), expansionPolicy);
  }

  public ArrayLinkedList(final int initialCapacity,
      final ExpansionPolicy expansionPolicy) {
    requireGreater("initialCapacity", initialCapacity, "zero", 0);
    capacity = initialCapacity;
    this.expansionPolicy = requireNonNull("expansionPolicy", expansionPolicy);
    nodes = new Node[capacity];
    size = 0;
    head = -1;
    // initialize the nodes array in order to avoid frequent memory allocation
    // makes all free nodes become a singly linked list
    for (int i = 0; i < capacity; ++i) {
      nodes[i] = new Node();
      nodes[i].next = i + 1;
    }
    freeListHead = 0;
    nodes[capacity - 1].next = -1;
  }

  public ArrayLinkedList(final Collection<E> col) {
    this((col.isEmpty() ? ExpansionPolicy.getInitialCapacity() : col.size()),
        ExpansionPolicy.getDefault());
    addAll(col);
  }

  public ArrayLinkedList(final Collection<E> col,
      final ExpansionPolicy expansionPolicy) {
    this((col.isEmpty() ? ExpansionPolicy.getInitialCapacity() : col.size()),
        expansionPolicy);
    addAll(col);
  }

  @Override
  public int size() {
    return size;
  }

  /**
   * Gets the current capacity of the internal node array.
   *
   * @return the current capacity of the internal node array.
   */
  public int capacity() {
    return capacity;
  }

  /**
   * Gets the expansion policy of this list.
   *
   * @return the expansion policy of this list.
   */
  public ExpansionPolicy getExpansionPolicy() {
    return expansionPolicy;
  }

  @Override
  public boolean isEmpty() {
    return (size == 0);
  }

  @Override
  public boolean contains(@Nullable final Object obj) {
    final int index = findNodeForward(head, head, obj);
    return (index >= 0);
  }

  @Override
  public Iterator<E> iterator() {
    return new ListIter<>(this, 0);
  }

  @Override
  public E remove() {
    return removeFirst();
  }

  @SuppressWarnings("unchecked")
  @Override
  public E remove(final int index) {
    if ((index < 0) || (index >= size)) {
      throw new IndexOutOfBoundsException(
          "Index: " + index + ", Size: " + size);
    }
    final int pos = getNodeAtIndex(index);
    final E old = (E) nodes[pos].element;
    removeNode(pos);
    return old;
  }

  @Override
  public boolean remove(@Nullable final Object obj) {
    return removeFirstOccurrence(obj);
  }

  @Override
  public boolean containsAll(final Collection<?> col) {
    for (final Object obj : col) {
      final int pos = findNodeForward(head, head, obj);
      if (pos < 0) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean addAll(final Collection<? extends E> col) {
    final int n = col.size();
    if (n == 0) {
      return false;
    }
    final int newSize = size + n;
    if (newSize > capacity) {
      ensureCapacity(newSize);
    }
    int tail = getTail();
    for (final E e : col) {
      tail = insertNodeAfter(tail, e);
    }
    return true;
  }

  @Override
  public boolean addAll(final int index, final Collection<? extends E> col) {
    if ((index < 0) || (index > size)) {
      throw new IndexOutOfBoundsException(
          "Index: " + index + ", Size: " + size);
    }
    final int n = col.size();
    if (n == 0) {
      return false;
    }
    final int newSize = size + n;
    if (newSize > capacity) {
      ensureCapacity(newSize);
    }
    if (index < size) {
      assert (size > 0);
      final int pos = getNodeAtIndex(index);
      assert (pos >= 0);
      for (final E e : col) {
        insertNodeBefore(pos, e);
      }
    } else { // index == size, insert at the tail
      int tail = getTail();
      for (final E e : col) {
        tail = insertNodeAfter(tail, e);
      }
    }
    return true;
  }

  @Override
  public boolean removeAll(@NotNull final Collection<?> col) {
    if ((size == 0) || (col.size() == 0)) {
      return false;
    }
    boolean modified = false;
    int index = 0;
    int pos = head;
    while (index < size) {
      final Node node = nodes[pos];
      if (col.contains(node.element)) {
        pos = removeNode(pos);
        modified = true;
      } else {
        pos = node.next;
        ++index;
      }
    }
    return modified;
  }

  @Override
  public boolean retainAll(@NotNull final Collection<?> col) {
    if (size == 0) {
      return false;
    }
    if (col.size() == 0) {
      clear();
      return true;
    }
    boolean modified = false;
    int index = 0;
    int pos = head;
    while (index < size) {
      final Node node = nodes[pos];
      if (!col.contains(node.element)) {
        pos = removeNode(pos);
        modified = true;
      } else {
        pos = node.next;
        ++index;
      }
    }
    return modified;
  }

  @Override
  public void clear() {
    while (size > 0) {
      removeNode(head);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public E get(final int index) {
    if ((index < 0) || (index >= size)) {
      throw new IndexOutOfBoundsException(
          "Index: " + index + ", Size: " + size);
    }
    final int pos = getNodeAtIndex(index);
    return (E) nodes[pos].element;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E set(final int index, @Nullable final E element) {
    if ((index < 0) || (index >= size)) {
      throw new IndexOutOfBoundsException(
          "Index: " + index + ", Size: " + size);
    }
    final int pos = getNodeAtIndex(index);
    final E old = (E) nodes[pos].element;
    nodes[pos].element = element;
    return old;
  }

  @Override
  public boolean add(@Nullable final E e) {
    addLast(e);
    return true;
  }

  @Override
  public void add(final int index, @Nullable final E element) {
    if ((index < 0) || (index > size)) {
      throw new IndexOutOfBoundsException(
          "Index: " + index + ", Size: " + size);
    }
    if (size == capacity) {
      ensureCapacity(size + 1);
    }
    if (index < size) {
      final int pos = getNodeAtIndex(index);
      insertNodeBefore(pos, element);
    } else {  // index == size
      final int tail = getTail();
      insertNodeAfter(tail, element);
    }
  }

  @Override
  public int indexOf(@Nullable final Object obj) {
    if (size == 0) {
      return -1;
    }
    int index = 0;
    int pos = head;
    if (obj == null) {
      do {
        final Node node = nodes[pos];
        if (node.element == null) {
          return index;
        }
        pos = node.next;
        ++index;
      } while (pos != head);
    } else {  // obj != null
      do {
        final Node node = nodes[pos];
        if (obj.equals(node.element)) {
          return index;
        }
        pos = node.next;
        ++index;
      } while (pos != head);
    }
    return -1;
  }

  @Override
  public int lastIndexOf(final Object obj) {
    if (size == 0) {
      return -1;
    }
    final int tail = nodes[head].previous;
    int index = size - 1;
    int pos = tail;
    if (obj == null) {
      do {
        final Node node = nodes[pos];
        if (node.element == null) {
          return index;
        }
        pos = node.previous;
        --index;
      } while (pos != tail);
    } else {  // obj != null
      do {
        final Node node = nodes[pos];
        if (obj.equals(node.element)) {
          return index;
        }
        pos = node.previous;
        --index;
      } while (pos != tail);
    }
    return -1;
  }

  @Override
  public ListIterator<E> listIterator() {
    return new ListIter<>(this, 0);
  }

  @Override
  public ListIterator<E> listIterator(final int index) {
    return new ListIter<>(this, index);
  }

  @Override
  public void addFirst(@Nullable final E e) {
    if (size == capacity) {
      ensureCapacity(size + 1);
    }
    insertNodeBefore(head, e);
  }

  @Override
  public void addLast(@Nullable final E e) {
    if (size == capacity) {
      ensureCapacity(size + 1);
    }
    final int tail = getTail();
    insertNodeAfter(tail, e);
  }

  @Override
  public boolean offerFirst(@Nullable final E e) {
    addFirst(e);
    return true;
  }

  @Override
  public boolean offerLast(@Nullable final E e) {
    addLast(e);
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E removeFirst() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    final E result = (E) nodes[head].element;
    removeNode(head);
    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E removeLast() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    final int tail = nodes[head].previous;
    final E result = (E) nodes[tail].element;
    removeNode(tail);
    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E pollFirst() {
    if (size == 0) {
      return null;
    }
    final E result = (E) nodes[head].element;
    removeNode(head);
    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E pollLast() {
    if (size == 0) {
      return null;
    }
    final int tail = nodes[head].previous;
    final E result = (E) nodes[tail].element;
    removeNode(tail);
    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E getFirst() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    return (E) nodes[head].element;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E getLast() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    final int tail = nodes[head].previous;
    return (E) nodes[tail].element;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E peekFirst() {
    if (size == 0) {
      return null;
    } else {
      return (E) nodes[head].element;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public E peekLast() {
    if (size == 0) {
      return null;
    } else {
      final int tail = nodes[head].previous;
      return (E) nodes[tail].element;
    }
  }

  @Override
  public boolean removeFirstOccurrence(@Nullable final Object obj) {
    final int pos = findNodeForward(head, head, obj);
    if (pos < 0) {
      return false;
    } else {
      removeNode(pos);
      return true;
    }
  }

  @Override
  public boolean removeLastOccurrence(@Nullable final Object obj) {
    final int tail = getTail();
    final int pos = findNodeBackward(tail, tail, obj);
    if (pos < 0) {
      return false;
    } else {
      removeNode(pos);
      return true;
    }
  }

  @Override
  public boolean offer(@Nullable final E e) {
    return offerLast(e);
  }

  @Override
  public E poll() {
    return pollFirst();
  }

  @Override
  public E element() {
    return getFirst();
  }

  @Override
  public E peek() {
    return peekFirst();
  }

  @Override
  public void push(@Nullable final E e) {
    addFirst(e);
  }

  @Override
  public E pop() {
    return removeFirst();
  }

  @Override
  public Iterator<E> descendingIterator() {
    return new DescendingIter<>(this);
  }

  /**
   * Returns a shallow copy of this {@link ArrayLinkedList}. (The elements
   * themselves are not cloned.)
   *
   * @return a shallow copy of this {@link ArrayLinkedList} instance.
   */
  @Override
  public ArrayLinkedList<E> cloneEx() {
    return new ArrayLinkedList<>(this, expansionPolicy);
  }

  /**
   * Ensures the capacity of the node array to be able to hold the nodes of the
   * specified count.
   *
   * @param count
   *     the number of nodes need to be hold by the node array. It MUST be
   *     greater than the current capacity.
   */
  protected final void ensureCapacity(final int count) {
    assert (capacity < count);
    nodes = expansionPolicy.expand(nodes, capacity, count, Node.class);
    assert (count <= nodes.length);
    for (int i = capacity; i < nodes.length; ++i) {
      nodes[i] = new Node();  // may throw OutOfMemoryError
      nodes[i].next = i + 1;
    }
    // add the new nodes list to the free list
    nodes[nodes.length - 1].next = freeListHead;
    freeListHead = capacity;
    capacity = nodes.length;
  }

  /**
   * Gets the position of the tail (last node) of the list.
   *
   * @return the position of the tail of the list, or -1 if the list is empty.
   */
  protected final int getTail() {
    return (head < 0 ? -1 : nodes[head].previous);
  }

  /**
   * Removes a node from the list.
   *
   * @param pos
   *     the position of the node to be removed.
   * @return the position of the node next to the node just removed. If the node
   *     just removed is the last node, returns -1.
   */
  protected final int removeNode(final int pos) {
    assert ((size > 0) && (pos >= 0) && (pos < capacity));
    final Node node = nodes[pos];
    int result = -1;
    if (node.next != pos) {
      // this is NOT the sole node
      result = node.next;
      nodes[node.next].previous = node.previous;
      nodes[node.previous].next = node.next;
      if (head == pos) {
        head = node.next;
      }
    } else { // node.next == pos
      // this is the sole node
      assert (head == pos);
      head = -1;
    }
    // dereference the element so that it could be garbage collected
    node.element = null;
    // add the node to the free list
    node.next = freeListHead;
    freeListHead = pos;
    // update the state
    --size;
    ++modCount;
    return result;
  }

  /**
   * Inserts an element before the specified node of the non-empty list.
   *
   * <p>The node array MUST have enough free space before calling this function.
   *
   * @param pos
   *     the position of the node before which to insert the new element. If the
   *     list is empty before calling this function, this argument must be -1.
   * @param e
   *     the element to be inserted.
   * @return the position of the new node inserted by this function.
   */
  protected final int insertNodeBefore(final int pos, final E e) {
    assert ((size < capacity) && (freeListHead >= 0));
    // get a free node
    final int newPos = freeListHead;
    final Node newNode = nodes[newPos];
    freeListHead = newNode.next;
    // set the element of the new node
    newNode.element = e;
    // insert the new node
    if (pos >= 0) {
      // the list is non-empty before calling this function
      final Node nextNode = nodes[pos];
      final Node prevNode = nodes[nextNode.previous];
      newNode.next = pos;
      newNode.previous = nextNode.previous;
      nextNode.previous = newPos;
      prevNode.next = newPos;
      // update the head of allocated list if necessary
      if (head == pos) {
        head = newPos;
      }
    } else { // pos < 0
      // the list is empty before calling this function
      // construct a sole node cycle list
      newNode.next = newPos;
      newNode.previous = newPos;
      assert (head < 0);
      head = newPos;
    }
    //  update the state
    ++size;
    ++modCount;
    return newPos;
  }

  /**
   * Inserts an element after the specified node of the non-empty list.
   *
   * <p>The node array MUST have enough free space before calling this function.
   *
   * @param pos
   *     the position of the node after which to insert the new element. If the
   *     list is empty before calling this function, this argument must be -1.
   * @param e
   *     the element to be inserted.
   * @return the position of the new node inserted by this function.
   */
  protected final int insertNodeAfter(final int pos, final E e) {
    assert ((size < capacity) && (freeListHead >= 0));
    // get a free node
    final int newPos = freeListHead;
    final Node newNode = nodes[newPos];
    freeListHead = newNode.next;
    // set the element of the new node
    newNode.element = e;
    // insert the new node
    if (pos >= 0) {
      // the list is non-empty before calling this function
      final Node prevNode = nodes[pos];
      final Node nextNode = nodes[prevNode.next];
      newNode.previous = pos;
      newNode.next = prevNode.next;
      prevNode.next = newPos;
      nextNode.previous = newPos;
      // don't need to update the head of allocated list
    } else {  // pos < 0
      // the list is empty before calling this function
      // construct a sole node cycle list
      newNode.next = newPos;
      newNode.previous = newPos;
      assert (head < 0);
      head = newPos;
    }
    //  update the state
    ++size;
    ++modCount;
    return newPos;
  }

  /**
   * Gets the node at the specified index of the list.
   *
   * <p>The allowed index ranges from 0 to the size of the list. If the index is
   * the size of the list, gets the node next to the last node of the list,
   * which is also the head node of the list, since the list is a bidirectional
   * cycle.
   *
   * @param index
   *     the index of the node to be get. It must be in the range of {@code [0,
   *     size]}.
   * @return the position of the node at the specified index in the list.
   */
  protected final int getNodeAtIndex(final int index) {
    assert ((index >= 0) && (index <= size));
    if (size == 0) {
      return -1;
    }
    if (index <= (size / 2)) {
      int pos = head;
      for (int i = 0; i < index; ++i) {
        pos = nodes[pos].next;
      }
      return pos;
    } else {
      int pos = head;
      for (int i = size; i > index; --i) {
        pos = nodes[pos].previous;
      }
      return pos;
    }
  }

  /**
   * Finds the first occurrence of the specified element in the allocated list
   * in the forward direction.
   *
   * @param startPos
   *     the position of the node where the search starts. If the list is empty
   *     before calling this function, this argument must be -1, and this
   *     function will returns -1.
   * @param stopPos
   *     the position of the node next to the node where the search ends. If it
   *     is the same as {@code startPos}, the whole list will be searched, since
   *     the list is a cycle. If the list is empty before calling this function,
   *     this argument must be -1, and this function will returns -1.
   * @param obj
   *     the object to be found, which could be null.
   * @return the position of the node where the specified element first
   *     occurred, or -1 if no such node.
   */
  protected final int findNodeForward(final int startPos, final int stopPos,
      @Nullable final Object obj) {
    if (startPos < 0) {
      return -1;
    }
    int pos = startPos;
    if (obj == null) {
      do {
        final Node node = nodes[pos];
        if (node.element == null) {
          return pos;
        }
        pos = node.next;
      } while (pos != stopPos);
    } else {  // obj != null
      do {
        final Node node = nodes[pos];
        if (obj.equals(node.element)) {
          return pos;
        }
        pos = node.next;
      } while (pos != stopPos);
    }
    return -1;
  }

  /**
   * Finds the first occurrence of the specified element in the allocated list
   * in the backward direction.
   *
   * @param startPos
   *     the position of the node where the search starts. If the list is empty
   *     before calling this function, this argument must be -1, and this
   *     function will returns -1.
   * @param stopPos
   *     the position of the node previous to the node where the search ends. If
   *     it is the same as {@code startPos}, the whole list will be searched,
   *     since the list is a cycle. If the list is empty before calling this
   *     function, this argument must be -1, and this function will returns -1.
   * @param obj
   *     the object to be found, which could be null.
   * @return the position of the node where the specified element first
   *     occurred, or -1 if no such node.
   */
  protected final int findNodeBackward(final int startPos, final int stopPos,
      @Nullable final Object obj) {
    if (startPos < 0) {
      return -1;
    }
    int pos = startPos;
    if (obj == null) {
      do {
        final Node node = nodes[pos];
        if (node.element == null) {
          return pos;
        }
        pos = node.previous;
      } while (pos != stopPos);
    } else {  // obj != null
      do {
        final Node node = nodes[pos];
        if (obj.equals(node.element)) {
          return pos;
        }
        pos = node.previous;
      } while (pos != stopPos);
    }
    return -1;
  }
}