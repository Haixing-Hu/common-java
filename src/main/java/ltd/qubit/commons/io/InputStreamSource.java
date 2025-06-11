////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;

import ltd.qubit.commons.io.resource.ByteArrayResource;
import ltd.qubit.commons.io.resource.InputStreamResource;
import ltd.qubit.commons.io.resource.Resource;

/**
 * 作为 {@link InputStream} 源的对象的简单接口。
 * <p>
 * 这是 Spring 更广泛的 {@link Resource} 接口的基础接口。
 * <p>
 * 对于一次性流，{@link InputStreamResource} 可以用于任何给定的 {@code InputStream}。
 * Spring 的 {@link ByteArrayResource} 或任何基于文件的 {@code Resource} 实现都可以用作具体实例，
 * 从而允许多次读取底层内容流。这使得此接口可作为邮件附件等的抽象内容源。
 * <p>
 * 此类是 {@code org.springframework.core.io.InputStreamSource} 的副本，
 * 经过了少量修改。它用于避免对 Spring 框架的依赖。
 *
 * @author Juergen Hoeller
 * @author 胡海星
 * @see InputStream
 */
public interface InputStreamSource {

  /**
   * 返回底层资源内容的 {@link InputStream}。
   * <p>
   * 期望每次调用都创建一个 <i>新的</i> 流。
   * <p>
   * 当您考虑像 JavaMail 这样的 API 时，此要求尤其重要，
   * 在创建邮件附件时，它需要能够多次读取流。对于这样的用例，<i>要求</i>
   * 每次 {@code getInputStream()} 调用都返回一个新的流。
   *
   * @return 底层资源的输入流（不得为 {@code null}）
   * @throws java.io.FileNotFoundException
   *     如果底层资源不存在
   * @throws IOException
   *     如果无法打开内容流
   * @see Resource#isReadable()
   */
  @Nonnull
  InputStream getInputStream() throws IOException;
}