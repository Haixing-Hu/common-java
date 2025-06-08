////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

import java.util.Locale;

import ltd.qubit.commons.lang.EnumUtils;

import static ltd.qubit.commons.lang.EnumUtils.registerLocalizedNames;

/**
 * 此枚举表示媒体类型。
 *
 * @author 胡海星
 */
public enum MediaType {

  /**
   * 图片。
   */
  IMAGE,

  /**
   * 音乐。
   */
  MUSIC,

  /**
   * 录音。
   */
  RECORD,

  /**
   * 音频。
   */
  AUDIO,

  /**
   * 视频。
   */
  VIDEO;

  static {
    registerLocalizedNames(MediaType.class, "i18n.media-type");
  }

  public String getLocalizedName() {
    return getLocalizedName(Locale.getDefault());
  }

  public String getLocalizedName(final Locale locale) {
    return EnumUtils.getLocalizedName(locale, this);
  }
}