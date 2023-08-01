////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import java.io.Serializable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示媒体信息。
 *
 * @author 胡海星
 */
public class MediaInfo implements Serializable, Assignable<MediaInfo> {

  private static final long serialVersionUID = 4281963056262327335L;

  /**
   * 媒体类别。
   */
  private MediaType type;

  /**
   * 文件大小。
   */
  private Long size;

  /**
   * 屏幕尺寸。
   */
  @Size(min = 1, max = 64)
  private String screen;

  /**
   * 持续时长，单位为秒。
   */
  private Long duration;

  public MediaInfo() {
    // empty
  }

  public MediaInfo(final MediaInfo other) {
    assign(other);
  }

  @Override
  public void assign(final MediaInfo other) {
    Argument.requireNonNull("other", other);
    type = other.type;
    size = other.size;
    screen = other.screen;
    duration = other.duration;
  }

  @Override
  public MediaInfo clone() {
    return new MediaInfo(this);
  }

  public MediaType getType() {
    return type;
  }

  public void setType(final MediaType type) {
    this.type = type;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(final Long size) {
    this.size = size;
  }

  public String getScreen() {
    return screen;
  }

  public void setScreen(final String screen) {
    this.screen = screen;
  }

  public Long getDuration() {
    return duration;
  }

  public void setDuration(final Long duration) {
    this.duration = duration;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final MediaInfo other = (MediaInfo) o;
    return Equality.equals(type, other.type)
        && Equality.equals(size, other.size)
        && Equality.equals(screen, other.screen)
        && Equality.equals(duration, other.duration);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, size);
    result = Hash.combine(result, multiplier, screen);
    result = Hash.combine(result, multiplier, duration);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("type", type)
        .append("size", size)
        .append("screen", screen)
        .append("duration", duration)
        .toString();
  }
}
