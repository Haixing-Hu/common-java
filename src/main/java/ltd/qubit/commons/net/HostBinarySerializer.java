////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.OutputUtils;
import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.io.error.UnexpectedNullValueException;
import ltd.qubit.commons.io.serialize.BinarySerializer;

import static ltd.qubit.commons.io.InputUtils.readInt;
import static ltd.qubit.commons.io.InputUtils.readNullMark;
import static ltd.qubit.commons.io.InputUtils.readString;

/**
 * {@link Host} 类的{@link BinarySerializer}。
 *
 * @author 胡海星
 */
@Immutable
public final class HostBinarySerializer implements BinarySerializer {

  public static final HostBinarySerializer INSTANCE = new HostBinarySerializer();

  /**
   * 从二进制输入流反序列化对象。
   *
   * @param in
   *     二进制输入流。
   * @param allowNull
   *     指示是否允许返回的对象为 {@code null}。
   * @return
   *     从二进制输入流反序列化的对象；如果二进制输入流中存储的对象为 null
   *     并且参数 {@code allowNull} 为 true，则返回 {@code null}。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  @Override
  public Host deserialize(final InputStream in, final boolean allowNull)
      throws IOException {
    if (readNullMark(in)) {
      if (allowNull) {
        return null;
      } else {
        throw new UnexpectedNullValueException();
      }
    } else {
      final String scheme = readString(in, false);
      final String hostname = readString(in, false);
      final int port = readInt(in);
      return new Host(scheme, hostname, port);
    }
  }

  /**
   * 将对象序列化到二进制输出流。
   *
   * @param out
   *     二进制输出流。
   * @param obj
   *     要序列化的对象，可以为 {@code null}。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  @Override
  public void serialize(final OutputStream out, final Object obj)
      throws IOException {
    if (! OutputUtils.writeNullMark(out, obj)) {
      final Host host;
      try {
        host = (Host) obj;
      } catch (final ClassCastException e) {
        throw new SerializationException(e);
      }
      OutputUtils.writeString(out, host.scheme());
      OutputUtils.writeString(out, host.hostname());
      OutputUtils.writeInt(out, host.port());
    }
  }
}