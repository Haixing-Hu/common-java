////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * {@link BinarySerializer} 提供将对象序列化和反序列化到二进制流和从二进制流反序列化的接口。
 *
 * <p><b>注意</b>：此接口的实现<b>必须</b>是线程安全的。
 *
 * @author 胡海星
 */
@ThreadSafe
public interface BinarySerializer {

  /**
   * 从二进制输入流中反序列化对象。
   *
   * @param in
   *     二进制输入流。
   * @param allowNull
   *     指示是否允许返回的对象为 {@code null}。
   * @return 
   *    从二进制输入流反序列化的对象，如果存储在二进制输入流中的对象为 null 且参数 
   *    {@code allowNull} 为 true，则返回 {@code null}。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  Object deserialize(InputStream in, boolean allowNull)
      throws IOException;

  /**
   * 将对象序列化到二进制输出流中。
   *
   * @param out
   *     二进制输出流。
   * @param obj
   *     要序列化的对象。它可以为 {@code null}。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  void serialize(OutputStream out, @Nullable Object obj)
      throws IOException;

}