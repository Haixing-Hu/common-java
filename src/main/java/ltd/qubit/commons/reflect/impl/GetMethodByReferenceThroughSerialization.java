////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.reflect.MethodUtils;
import ltd.qubit.commons.reflect.ReflectionException;

import static ltd.qubit.commons.reflect.MethodUtils.getMatchingMethod;

/**
 * 提供使用序列化技巧从lambda表达式获取方法名的功能。
 *
 * @author 胡海星
 * @see La
 * @see <a href="https://gist.github.com/jhorstmann/de367a42a08d8deb8df9">How to get the method name from a java 8 lambda using serialization hacks</a>
 */
public class GetMethodByReferenceThroughSerialization {

  /**
   * 用于字节数组和字符串之间转换的字符编码。
   */
  private static final String BINARY = "iso-8859-1";

  /**
   * 通过序列化获取方法名的内部实现。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   * @throws ReflectionException
   *     如果序列化过程中发生错误
   */
  private static <T, R> String getMethodNameBySerializationImpl(final MethodReference<T> ref) {
    final String originalName = java.lang.invoke.SerializedLambda.class.getName();
    final String replacedName = ltd.qubit.commons.reflect.impl.La.class.getName();
    try {
      if (originalName.length() != replacedName.length()) {
        throw new IllegalArgumentException("The length of the original name and the replaced name are not equal.");
      }
      final ByteArrayOutputStream bos = new ByteArrayOutputStream();
      final ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(ref);
      oos.flush();
      final byte[] bytes = new String(bos.toByteArray(), BINARY)
          .replace(originalName, replacedName)
          .getBytes(BINARY);
      final ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
      final La la = (La) in.readObject();
      return la.implMethodName;
    } catch (final Exception e) {
      throw new ReflectionException(e);
    }
  }

