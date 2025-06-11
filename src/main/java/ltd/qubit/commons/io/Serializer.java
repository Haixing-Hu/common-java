////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;

import ltd.qubit.commons.io.error.SerializationException;
import ltd.qubit.commons.lang.Argument;

/**
 * 此类用于辅助序列化过程，并执行基于序列化的附加功能。
 *
 * <ul>
 * <li>使用序列化深度克隆对象</li>
 * <li>管理序列化过程中的 finally 和 IOException</li>
 * <li>管理反序列化过程中的 finally 和 IOException</li>
 * </ul>
 *
 * <p>此类的函数会对无效的{@code null}输入抛出异常。每个方法都更详细地记录了其行为。
 *
 * @author 胡海星
 */
public final class Serializer {

  private Serializer() {}

  /**
   * 将一个{@code Object}序列化到指定的输出流。
   *
   * <p>一旦对象被写入，流将被关闭。这避免了在应用程序代码中需要 finally 子句，也可能避免了异常处理。
   *
   * <p>传入的流在此方法内部没有被缓冲。如果需要，这是您的应用程序的责任。
   *
   * @param object
   *     要序列化为字节的对象，可以为 null。
   * @param output
   *     要写入的流，不能为 null。
   * @throws NullPointerException
   *     如果 {@code object} 或 {@code output} 是 {@code null}。
   * @throws SerializationException
   *     如果序列化失败。
   */
  public static void serialize(final Serializable object,
      final OutputStream output) throws SerializationException {
    Argument.requireNonNull("object", object);
    Argument.requireNonNull("output", output);
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(output);
      oos.writeObject(object);
    } catch (final IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(oos);
      IoUtils.closeQuietly(output);
    }
  }

  /**
   * 将一个{@code Object}序列化到一个文件用于存储。
   *
   * @param object
   *     要序列化为字节的对象。
   * @param file
   *     存储对象序列化内容的文件的抽象路径名。
   * @throws NullPointerException
   *     如果 {@code object} 或 {@code file} 是 {@code null}。
   * @throws SerializationException
   *     如果序列化失败。
   */
  public static void serialize(final Serializable object, final File file)
      throws SerializationException {
    Argument.requireNonNull("object", object);
    Argument.requireNonNull("file", file);
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    ObjectOutputStream oos = null;
    try {
      FileUtils.ensureParentExist(file);
      fos = new FileOutputStream(file);
      bos = new BufferedOutputStream(fos);
      oos = new ObjectOutputStream(bos);
      oos.writeObject(object);
    } catch (final IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(oos);
      IoUtils.closeQuietly(bos);
      IoUtils.closeQuietly(fos);
    }
  }

  /**
   * 将一个{@code Object}序列化到一个字节数组用于存储。
   *
   * @param object
   *     要序列化为字节的对象。
   * @return 转换后的可序列化对象的字节数组。
   * @throws NullPointerException
   *     如果 {@code object} 是 {@code null}。
   * @throws SerializationException
   *     如果序列化失败。
   */
  public static byte[] serialize(final Serializable object)
      throws SerializationException {
    Argument.requireNonNull("object", object);
    final ByteArrayOutputStream output = new ByteArrayOutputStream();
    ObjectOutputStream oos = null;
    try {
      oos = new ObjectOutputStream(output);
      oos.writeObject(object);
    } catch (final IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(oos);
      IoUtils.closeQuietly(output);
    }
    return output.toByteArray();
  }

  /**
   * 从指定的输入流反序列化一个{@code Object}。
   *
   * <p>一旦对象被读取，流将被关闭。这避免了在应用程序代码中需要 finally 子句，也可能避免了异常处理。
   *
   * <p>传入的流在此方法内部没有被缓冲。如果需要，这是您的应用程序的责任。
   *
   * @param input
   *     序列化对象的输入流，不能为 null。
   * @return 反序列化后的对象。
   * @throws NullPointerException
   *     如果 {@code inputStream} 是 {@code null}。
   * @throws SerializationException
   *     如果反序列化失败。
   */
  public static Object deserialize(final InputStream input)
      throws SerializationException {
    Argument.requireNonNull("input", input);
    ObjectInputStream ois = null;
    try {
      // stream closed in the finally
      ois = new ObjectInputStream(input);
      return ois.readObject();
    } catch (final ClassNotFoundException | IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(ois);
      IoUtils.closeQuietly(input);
    }
  }

  /**
   * 从文件中反序列化单个{@code Object}。
   *
   * @param file
   *     文件的抽象路径名。
   * @return 反序列化后的对象。
   * @throws NullPointerException
   *     如果 {@code file} 是 {@code null}。
   * @throws SerializationException
   *     如果反序列化失败。
   */
  public static Object deserialize(final File file)
      throws SerializationException {
    Argument.requireNonNull("file", file);
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    ObjectInputStream ois = null;
    try {
      fis = new FileInputStream(file);
      bis = new BufferedInputStream(fis);
      ois = new ObjectInputStream(bis);
      return ois.readObject();
    } catch (final ClassNotFoundException | IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(ois);
      IoUtils.closeQuietly(bis);
      IoUtils.closeQuietly(fis);
    }
  }

  /**
   * 从URL反序列化单个{@code Object}。
   *
   * @param url
   *     一个 URL。
   * @return 反序列化后的对象。
   * @throws NullPointerException
   *     如果 {@code url} 是 {@code null}。
   * @throws SerializationException
   *     如果反序列化失败。
   */
  public static Object deserialize(final URL url)
      throws SerializationException {
    Argument.requireNonNull("url", url);
    InputStream input = null;
    BufferedInputStream bis = null;
    ObjectInputStream ois = null;
    try {
      input = url.openStream();
      bis = new BufferedInputStream(input);
      ois = new ObjectInputStream(bis);
      return ois.readObject();
    } catch (final ClassNotFoundException | IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(ois);
      IoUtils.closeQuietly(bis);
      IoUtils.closeQuietly(input);
    }
  }

  /**
   * 从字节数组反序列化单个{@code Object}。
   *
   * @param data
   *     序列化后的对象，不能为 null。
   * @return 反序列化后的对象。
   * @throws NullPointerException
   *     如果 {@code data} 是 {@code null} 或为空。
   * @throws SerializationException
   *     如果反序列化失败。
   */
  public static Object deserialize(final byte[] data)
      throws SerializationException {
    Argument.requireNonEmpty("data", data);
    final ByteArrayInputStream input = new ByteArrayInputStream(data);
    ObjectInputStream ois = null;
    try {
      // stream closed in the finally
      ois = new ObjectInputStream(input);
      return ois.readObject();
    } catch (final ClassNotFoundException | IOException e) {
      throw new SerializationException(e);
    } finally {
      IoUtils.closeQuietly(ois);
      IoUtils.closeQuietly(input);
    }
  }
}