////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.testbed;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Indexed;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示数据字典。
 *
 * @author 胡海星
 * @see DictEntry
 */
public class Dict implements WithStatefulInfo, WithApp, WithCategory, WithComment,
    Predefinable, Emptyful, Normalizable, Auditable, Assignable<Dict> {

  private static final long serialVersionUID = 6810466251438620015L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 代码，全局不可重复，一旦设置不能更改。
   */
  @Size(min = 1, max = 64)
  @Unique
  private String code;

  /**
   * 名称。
   */
  @Size(min = 1, max = 128)
  @Indexed
  private String name;

  /**
   * 所遵循的标准规范。
   */
  @Size(min = 1, max = 128)
  @Indexed
  @Nullable
  private String standardDoc;

  /**
   * 在所遵循的标准规范中的编码。
   */
  @Size(min = 1, max = 64)
  @Indexed
  @Nullable
  private String standardCode;

  /**
   * 网址 URL。
   */
  @Size(min = 1, max = 512)
  @Nullable
  private String url;

  /**
   * 详细描述。
   */
  @Nullable
  private String description;

  /**
   * 备注。
   */
  @Nullable
  private String comment;

  /**
   * 所属App的基本信息。
   */
  // @Reference(entity = App.class, property = "info")
  @Nullable
  private StatefulInfo app;

  /**
   * 所属类别的基本信息。
   */
  // @Reference(entity = Category.class, property = "info")
  @Nullable
  private InfoWithEntity category;

  /**
   * 状态。
   */
  @Indexed
  private State state = State.NORMAL;

  /**
   * 是否是预定义的数据。
   */
  @Indexed
  private Boolean predefined;

  /**
   * 创建时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Indexed
  private Instant createTime;

  /**
   * 最后一次修改时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Indexed
  @Nullable
  private Instant modifyTime;

  /**
   * 标记删除时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Indexed
  @Nullable
  private Instant deleteTime;

  public Dict() {
    // empty
  }

  public Dict(final Dict other) {
    assign(other);
  }

  @Override
  public void assign(final Dict other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    code = other.code;
    name = other.name;
    standardDoc = other.standardDoc;
    standardCode = other.standardCode;
    url = other.url;
    description = other.description;
    comment = other.comment;
    app = Assignment.clone(other.app);
    category = Assignment.clone(other.category);
    state = other.state;
    predefined = other.predefined;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Dict cloneEx() {
    return new Dict(this);
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
  public String getStandardDoc() {
    return standardDoc;
  }

  public void setStandardDoc(@Nullable final String standardDoc) {
    this.standardDoc = standardDoc;
  }

  @Nullable
  public String getStandardCode() {
    return standardCode;
  }

  public void setStandardCode(@Nullable final String standardCode) {
    this.standardCode = standardCode;
  }

  @Nullable
  public String getUrl() {
    return url;
  }

  public void setUrl(@Nullable final String url) {
    this.url = url;
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
  public StatefulInfo getApp() {
    return app;
  }

  public void setApp(@Nullable final StatefulInfo app) {
    this.app = app;
  }

  @Nullable
  public InfoWithEntity getCategory() {
    return category;
  }

  public void setCategory(@Nullable final InfoWithEntity category) {
    this.category = category;
  }

  public State getState() {
    return state;
  }

  public void setState(final State state) {
    this.state = state;
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
    final Dict other = (Dict) o;
    return Equality.equals(id, other.id)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(standardDoc, other.standardDoc)
        && Equality.equals(standardCode, other.standardCode)
        && Equality.equals(url, other.url)
        && Equality.equals(description, other.description)
        && Equality.equals(comment, other.comment)
        && Equality.equals(app, other.app)
        && Equality.equals(category, other.category)
        && Equality.equals(state, other.state)
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
    result = Hash.combine(result, multiplier, standardDoc);
    result = Hash.combine(result, multiplier, standardCode);
    result = Hash.combine(result, multiplier, url);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, comment);
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, category);
    result = Hash.combine(result, multiplier, state);
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
        .append("standardDoc", standardDoc)
        .append("standardCode", standardCode)
        .append("url", url)
        .append("description", description)
        .append("comment", comment)
        .append("app", app)
        .append("category", category)
        .append("state", state)
        .append("predefined", predefined)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}