  /**
   * 通过序列化获取无参数void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T> String getMethodNameBySerialization(final VoidMethod0<T> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取无参数非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R> String getMethodNameBySerialization(final NonVoidMethod0<T, R> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, P1> String getMethodNameBySerialization(final VoidMethod1<T, P1> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R, P1> String getMethodNameBySerialization(final NonVoidMethod1<T, R, P1> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带两个参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, P1, P2> String getMethodNameBySerialization(final VoidMethod2<T, P1, P2> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带两个参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R, P1, P2> String getMethodNameBySerialization(final NonVoidMethod2<T, R, P1, P2> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带三个参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, P1, P2, P3> String getMethodNameBySerialization(final VoidMethod3<T, P1, P2, P3> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带三个参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R, P1, P2, P3> String getMethodNameBySerialization(final NonVoidMethod3<T, R, P1, P2, P3> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带四个参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, P1, P2, P3, P4> String getMethodNameBySerialization(final VoidMethod4<T, P1, P2, P3, P4> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带四个参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R, P1, P2, P3, P4> String getMethodNameBySerialization(final NonVoidMethod4<T, R, P1, P2, P3, P4> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带五个参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, P1, P2, P3, P4, P5> String getMethodNameBySerialization(final VoidMethod5<T, P1, P2, P3, P4, P5> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带五个参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R, P1, P2, P3, P4, P5> String getMethodNameBySerialization(final NonVoidMethod5<T, R, P1, P2, P3, P4, P5> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带六个参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, P1, P2, P3, P4, P5, P6> String getMethodNameBySerialization(final VoidMethod6<T, P1, P2, P3, P4, P5, P6> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带六个参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R, P1, P2, P3, P4, P5, P6> String getMethodNameBySerialization(final NonVoidMethod6<T, R, P1, P2, P3, P4, P5, P6> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带七个参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param <P7>
   *     第七个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, P1, P2, P3, P4, P5, P6, P7> String getMethodNameBySerialization(final VoidMethod7<T, P1, P2, P3, P4, P5, P6, P7> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带七个参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param <P7>
   *     第七个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R, P1, P2, P3, P4, P5, P6, P7> String getMethodNameBySerialization(final NonVoidMethod7<T, R, P1, P2, P3, P4, P5, P6, P7> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带八个参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param <P7>
   *     第七个参数的类型
   * @param <P8>
   *     第八个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, P1, P2, P3, P4, P5, P6, P7, P8> String getMethodNameBySerialization(final VoidMethod8<T, P1, P2, P3, P4, P5, P6, P7, P8> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带八个参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param <P7>
   *     第七个参数的类型
   * @param <P8>
   *     第八个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R, P1, P2, P3, P4, P5, P6, P7, P8> String getMethodNameBySerialization(final NonVoidMethod8<T, R, P1, P2, P3, P4, P5, P6, P7, P8> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带九个参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param <P7>
   *     第七个参数的类型
   * @param <P8>
   *     第八个参数的类型
   * @param <P9>
   *     第九个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, P1, P2, P3, P4, P5, P6, P7, P8, P9> String getMethodNameBySerialization(final VoidMethod9<T, P1, P2, P3, P4, P5, P6, P7, P8, P9> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带九个参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param <P7>
   *     第七个参数的类型
   * @param <P8>
   *     第八个参数的类型
   * @param <P9>
   *     第九个参数的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9> String getMethodNameBySerialization(final NonVoidMethod9<T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个boolean参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T> String getMethodNameBySerialization(final VoidMethod1Boolean<T> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个boolean参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R> String getMethodNameBySerialization(final NonVoidMethod1Boolean<T, R> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个char参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T> String getMethodNameBySerialization(final VoidMethod1Char<T> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个char参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R> String getMethodNameBySerialization(final NonVoidMethod1Char<T, R> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个byte参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T> String getMethodNameBySerialization(final VoidMethod1Byte<T> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个byte参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R> String getMethodNameBySerialization(final NonVoidMethod1Byte<T, R> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个short参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T> String getMethodNameBySerialization(final VoidMethod1Short<T> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个short参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R> String getMethodNameBySerialization(final NonVoidMethod1Short<T, R> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个int参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T> String getMethodNameBySerialization(final VoidMethod1Int<T> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个int参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R> String getMethodNameBySerialization(final NonVoidMethod1Int<T, R> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个long参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T> String getMethodNameBySerialization(final VoidMethod1Long<T> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个long参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R> String getMethodNameBySerialization(final NonVoidMethod1Long<T, R> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个float参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T> String getMethodNameBySerialization(final VoidMethod1Float<T> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个float参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R> String getMethodNameBySerialization(final NonVoidMethod1Float<T, R> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个double参数的void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T> String getMethodNameBySerialization(final VoidMethod1Double<T> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化获取带一个double参数的非void方法的方法名。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param ref
   *     方法引用
   * @return
   *     方法名
   */
  public static <T, R> String getMethodNameBySerialization(final NonVoidMethod1Double<T, R> ref) {
    return getMethodNameBySerializationImpl(ref);
  }

