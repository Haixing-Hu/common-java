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

import ltd.qubit.commons.annotation.Computed;
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

import static ltd.qubit.commons.text.CaseFormat.UPPER_CAMEL;
import static ltd.qubit.commons.text.CaseFormat.UPPER_UNDERSCORE;

/**
 * 此模型表示实体的类别。
 *
 * @author 胡海星
 */
public class Category implements HasInfoWithEntity, Predefinable, Auditable,
  Emptyful, Normalizable, Assignable<Category> {

  private static final long serialVersionUID = 8069770708740141238L;

  /**
   * 用于连接类别显示名称的字符串。
   */
  public static final String TITLE_JOINER = " - ";

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 该类别所属实体。
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
   * 名称，同一实体下类别名称不可重复。
   */
  @Size(min = 1, max = 128)
  @Unique(respectTo = "entity")
  private String name;

  /**
   * 图标。
   */
  @Size(min = 1, max = 512)
  @Nullable
  private String icon;

  /**
   * 描述。
   */
  @Nullable
  private String description;

  /**
   * 显示标题。
   *
   * <p>若当前类别没有父类别，则显示标题为当前类别的名称；否则显示标题定义为：
   * <pre>
   * [父类别标题] "-" [当前类别名称]
   * </pre>
   *
   * <p>即用连字符连接父类别标题和当前类别名称，其中父类别标题用同样的方法递归定义。
   *
   * <p>例如，<code>"主营业务收入 - 在线商城 - 会员卡"</code>
   *
   * <p>此字段通过查询构造生成
   */
  @Size(min = 1, max = 4096)
  @Computed(value = {"name", "parent"})
  @Nullable
  private String title;

  /**
   * 父类别基本信息。
   *
   * <p>父类别必须与子类别属于同一个App同一个实体；若不存在父类别则此属性为{@code null}。
   */
  @Reference(entity = Category.class, property = "info")
  @Nullable
  private InfoWithEntity parent;

  /**
   * 是否是预定义的数据。
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

  public Category() {
    // empty
  }

  public Category(final Category other) {
    assign(other);
  }

  @Override
  public void assign(final Category other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    entity = other.entity;
    code = other.code;
    name = other.name;
    icon = other.icon;
    description = other.description;
    title = other.title;
    parent = Assignment.clone(other.parent);
    predefined = other.predefined;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Category cloneEx() {
    return new Category(this);
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
  public String getEntity() {
    return entity;
  }

  @Override
  public void setEntity(final String entity) {
    this.entity = entity;
  }

  @Computed
  public void setEntityClass(final Class<?> entityClass) {
    this.entity = UPPER_CAMEL.to(UPPER_UNDERSCORE, entityClass.getSimpleName());
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
  public String getTitle() {
    return title;
  }

  public void setTitle(@Nullable final String title) {
    this.title = title;
  }

  @Nullable
  public InfoWithEntity getParent() {
    return parent;
  }

  public void setParent(@Nullable final InfoWithEntity parent) {
    this.parent = parent;
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
  public Instant getCreateTime() {
    return createTime;
  }

  @Override
  public void setCreateTime(final Instant createTime) {
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
    final Category other = (Category) o;
    return Equality.equals(id, other.id)
        && Equality.equals(entity, other.entity)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(icon, other.icon)
        && Equality.equals(description, other.description)
        && Equality.equals(title, other.title)
        && Equality.equals(parent, other.parent)
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
    result = Hash.combine(result, multiplier, entity);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, icon);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, title);
    result = Hash.combine(result, multiplier, parent);
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
        .append("entity", entity)
        .append("code", code)
        .append("name", name)
        .append("icon", icon)
        .append("description", description)
        .append("title", title)
        .append("parent", parent)
        .append("predefined", predefined)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
