////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * The model of sizes of images.
 *
 * @author Haixing Hu
 */
public class ImageSize implements Serializable, CloneableEx<ImageSize> {

  private static final long serialVersionUID = -1596759272087911931L;

  public int width;
  public int height;

  public ImageSize() {}

  public ImageSize(final int width, final int height) {
    this.width = width;
    this.height = height;
  }

  public ImageSize(final BufferedImage image) {
    width = image.getWidth();
    height = image.getHeight();
  }

  public ImageSize(final ImageSize size) {
    width = size.width;
    height = size.height;
  }

  public void scaleToWidth(final int expectedWidth) {
    height = (height * expectedWidth) / width;
    width = expectedWidth;
  }

  public void scaleToHeight(final int expectedHeight) {
    width = (width * expectedHeight) / height;
    height = expectedHeight;
  }

  @Override
  public ImageSize cloneEx() {
    return new ImageSize(this);
  }
}
