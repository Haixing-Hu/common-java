////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.mapper;

@FunctionalInterface
public interface ColumnHeaderTransformer {

  /**
   * Transforms the specified column header.
   *
   * @param index
   *     the index of the column header to be transformed, starting from 0.
   * @param header
   *     the column header to be transformed.
   * @return
   *     the transformed column header.
   */
  String transform(int index, String header);

}
