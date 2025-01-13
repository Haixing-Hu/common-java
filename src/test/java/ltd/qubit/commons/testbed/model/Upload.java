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

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示一个上传的文件的元信息。
 *
 * @author 胡海星
 */
public class Upload implements Identifiable, Creatable, Deletable,
    Assignable<Upload> {

  private static final long serialVersionUID = -1521818404700070671L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 附件类型。
   */
  private AttachmentType type;

  /**
   * 原始文件在服务器上的存储信息。
   */
  private FileInfo file;

  /**
   * 视频文件的截屏在服务器上的存储信息。
   */
  @Nullable
  private FileInfo screenshot;

  /**
   * 图像/视频文件的小号缩略图在服务器上的存储信息。
   */
  @Nullable
  private FileInfo smallThumbnail;

  /**
   * 图像/视频文件的大号缩略图在服务器上的存储信息。
   */
  @Nullable
  private FileInfo largeThumbnail;

  /**
   * 创建时间。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 标记删除时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public Upload() {
    // empty
  }

  public Upload(final Upload other) {
    assign(other);
  }

  @Override
  public void assign(final Upload other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    type = other.type;
    file = Assignment.clone(other.file);
    screenshot = Assignment.clone(other.screenshot);
    smallThumbnail = Assignment.clone(other.smallThumbnail);
    largeThumbnail = Assignment.clone(other.largeThumbnail);
    createTime = other.createTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Upload cloneEx() {
    return new Upload(this);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  public AttachmentType getType() {
    return type;
  }

  public void setType(final AttachmentType type) {
    this.type = type;
  }

  public FileInfo getFile() {
    return file;
  }

  public void setFile(final FileInfo file) {
    this.file = file;
  }

  @Nullable
  public FileInfo getScreenshot() {
    return screenshot;
  }

  public void setScreenshot(@Nullable final FileInfo screenshot) {
    this.screenshot = screenshot;
  }

  @Nullable
  public FileInfo getSmallThumbnail() {
    return smallThumbnail;
  }

  public void setSmallThumbnail(@Nullable final FileInfo smallThumbnail) {
    this.smallThumbnail = smallThumbnail;
  }

  @Nullable
  public FileInfo getLargeThumbnail() {
    return largeThumbnail;
  }

  public void setLargeThumbnail(@Nullable final FileInfo largeThumbnail) {
    this.largeThumbnail = largeThumbnail;
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
  public Instant getDeleteTime() {
    return deleteTime;
  }

  @Override
  public void setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
  }

  @Override
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Upload other = (Upload) o;
    return Equality.equals(id, other.id)
        && Equality.equals(type, other.type)
        && Equality.equals(file, other.file)
        && Equality.equals(screenshot, other.screenshot)
        && Equality.equals(smallThumbnail, other.smallThumbnail)
        && Equality.equals(largeThumbnail, other.largeThumbnail)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, file);
    result = Hash.combine(result, multiplier, screenshot);
    result = Hash.combine(result, multiplier, smallThumbnail);
    result = Hash.combine(result, multiplier, largeThumbnail);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("type", type)
        .append("file", file)
        .append("screenshot", screenshot)
        .append("smallThumbnail", smallThumbnail)
        .append("largeThumbnail", largeThumbnail)
        .append("createTime", createTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
