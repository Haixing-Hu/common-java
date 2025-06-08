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
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示渠道来源，例如可以表示产品订单的渠道，也可以表示新用户的来源等。
 *
 * @author 胡海星
 */
public class Source implements HasInfo, WithApp, WithEntity, Predefinable,
    Emptyful, Normalizable, Auditable, Assignable<Source> {

  private static final long serialVersionUID = -2153621088317243035L;

  /**
   * 内部唯一标识。
   */
  @Identifier
  private Long id;

  /**
   * 所属 App 的基本信息。
   */
  @Reference(entity = App.class, property = "info")
  private StatefulInfo app;

  /**
   * 所属实体名称。
   */
  @Size(min = 1, max = 64)
  private String entity;

  /**
   * 编码，全局不可重复。
   */
  @Size(min = 1, max = 64)
  @Unique
  private String code;

  /**
   * 名称，同一个App的同一类实体下来源名称不可重复。
   */
  @Size(min = 1, max = 128)
  @Unique(respectTo = {"app", "entity"})
  private String name;

  /**
   * 描述。
   */
  @Nullable
  private String description;

  /**
   * 所属类别的基本信息。
   */
  @Reference(entity = Category.class, property = "info")
  @Nullable
  private InfoWithEntity category;

  /**
   * 供应商的App的基本信息。
   */
  @Reference(entity = App.class, property = "info")
  @Nullable
  private StatefulInfo providerApp;

  /**
   * 供应商机构的基本信息。
   */
  @Reference(entity = Organization.class, property = "info")
  @Nullable
  private StatefulInfo providerOrg;

  /**
   * 是否是预定义的数据。
   */
  private Boolean predefined;

  /**
   * 此对象创建时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant createTime;

  /**
   * 此对象最后一次修改时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant modifyTime;

  /**
   * 此对象标记删除时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public Source() {
    // empty
  }

  public Source(final Source other) {
    assign(other);
  }

  @Override
  public void assign(final Source other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    app = Assignment.clone(other.app);
    entity = other.entity;
    code = other.code;
    name = other.name;
    description = other.description;
    category = Assignment.clone(other.category);
    providerApp = Assignment.clone(other.providerApp);
    providerOrg = Assignment.clone(other.providerOrg);
    predefined = other.predefined;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Source cloneEx() {
    return new Source(this);
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
  public StatefulInfo getApp() {
    return app;
  }

  @Override
  public void setApp(final StatefulInfo app) {
    this.app = app;
  }

  @Override
  public String getEntity() {
    return entity;
  }

  @Override
  public void setEntity(final String entity) {
    this.entity = entity;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public void setCode(final String code) {
    this.code = code;
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
  public String getDescription() {
    return description;
  }

  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  @Nullable
  public InfoWithEntity getCategory() {
    return category;
  }

  public void setCategory(@Nullable final InfoWithEntity category) {
    this.category = category;
  }

  @Nullable
  public StatefulInfo getProviderApp() {
    return providerApp;
  }

  public void setProviderApp(@Nullable final StatefulInfo providerApp) {
    this.providerApp = providerApp;
  }

  @Nullable
  public StatefulInfo getProviderOrg() {
    return providerOrg;
  }

  public void setProviderOrg(@Nullable final StatefulInfo providerOrg) {
    this.providerOrg = providerOrg;
  }

  @Override
  public Boolean isPredefined() {
    return predefined;
  }

  @Override
  public void setPredefined(final Boolean predefined) {
    this.predefined = predefined;
  }

  @Override
  @Nullable
  public Instant getCreateTime() {
    return createTime;
  }

  @Override
  public void setCreateTime(@Nullable final Instant createTime) {
    this.createTime = createTime;
  }

  @Override
  @Nullable
  public Instant getModifyTime() {
    return modifyTime;
  }

  @Override
  public void setModifyTime(@Nullable final Instant modifyTime) {
    this.modifyTime = modifyTime;
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
    final Source other = (Source) o;
    return Equality.equals(id, other.id)
        && Equality.equals(app, other.app)
        && Equality.equals(entity, other.entity)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(description, other.description)
        && Equality.equals(category, other.category)
        && Equality.equals(providerApp, other.providerApp)
        && Equality.equals(providerOrg, other.providerOrg)
        && Equality.equals(predefined, other.predefined)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, entity);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, category);
    result = Hash.combine(result, multiplier, providerApp);
    result = Hash.combine(result, multiplier, providerOrg);
    result = Hash.combine(result, multiplier, predefined);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("app", app)
        .append("entity", entity)
        .append("code", code)
        .append("name", name)
        .append("description", description)
        .append("category", category)
        .append("providerApp", providerApp)
        .append("providerOrg", providerOrg)
        .append("predefined", predefined)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}