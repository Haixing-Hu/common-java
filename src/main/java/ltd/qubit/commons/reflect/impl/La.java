////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2024 - 2024.
//    Nanjing Xinglin Digital Technology Co., Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect.impl;

import java.io.Serial;
import java.io.Serializable;

/**
 * This is a class similar to java.lang.invoke.SerializedLambda, which is used to
 * capture the information of a lambda expression.
 *
 * <b>NOTE:</b> The length of the full class name of this class MUST be the
 * exactly same as the length of the `java.lang.invoke.SerializedLambda.class`.
 *
 * <b>NOTE:</b> The `serialVersionUID` of this class MUST be exactly the same
 * as the `java.lang.invoke.SerializedLambda.class`.
 *
 * @author Haixing Hu
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
