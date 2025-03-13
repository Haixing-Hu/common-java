////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.map;


import ltd.qubit.commons.lang.ObjectUtils;

/**
 * Wrapper class using objects as keys of a hash map.
 * <p>
 * This class wraps an object and use the correct hash code function for the object.
 * <p>
 * The JDK's native HashMap has a problem: if the key is an native array, the
 * default implementation of the {@link Object#hashCode()} method will return the
 * hash code of the array object, not the hash code of the array's content. This
 * will cause the HashMap to fail to find the key in the map. This class can be
 * used to solve this problem.
 *
 * @author Haixing Hu
 */
public class ObjectKeyWrapper extends KeyWrapper<Object> {

  public ObjectKeyWrapper(final Object key) {
    super(key, ObjectUtils::hashCode);
  }
}