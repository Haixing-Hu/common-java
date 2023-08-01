////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.testbed;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomList implements List<String> {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean contains(final Object o) {
    return false;
  }

  @Override
  public Iterator<String> iterator() {
    return null;
  }

  @Override
  public Object[] toArray() {
    return new Object[0];
  }

  @Override
  public <T> T[] toArray(final T[] a) {
    return null;
  }

  @Override
  public boolean add(final String s) {
    return false;
  }

  @Override
  public void add(final int index, final String element) {
    //  empty
  }

  @Override
  public boolean remove(final Object o) {
    return false;
  }

  @Override
  public String remove(final int index) {
    return null;
  }

  @Override
  public boolean containsAll(final Collection<?> c) {
    return false;
  }

  @Override
  public boolean addAll(final Collection<? extends String> c) {
    return false;
  }

  @Override
  public boolean addAll(final int index, final Collection<? extends String> c) {
    return false;
  }

  @Override
  public boolean removeAll(final Collection<?> c) {
    return false;
  }

  @Override
  public boolean retainAll(final Collection<?> c) {
    return false;
  }

  @Override
  public void clear() {

  }

  @Override
  public String get(final int index) {
    return null;
  }

  @Override
  public String set(final int index, final String element) {
    return null;
  }

  @Override
  public int indexOf(final Object o) {
    return 0;
  }

  @Override
  public int lastIndexOf(final Object o) {
    return 0;
  }

  @Override
  public ListIterator<String> listIterator() {
    return null;
  }

  @Override
  public ListIterator<String> listIterator(final int index) {
    return null;
  }

  @Override
  public List<String> subList(final int fromIndex, final int toIndex) {
    return null;
  }
}
