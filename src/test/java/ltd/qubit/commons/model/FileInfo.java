////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import jakarta.validation.constraints.Size;
import ltd.qubit.commons.annotation.Scale;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 此模型表示存储在服务器上的文件的元信息。
 *
 * @author 胡海星
 */
public class FileInfo implements Serializable, Assignable<FileInfo> {

  private static final long serialVersionUID = -2698757688586607397L;

  /**
   * 文件存储位置的URL，可以是绝对地址或相对地址。
   */
  @Size(min = 1, max = 512)
  private String url;

  /**
   * 文件格式。
   */
  @Size(min = 1, max = 128)
  private String format;

  /**
   * 文件的Content-Type。
   */
  @Size(min = 1, max = 128)
  private String contentType;

  /**
   * 文件大小，单位为字节。
   */
  private Long size;

  /**
   * （图像或视频）文件的宽度，单位为像素。
   */
  @Nullable
  private Integer width;

  /**
   * （图像或视频）文件的高度，单位为像素。
   */
  @Nullable
  private Integer height;

  /**
   * 视频或音频文件的长度，单位为秒。
   */
  @Nullable
  private Integer duration;

  /**
   * 图像、视频或音频文件的压缩质量，100%为无损压缩。
   */
  @Nullable
  @Scale(2)
  private BigDecimal quality;

  public FileInfo() {
    // empty
  }

  public FileInfo(final FileInfo other) {
    assign(other);
  }

  @Override
  public void assign(final FileInfo other) {
    Argument.requireNonNull("other", other);
    url = other.url;
    format = other.format;
    contentType = other.contentType;
    size = other.size;
    width = other.width;
    height = other.height;
    duration = other.duration;
    quality = other.quality;
  }

  @Override
  public FileInfo clone() {
    return new FileInfo(this);
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(final String format) {
    this.format = format;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(final String contentType) {
    this.contentType = contentType;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(final Long size) {
    this.size = size;
  }

  @Nullable
  public Integer getWidth() {
    return width;
  }

  public void setWidth(@Nullable final Integer width) {
    this.width = width;
  }

  @Nullable
  public Integer getHeight() {
    return height;
  }

  public void setHeight(@Nullable final Integer height) {
    this.height = height;
  }

  @Nullable
  public Integer getDuration() {
    return duration;
  }

  public void setDuration(@Nullable final Integer duration) {
    this.duration = duration;
  }

  @Nullable
  public BigDecimal getQuality() {
    return quality;
  }

  public void setQuality(@Nullable final BigDecimal quality) {
    this.quality = quality;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final FileInfo other = (FileInfo) o;
    return Equality.equals(url, other.url)
        && Equality.equals(format, other.format)
        && Equality.equals(contentType, other.contentType)
        && Equality.equals(size, other.size)
        && Equality.equals(width, other.width)
        && Equality.equals(height, other.height)
        && Equality.equals(duration, other.duration)
        && Equality.equals(quality, other.quality);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, url);
    result = Hash.combine(result, multiplier, format);
    result = Hash.combine(result, multiplier, contentType);
    result = Hash.combine(result, multiplier, size);
    result = Hash.combine(result, multiplier, width);
    result = Hash.combine(result, multiplier, height);
    result = Hash.combine(result, multiplier, duration);
    result = Hash.combine(result, multiplier, quality);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("url", url)
        .append("format", format)
        .append("contentType", contentType)
        .append("size", size)
        .append("width", width)
        .append("height", height)
        .append("duration", duration)
        .append("quality", quality)
        .toString();
  }
}