  /**
   * 通过序列化查找无参数void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T> Method findMethodBySerialization(final Class<T> cls, final VoidMethod0<T> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return getMatchingMethod(cls, methodName, ArrayUtils.EMPTY_CLASS_ARRAY);
  }

  /**
   * 通过序列化查找无参数非void方法。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod0<T, R> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    final Method method = getMatchingMethod(cls, methodName, ArrayUtils.EMPTY_CLASS_ARRAY);
    if (method == null) {
      throw new IllegalArgumentException("Cannot find the method " + cls.getName() + "." + methodName + "()");
    }
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, P1> Method findMethodBySerialization(final Class<T> cls, final VoidMethod1<T, P1> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R, P1> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod1<T, R, P1> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带两个参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, P1, P2> Method findMethodBySerialization(final Class<T> cls, final VoidMethod2<T, P1, P2> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带两个参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R, P1, P2> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod2<T, R, P1, P2> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带三个参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, P1, P2, P3> Method findMethodBySerialization(final Class<T> cls, final VoidMethod3<T, P1, P2, P3> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带三个参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R, P1, P2, P3> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod3<T, R, P1, P2, P3> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带四个参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, P1, P2, P3, P4> Method findMethodBySerialization(final Class<T> cls, final VoidMethod4<T, P1, P2, P3, P4> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带四个参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R, P1, P2, P3, P4> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod4<T, R, P1, P2, P3, P4> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带五个参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, P1, P2, P3, P4, P5> Method findMethodBySerialization(final Class<T> cls, final VoidMethod5<T, P1, P2, P3, P4, P5> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带五个参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R, P1, P2, P3, P4, P5> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod5<T, R, P1, P2, P3, P4, P5> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带六个参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, P1, P2, P3, P4, P5, P6> Method findMethodBySerialization(final Class<T> cls, final VoidMethod6<T, P1, P2, P3, P4, P5, P6> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带六个参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R, P1, P2, P3, P4, P5, P6> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod6<T, R, P1, P2, P3, P4, P5, P6> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带七个参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param <P7>
   *     第七个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, P1, P2, P3, P4, P5, P6, P7> Method findMethodBySerialization(final Class<T> cls, final VoidMethod7<T, P1, P2, P3, P4, P5, P6, P7> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带七个参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param <P7>
   *     第七个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R, P1, P2, P3, P4, P5, P6, P7> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod7<T, R, P1, P2, P3, P4, P5, P6, P7> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带八个参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param <P7>
   *     第七个参数的类型
   * @param <P8>
   *     第八个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, P1, P2, P3, P4, P5, P6, P7, P8> Method findMethodBySerialization(final Class<T> cls, final VoidMethod8<T, P1, P2, P3, P4, P5, P6, P7, P8> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带八个参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param <P7>
   *     第七个参数的类型
   * @param <P8>
   *     第八个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R, P1, P2, P3, P4, P5, P6, P7, P8> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod8<T, R, P1, P2, P3, P4, P5, P6, P7, P8> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带九个参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param <P7>
   *     第七个参数的类型
   * @param <P8>
   *     第八个参数的类型
   * @param <P9>
   *     第九个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, P1, P2, P3, P4, P5, P6, P7, P8, P9> Method findMethodBySerialization(final Class<T> cls, final VoidMethod9<T, P1, P2, P3, P4, P5, P6, P7, P8, P9> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带九个参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param <R>
   *     返回值类型
   * @param <P1>
   *     第一个参数的类型
   * @param <P2>
   *     第二个参数的类型
   * @param <P3>
   *     第三个参数的类型
   * @param <P4>
   *     第四个参数的类型
   * @param <P5>
   *     第五个参数的类型
   * @param <P6>
   *     第六个参数的类型
   * @param <P7>
   *     第七个参数的类型
   * @param <P8>
   *     第八个参数的类型
   * @param <P9>
   *     第九个参数的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod9<T, R, P1, P2, P3, P4, P5, P6, P7, P8, P9> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个boolean参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T> Method findMethodBySerialization(final Class<T> cls, final VoidMethod1Boolean<T> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个boolean参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod1Boolean<T, R> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个char参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T> Method findMethodBySerialization(final Class<T> cls, final VoidMethod1Char<T> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个char参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod1Char<T, R> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个byte参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T> Method findMethodBySerialization(final Class<T> cls, final VoidMethod1Byte<T> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个byte参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod1Byte<T, R> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个short参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T> Method findMethodBySerialization(final Class<T> cls, final VoidMethod1Short<T> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个short参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod1Short<T, R> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个int参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T> Method findMethodBySerialization(final Class<T> cls, final VoidMethod1Int<T> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个int参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod1Int<T, R> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个long参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T> Method findMethodBySerialization(final Class<T> cls, final VoidMethod1Long<T> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个long参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod1Long<T, R> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个float参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T> Method findMethodBySerialization(final Class<T> cls, final VoidMethod1Float<T> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个float参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod1Float<T, R> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个double参数的void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T> Method findMethodBySerialization(final Class<T> cls, final VoidMethod1Double<T> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }

  /**
   * 通过序列化查找带一个double参数的非void方法。
   *
   * @param <T>
   *     类的类型
   * @param cls
   *     包含方法的类
   * @param ref
   *     方法引用
   * @return
   *     对应的方法对象
   * @throws ReflectionException
   *     如果查找方法失败
   */
  public static <T, R> Method findMethodBySerialization(final Class<T> cls, final NonVoidMethod1Double<T, R> ref) {
    final String methodName = getMethodNameBySerialization(ref);
    return MethodUtils.getMethodByName(cls, methodName);
  }
}