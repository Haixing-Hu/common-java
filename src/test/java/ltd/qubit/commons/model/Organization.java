////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示组织机构，可用于表示公司、医院、药店、学校、政府机构等。
 *
 * <p>参考标准：《WS 445.17-2014 电子病历基本数据集 第17部分：医疗机构信息》
 *
 * @author 胡海星
 */
public class Organization implements HasStatefulInfo, WithCategory, WithContact,
  WithComment, Predefinable, Auditable, Emptyful, Normalizable,
  Assignable<Organization> {

  private static final long serialVersionUID = 5527359674601589225L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 编码，全局唯一。
   */
  @Size(min = 1, max = 64)
  @Unique
  private String code;

  /**
   * 名称，全局唯一。
   */
  @Size(min = 1, max = 128)
  @Unique
  private String name;

  /**
   * 所属类别基本信息。
   */
  @Reference(entity = Category.class, property = "info")
  @Nullable
  private InfoWithEntity category;

  /**
   * 上级机构的基本信息，若没有则为{@code null}。
   */
  @Reference(entity = Organization.class, property = "info")
  @Nullable
  private StatefulInfo parent;

  /**
   * 状态。
   */
  private State state = State.NORMAL;

  /**
   * 图标，存储相对路径或者URL。
   */
  @Size(max = 512)
  @Nullable
  private String icon;

  /**
   * 描述。
   */
  @Nullable
  private String description;

  /**
   * 备注。
   *
   * <p>备注(comment)是由系统管理员填写，描述(description)可以由用户自己填写。</p>
   */
  @Nullable
  private String comment;

  /**
   * 联系方式。
   */
  @Reference
  @Nullable
  private Contact contact;

  /**
   * 机构的身份证明证件，例如营业执照、组织机构代码证等。
   */
  @Reference(entity = Credential.class, property = "info", existing = false)
  @Nullable
  private CredentialInfo credential;

  /**
   * 其他资质证件列表，包含执业许可证、生产经营许可证等。
   */
  @Reference(entity = Credential.class, property = "info", existing = false)
  @Nullable
  private List<CredentialInfo> licenses;

  /**
   * 机构的法人或负责人信息。
   */
  @Nullable
  private PersonInfo principal;

  /**
   * 机构的纳税人类型。
   */
  @Nullable
  private TaxPayerType taxPayerType;

  /**
   * 机构的纳税号。
   */
  @Size(min = 1, max = 64)
  @Nullable
  private String taxNumber;

  /**
   * 额外参数
   */
  @Size(min = 1, max = 10)
  @Reference(entity = Payload.class, existing = false)
  @Nullable
  private List<Payload> payloads;

  /**
   * 是否是预定义的数据
   */
  private Boolean predefined;

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

  public Organization() {
    // empty
  }

  public Organization(final Organization other) {
    assign(other);
  }

  @Override
  public void assign(final Organization other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    code = other.code;
    name = other.name;
    category = Assignment.clone(other.category);
    parent = Assignment.clone(other.parent);
    state = other.state;
    icon = other.icon;
    description = other.description;
    comment = other.comment;
    contact = Assignment.clone(other.contact);
    credential = Assignment.clone(other.credential);
    licenses = Assignment.deepClone(other.licenses);
    principal = Assignment.clone(other.principal);
    taxPayerType = other.taxPayerType;
    taxNumber = other.taxNumber;
    payloads = Assignment.cloneList(other.payloads);
    predefined = other.predefined;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Organization clone() {
    return new Organization(this);
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Nullable
  public InfoWithEntity getCategory() {
    return category;
  }

  public void setCategory(@Nullable final InfoWithEntity category) {
    this.category = category;
  }

  @Nullable
  public StatefulInfo getParent() {
    return parent;
  }

  public void setParent(@Nullable final StatefulInfo parent) {
    this.parent = parent;
  }

  public State getState() {
    return state;
  }

  public void setState(final State state) {
    this.state = state;
  }

  @Nullable
  public String getIcon() {
    return icon;
  }

  public void setIcon(@Nullable final String icon) {
    this.icon = icon;
  }

  @Nullable
  public String getDescription() {
    return description;
  }

  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  @Nullable
  public String getComment() {
    return comment;
  }

  public void setComment(@Nullable final String comment) {
    this.comment = comment;
  }

  @Nullable
  public Contact getContact() {
    return contact;
  }

  public void setContact(@Nullable final Contact contact) {
    this.contact = contact;
  }

  @Nullable
  public CredentialInfo getCredential() {
    return credential;
  }

  public void setCredential(@Nullable final CredentialInfo credential) {
    this.credential = credential;
  }

  @Nullable
  public List<CredentialInfo> getLicenses() {
    return licenses;
  }

  public void setLicenses(@Nullable final List<CredentialInfo> licenses) {
    this.licenses = licenses;
  }

  @Nullable
  public PersonInfo getPrincipal() {
    return principal;
  }

  public void setPrincipal(@Nullable final PersonInfo principal) {
    this.principal = principal;
  }

  @Nullable
  public TaxPayerType getTaxPayerType() {
    return taxPayerType;
  }

  public void setTaxPayerType(@Nullable final TaxPayerType taxPayerType) {
    this.taxPayerType = taxPayerType;
  }

  @Nullable
  public String getTaxNumber() {
    return taxNumber;
  }

  public void setTaxNumber(@Nullable final String taxNumber) {
    this.taxNumber = taxNumber;
  }

  @Nullable
  public List<Payload> getPayloads() {
    return payloads;
  }

  public void setPayloads(@Nullable final List<Payload> payloads) {
    this.payloads = payloads;
  }

  public Boolean isPredefined() {
    return predefined;
  }

  public void setPredefined(final Boolean predefined) {
    this.predefined = predefined;
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

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Organization other = (Organization) o;
    return Equality.equals(id, other.id)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(category, other.category)
        && Equality.equals(parent, other.parent)
        && Equality.equals(state, other.state)
        && Equality.equals(icon, other.icon)
        && Equality.equals(description, other.description)
        && Equality.equals(comment, other.comment)
        && Equality.equals(contact, other.contact)
        && Equality.equals(credential, other.credential)
        && Equality.equals(licenses, other.licenses)
        && Equality.equals(principal, other.principal)
        && Equality.equals(taxPayerType, other.taxPayerType)
        && Equality.equals(taxNumber, other.taxNumber)
        && Equality.equals(payloads, other.payloads)
        && Equality.equals(predefined, other.predefined)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, category);
    result = Hash.combine(result, multiplier, parent);
    result = Hash.combine(result, multiplier, state);
    result = Hash.combine(result, multiplier, icon);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, comment);
    result = Hash.combine(result, multiplier, contact);
    result = Hash.combine(result, multiplier, credential);
    result = Hash.combine(result, multiplier, licenses);
    result = Hash.combine(result, multiplier, principal);
    result = Hash.combine(result, multiplier, taxPayerType);
    result = Hash.combine(result, multiplier, taxNumber);
    result = Hash.combine(result, multiplier, payloads);
    result = Hash.combine(result, multiplier, predefined);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("code", code)
        .append("name", name)
        .append("category", category)
        .append("parent", parent)
        .append("state", state)
        .append("icon", icon)
        .append("description", description)
        .append("comment", comment)
        .append("contact", contact)
        .append("credential", credential)
        .append("licenses", licenses)
        .append("principal", principal)
        .append("taxPayerType", taxPayerType)
        .append("taxNumber", taxNumber)
        .append("payloads", payloads)
        .append("predefined", predefined)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
