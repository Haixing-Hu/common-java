////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

/**
 * This interface indicates that an entity class has a remark property.
 *
 * @author Haixing Hu
 */
public interface WithComment {

  /**
   * Get the comment for this object.
   *
   * @return
   *     Comments for this object.
   */
  String getComment();

  /**
   * Sets a comment for this object.
   *
   * @param comment
   *     A new comment to be set.
   */
  void setComment(String comment);
}
