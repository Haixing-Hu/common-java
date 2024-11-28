////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 此模型表示各种证件、执照等。
 *
 * @author 胡海星
 */
public class Credential implements Identifiable, WithOwner, Auditable, Emptyful,
    Normalizable, Assignable<Credential> {

  private static final long serialVersionUID = 3943505292507133852L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 证件类型。
   *
   * <p>对于个人，证件类型通常是身份证，社保卡等；
   * 对于机构，证件类型通常是营业执照、组织机构代码证等。
   */
  private CredentialType type;

  /**
   * 证件号码。
   *
   * <p>对于个人，证件号码通常是身份证号码，社保卡号码等；
   * 对于机构，证件号码通常是营业执照编号、组织机构代码等。
   */
  @Size(min = 1, max = 128)
  private String number;

  /**
   * 证件的验证状态。
   */
  @Nullable
  private VerifyState verified;

  /**
   * 该证件的所有者。
   */
  private Owner owner;

  /**
   * 该证件在所有者指定属性的证件列表中的索引。
   */
  @Unique(respectTo = "owner")
  private Integer index = 0;

  /**
   * 标题。
   */
  @Size(max = 512)
  @Nullable
  private String title;

  /**
   * 描述。
   */
  @Nullable
  private String description;

  /**
   * 证件的附件列表。
   */
  @Size(min = 1, max = 16)
  @Reference(entity = Attachment.class, existing = false)
  @Nullable
  private List<Attachment> attachments;

  /**
   * 创建时间，存储UTC时间戳。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 最后一次修改时间，存储UTC时间戳。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant modifyTime;

  /**
   * 标记删除时间，存储UTC时间戳。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  /**
   * 创建一个证件。
   *
   * @param type
   *     证件类型，可以为{@code null}。
   * @param number
   *     证件号码，可以为{@code null}。
   * @return 若{@code type}和{@code number}不全为{@code null}，则返回一个指定类型和
   *     号码的证件对象，否则返回{@code null}.
   */
  public static Credential create(@Nullable final CredentialType type,
      @Nullable final String number) {
    if (type == null && number == null) {
      return null;
    } else {
      return new Credential(type, number);
    }
  }

  public Credential() {
    // empty
  }

  public Credential(final CredentialType type, final String number) {
    this.type = requireNonNull("type", type);
    this.number = requireNonNull("number", number);
  }

  public Credential(final CredentialInfo info) {
    requireNonNull("info", info);
    id = info.getId();
    type = info.getType();
    number = info.getNumber();
    verified = info.getVerified();
  }

  public Credential(final Credential other) {
    assign(other);
  }

  @Override
  public void assign(final Credential other) {
    requireNonNull("other", other);
    id = other.id;
    type = other.type;
    number = other.number;
    verified = other.verified;
    owner = Assignment.clone(other.owner);
    index = other.index;
    title = other.title;
    description = other.description;
    attachments = Assignment.deepClone(other.attachments);
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  public void assign(final CredentialInfo info) {
    requireNonNull("other", info);
    id = info.getId();
    type = info.getType();
    number = info.getNumber();
    verified = info.getVerified();
  }

  @Override
  public Credential cloneEx() {
    return new Credential(this);
  }

  @Computed({"id", "type", "number", "verified"})
  public CredentialInfo getInfo() {
    return new CredentialInfo(this);
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public CredentialType getType() {
    return type;
  }

  public void setType(final CredentialType type) {
    this.type = type;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(final String number) {
    this.number = number;
  }

  @Nullable
  public VerifyState getVerified() {
    return verified;
  }

  public void setVerified(@Nullable final VerifyState verified) {
    this.verified = verified;
  }

  @Override
  public Owner getOwner() {
    return owner;
  }

  @Override
  public void setOwner(final Owner owner) {
    this.owner = owner;
  }

  public Integer getIndex() {
    return index;
  }

  public void setIndex(final Integer index) {
    this.index = index;
  }

  @Nullable
  public String getTitle() {
    return title;
  }

  public void setTitle(@Nullable final String title) {
    this.title = title;
  }

  @Nullable
  public String getDescription() {
    return description;
  }

  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  @Nullable
  public List<Attachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(@Nullable final List<Attachment> attachments) {
    this.attachments = attachments;
  }

  public Instant getCreateTime() {
    return createTime;
  }

  public void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
  }

  @Nullable
  public Instant getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(@Nullable final Instant modifyTime) {
    this.modifyTime = modifyTime;
  }

  @Nullable
  public Instant getDeleteTime() {
    return deleteTime;
  }

  public void setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
  }

  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Credential other = (Credential) o;
    return Equality.equals(id, other.id)
        && Equality.equals(type, other.type)
        && Equality.equals(number, other.number)
        && Equality.equals(verified, other.verified)
        && Equality.equals(owner, other.owner)
        && Equality.equals(index, other.index)
        && Equality.equals(title, other.title)
        && Equality.equals(description, other.description)
        && Equality.equals(attachments, other.attachments)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, number);
    result = Hash.combine(result, multiplier, verified);
    result = Hash.combine(result, multiplier, owner);
    result = Hash.combine(result, multiplier, index);
    result = Hash.combine(result, multiplier, title);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, attachments);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("type", type)
        .append("number", number)
        .append("verified", verified)
        .append("owner", owner)
        .append("index", index)
        .append("title", title)
        .append("description", description)
        .append("attachments", attachments)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }

  @Override
  public void normalize() {
    if (attachments != null && attachments.isEmpty()) {
      attachments = null;
    }
  }
}
