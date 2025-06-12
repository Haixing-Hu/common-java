////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

import java.io.Serial;
import java.io.Serializable;

/**
 * 这是一个类似于 java.lang.invoke.SerializedLambda 的类，用于捕获lambda表达式的信息。
 *
 * <b>注意：</b> 此类的完整类名长度必须与 `java.lang.invoke.SerializedLambda.class` 的长度完全相同。
 *
 * <b>注意：</b> 此类的 `serialVersionUID` 必须与 `java.lang.invoke.SerializedLambda.class` 完全相同。
 *
 * @author 胡海星
 * @see <a href="https://gist.github.com/jhorstmann/de367a42a08d8deb8df9">How to get the method name from a java 8 lambda using serialization hacks</a>
 */
public class La implements Serializable {

  @Serial
  private static final long serialVersionUID = 8025925345765570181L;

  public Class<?> capturingClass;
  public String functionalInterfaceClass;
  public String functionalInterfaceMethodName;
  public String functionalInterfaceMethodSignature;
  public String implClass;
  public String implMethodName;
  public String implMethodSignature;
  public int implMethodKind;
  public String instantiatedMethodType;
  public Object[] capturedArgs;
}