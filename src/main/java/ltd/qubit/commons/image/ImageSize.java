////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.image;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import ltd.qubit.commons.lang.CloneableEx;

/**
 * 图像尺寸的模型。
 *
 * @author 胡海星
 */
public class ImageSize implements Serializable, CloneableEx<ImageSize> {

  private static final long serialVersionUID = -1596759272087911931L;

  public int width;
  public int height;

  /**
   * 构造一个默认的图像尺寸对象。
   */
  public ImageSize() {}

  /**
   * 构造一个指定宽度和高度的图像尺寸对象。
   *
   * @param width
   *     图像的宽度
   * @param height
   *     图像的高度
   */
  public ImageSize(final int width, final int height) {
    this.width = width;
    this.height = height;
  }

  /**
   * 根据 BufferedImage 构造图像尺寸对象。
   *
   * @param image
   *     BufferedImage 对象
   */
  public ImageSize(final BufferedImage image) {
    width = image.getWidth();
    height = image.getHeight();
  }

  /**
   * 复制构造函数。
   *
   * @param size
   *     要复制的图像尺寸对象
   */
  public ImageSize(final ImageSize size) {
    width = size.width;
    height = size.height;
  }

  /**
   * 按指定宽度缩放图像，高度按比例调整。
   *
   * @param expectedWidth
   *     期望的宽度
   */
  public void scaleToWidth(final int expectedWidth) {
    height = (height * expectedWidth) / width;
    width = expectedWidth;
  }

  /**
   * 按指定高度缩放图像，宽度按比例调整。
   *
   * @param expectedHeight
   *     期望的高度
   */
  public void scaleToHeight(final int expectedHeight) {
    width = (width * expectedHeight) / height;
    height = expectedHeight;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ImageSize cloneEx() {
    return new ImageSize(this);
  }
}