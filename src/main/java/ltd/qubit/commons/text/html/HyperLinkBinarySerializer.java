////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.html;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.io.OutputUtils;
import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.serialize.BinarySerializer;
import ltd.qubit.commons.net.Url;
import ltd.qubit.commons.net.UrlBinarySerializer;

import static ltd.qubit.commons.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.InputUtils.readString;

/**
 * {@link HyperLink} 类的 {@link BinarySerializer}。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class HyperLinkBinarySerializer implements BinarySerializer {

  /**
   * 单例实例。
   */
  public static final HyperLinkBinarySerializer INSTANCE = new HyperLinkBinarySerializer();

  /**
   * {@inheritDoc}
   */
  @Override
  public HyperLink deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    } else {
      final Url url = UrlBinarySerializer.INSTANCE.deserialize(in, true);
      final String anchor = readString(in, true);
      return new HyperLink(url, anchor);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(final OutputStream out, final Object obj)
      throws IOException {
    if (! OutputUtils.writeNullMark(out, obj)) {
      final HyperLink link;
      try {
        link = (HyperLink) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      UrlBinarySerializer.INSTANCE.serialize(out, link.url());
      OutputUtils.writeString(out, link.anchor());
    }
  }
}