////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 * 此接口提供从输入源读取键值对记录的功能。
 *
 * @author 胡海星
 */
public interface RecordReader<KEY, VALUE> extends Closeable {

  /**
   * 创建一个键。
   *
   * @return 默认构造的键对象。
   * @throws IllegalAccessException
   *           如果类或其无参构造函数不可访问。
   * @throws InstantiationException
   *           如果此 Class 表示抽象类、接口、数组类、基本类型或 void；
   *           或者如果类没有无参构造函数；或者如果实例化由于其他原因失败。
   * @throws ExceptionInInitializerError
   *           如果此方法引发的初始化失败。
   * @throws SecurityException
   *           如果存在安全管理器 s，并且满足以下任一条件：
   *           <ul>
   *           <li>调用 s.checkMemberAccess(this, Member.PUBLIC) 拒绝创建此类的新实例。</li>
   *           <li>调用者的类加载器与当前类的类加载器不同且不是其祖先，
   *           并且调用 s.checkPackageAccess() 拒绝访问此类的包。</li>
   *           </ul>
   */
  KEY createKey() throws IllegalAccessException, InstantiationException;

  /**
   * 创建一个值。
   *
   * @return 默认构造的值对象。
   * @throws IllegalAccessException
   *           如果类或其无参构造函数不可访问。
   * @throws InstantiationException
   *           如果此 Class 表示抽象类、接口、数组类、基本类型或 void；
   *           或者如果类没有无参构造函数；或者如果实例化由于其他原因失败。
   * @throws ExceptionInInitializerError
   *           如果此方法引发的初始化失败。
   * @throws SecurityException
   *           如果存在安全管理器 s，并且满足以下任一条件：
   *           <ul>
   *           <li>调用 s.checkMemberAccess(this, Member.PUBLIC) 拒绝创建此类的新实例</li>
   *           <li>调用者的类加载器与当前类的类加载器不同且不是其祖先，
   *           并且调用 s.checkPackageAccess() 拒绝访问此类的包。</li>
   *           </ul>
   */
  VALUE createValue() throws IllegalAccessException,
      InstantiationException;

  /**
   * 获取输入中的当前位置。
   *
   * @return 输入中的当前位置。
   * @throws IOException
   *           如果发生任何 I/O 错误。
   */
  long getPosition() throws IOException;

  /**
   * 获取此 RecordReader 已消费的输入比例。
   *
   * @return 此 RecordReader 已消费的输入比例，介于 0.0f 和 1.0f 之间。
   * @throws IOException
   *           如果发生任何 I/O 错误。
   */
  float getProgress() throws IOException;

  /**
   * 测试是否遇到文件结尾。
   *
   * @return 如果还有记录可以读取则返回 true；如果到达文件结尾则返回 false。
   * @throws IOException
   *           如果发生任何 I/O 错误。
   */
  boolean hasNext() throws IOException;

  /**
   * 从输入中读取下一个键值对进行处理。
   *
   * @return 从输入中读取的下一个键值对。
   * @throws IOException
   *           如果发生任何 I/O 错误。
   */
  Map.Entry<KEY, VALUE> next() throws IOException;
}