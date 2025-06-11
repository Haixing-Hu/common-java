////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

/**
 * 可写资源的扩展接口，支持对其进行写操作。
 * 提供一个 {@link #getOutputStream() OutputStream 访问器}。
 * <p>
 * 此类是 {@code org.springframework.core.io.WritableResource} 的一个副本，
 * 经过了少量修改。它的目的是为了避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author 胡海星
 * @see OutputStream
 */
public interface WritableResource extends Resource {

  /**
   * 指示此资源的内容是否可以通过 {@link #getOutputStream()} 写入。
   * <p>
   * 对于典型的资源描述符，这将是 {@code true}；请注意，实际的内容写入在尝试时仍可能失败。
   * 但是，值为 {@code false} 则明确表示无法修改资源内容。
   *
   * @see #getOutputStream()
   * @see #isReadable()
   */
  default boolean isWritable() {
    return true;
  }

  /**
   * 返回底层资源的 {@link OutputStream}，允许（覆盖）写入其内容。
   *
   * @throws IOException
   *     如果无法打开流
   * @see #getInputStream()
   */
  OutputStream getOutputStream() throws IOException;

  /**
   * 返回一个 {@link WritableByteChannel}。
   * <p>
   * 期望每次调用都创建一个 <i>新的</i> 通道。
   * <p>
   * 默认实现使用 {@link #getOutputStream()} 的结果返回
   * {@link Channels#newChannel(OutputStream)}。
   *
   * @return 底层资源的字节通道（不能为 {@code null}）
   * @throws FileNotFoundException
   *     如果底层资源不存在
   * @throws IOException
   *     如果无法打开内容通道
   * @see #getOutputStream()
   * @since 5.0
   */
  default WritableByteChannel writableChannel() throws IOException {
    return Channels.newChannel(getOutputStream());
  }
}