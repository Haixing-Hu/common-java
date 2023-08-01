////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import ltd.qubit.commons.lang.Argument;

import java.lang.reflect.Member;

/**
 * Stores the information of member.
 *
 * @author Haixing Hu
 */
public final class MemberInfo implements Comparable<MemberInfo> {

  /**
   * The member.
   */
  public final Member member;

  /**
   * The depth of the member in the specified class.
   */
  public final int depth;

  /**
   * Constructs a {@link MemberInfo}.
   *
   * @param member
   *          a member.
   * @param depth
   *          the depth of the member in the specified class.
   */
  public MemberInfo(final Member member, final int depth) {
    this.member = Argument.requireNonNull("member", member);
    this.depth = depth;
  }

  /**
   * Compares this {@link MemberInfo} with the other one.
   * <p>
   * The function will firstly compare the depth of two {@link MemberInfo}, and
   * the one with shallower depth will be smaller; if the depths of two
   * {@link MemberInfo} are the same, the function then lexicographically
   * compare the names of the members of two {@link MemberInfo}.
   *
   * @param other
   *          the other {@link MemberInfo} object.
   */
  @Override
  public int compareTo(final MemberInfo other) {
    if (other == null) {
      return + 1;
    } else if (depth != other.depth) {
      return depth - other.depth;
    } else {
      final String thisName = member.getName();
      final String otherName = other.member.getName();
      return thisName.compareTo(otherName);
    }
  }
}
