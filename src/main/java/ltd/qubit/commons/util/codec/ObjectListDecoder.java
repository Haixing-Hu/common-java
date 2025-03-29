/// /////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

/**
 * The interface of decoder which convert strings into lists of objects.
 *
 * @param <T>
 *     the type of the objects to be converted to.
 */
public interface ObjectListDecoder<T> extends ListDecoder<String, T> {
  //  empty
}