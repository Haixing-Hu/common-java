////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

import ltd.qubit.commons.error.UnsupportedContentTypeException;
import ltd.qubit.commons.lang.EnumUtils;

import java.util.Locale;

import static ltd.qubit.commons.lang.EnumUtils.registerLocalizedNames;

/**
 * 此枚举表示附件类型。
 *
 * @author Haixing Hu
 */
public enum AttachmentType {

  /**
   * 图像。
   */
  IMAGE,

  /**
   * 文档。
   */
  DOCUMENT,

  /**
   * 音频。
   */
  AUDIO,

  /**
   * 视频。
   */
  VIDEO,

  /**
   * VCARD 名片。
   */
  VCARD,

  /**
   * 地理位置。
   */
  LOCATION,

  /**
   * 外部图像。
   */
  OUTSIDE_IMAGE,

  /**
   * 外部音频。
   */
  OUTSIDE_AUDIO,

  /**
   * 外部视频。
   */
  OUTSIDE_VIDEO;

  public String id() {
    return name().toLowerCase();
  }

  /**
   * 根据Content-Type获取对应的附件类型。
   *
   * @param contentType
   *     指定的Content-Type，例如"image/jpeg"，"video/mp4"等。
   * @return 该Content-Type对应的附件类型。
   */
  public static AttachmentType forContentType(final String contentType) {
    if (contentType.startsWith(IMAGE.id() + "/")) {
      return IMAGE;
    } else if (contentType.startsWith(AUDIO.id() + "/")) {
      return AUDIO;
    } else if (contentType.startsWith(VIDEO.id() + "/")) {
      return VIDEO;
    } else if (contentType.equals("text/x-vcard")) {
      return VCARD;
    } else {
      throw new UnsupportedContentTypeException(contentType);
    }
  }

  static {
    registerLocalizedNames(AttachmentType.class, "i18n/attachment-type");
  }

  public String getLocalizedName() {
    return getLocalizedName(Locale.getDefault());
  }

  public String getLocalizedName(final Locale locale) {
    return EnumUtils.getLocalizedName(locale, this);
  }
}
