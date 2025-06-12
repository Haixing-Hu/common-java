////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.lang.reflect.Member;

import ltd.qubit.commons.lang.Argument;

/**
 * 存储成员的信息。
 *
 * @author 胡海星
 */
public final class MemberInfo implements Comparable<MemberInfo> {

  /**
   * 成员。
   */
  public final Member member;

  /**
   * 成员在指定类中的深度。
   */
  public final int depth;

  /**
   * 构造一个 {@link MemberInfo}。
   *
   * @param member
   *          一个成员。
   * @param depth
   *          成员在指定类中的深度。
   */
  public MemberInfo(final Member member, final int depth) {
    this.member = Argument.requireNonNull("member", member);
    this.depth = depth;
  }

  /**
   * 将此 {@link MemberInfo} 与另一个进行比较。
   * <p>
   * 此函数首先比较两个 {@link MemberInfo} 的深度，深度较浅的会被认为更小；
   * 如果两个 {@link MemberInfo} 的深度相同，则函数会按字典序比较两个
   * {@link MemberInfo} 的成员名称。
   *
   * @param other
   *          另一个 {@link MemberInfo} 对象。
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