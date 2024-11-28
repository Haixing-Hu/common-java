////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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

import ltd.qubit.commons.io.io.OutputUtils;
import ltd.qubit.commons.io.io.error.SerializationException;
import ltd.qubit.commons.io.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.io.serialize.BinarySerializer;
import ltd.qubit.commons.net.Url;
import ltd.qubit.commons.net.UrlBinarySerializer;

import static ltd.qubit.commons.io.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.io.InputUtils.readString;

/**
 * The {@link BinarySerializer} for the {@link HyperLink} class.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class HyperLinkBinarySerializer implements BinarySerializer {

  public static final HyperLinkBinarySerializer INSTANCE = new HyperLinkBinarySerializer();

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
