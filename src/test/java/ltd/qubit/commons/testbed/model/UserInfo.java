////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示用户对象{@link User}的基本信息。
 *
 * @author 胡海星
 */
public class UserInfo implements Identifiable, WithUsername, WithName, Stateful,
    Deletable, Emptyful, Normalizable, Assignable<UserInfo> {

  private static final long serialVersionUID = -3739818849317896899L;

  /**
   * 用户ID。
   */
  @Identifier
  private Long id;

  /**
   * 用户名。
   */
  @Size(min = 1, max = 64)
  private String username;

  /**
   * 真实姓名。
   */
  @Size(min = 1, max = 64)
  @Nullable
  private String name;

  /**
   * 昵称。
   */
  @Size(max = 64)
  @Nullable
  private String nickname;

  /**
   * 头像。
   */
  @Size(max = 512)
  @Nullable
  private String avatar;

  /**
   * 用户状态。
   */
  private State state;

  /**
   * 标记删除时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public static UserInfo create(@Nullable final Long id,
          @Nullable final String username,
          @Nullable final String name,
          @Nullable final String nickname) {
    if (id == null && username == null && name == null && nickname == null) {
      return null;
    } else {
      final UserInfo result = new UserInfo();
      result.setId(id);
      result.setUsername(username);
      result.setName(name);
      result.setNickname(nickname);
      return result;
    }
  }

  public UserInfo() {
    // empty
  }

  public UserInfo(final UserInfo other) {
    assign(other);
  }

  public UserInfo(final User user) {
    assign(user);
  }

  @Override
  public void assign(final UserInfo other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    username = other.username;
    name = other.name;
    nickname = other.nickname;
    avatar = other.avatar;
    state = other.state;
    deleteTime = other.deleteTime;
  }

  public void assign(final User user) {
    Argument.requireNonNull("user", user);
    id = user.getId();
    username = user.getUsername();
    name = user.getName();
    nickname = user.getNickname();
    avatar = user.getAvatar();
    state = user.getState();
  }

  @Override
  public UserInfo cloneEx() {
    return new UserInfo(this);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public void setUsername(final String username) {
    this.username = username;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }

  @Nullable
  public String getNickname() {
    return nickname;
  }

  public void setNickname(@Nullable final String nickname) {
    this.nickname = nickname;
  }

  @Nullable
  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(@Nullable final String avatar) {
    this.avatar = avatar;
  }

  @Override
  public State getState() {
    return state;
  }

  @Override
  public void setState(final State state) {
    this.state = state;
  }

  @Override
  @Nullable
  public Instant getDeleteTime() {
    return deleteTime;
  }

  @Override
  public void setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final UserInfo other = (UserInfo) o;
    return Equality.equals(id, other.id)
        && Equality.equals(username, other.username)
        && Equality.equals(name, other.name)
        && Equality.equals(nickname, other.nickname)
        && Equality.equals(avatar, other.avatar)
        && Equality.equals(state, other.state)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, username);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, nickname);
    result = Hash.combine(result, multiplier, avatar);
    result = Hash.combine(result, multiplier, state);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("username", username)
        .append("name", name)
        .append("nickname", nickname)
        .append("avatar", avatar)
        .append("state", state)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
