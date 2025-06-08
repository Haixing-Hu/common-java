////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

/**
 * 抛出此异常以指示参数为 {@code null} 但不应该为 {@code null}。
 * 此异常通过提供更丰富的语义描述来补充标准的 {@code IllegalArgumentException}。
 *
 * <p>{@code NullArgumentException} 表示方法接收的参数不能为 {@code null} 的情况。
 * 一些编码标准会在这种情况下使用 {@code NullPointerException}，
 * 其他的会使用 {@code IllegalArgumentException}。
 * 因此，此异常将用于替代 {@code IllegalArgumentException}，但它仍然扩展了它。
 *
 * <pre>
 * public void foo(String str) {
 *   if (str == null) {
 *     throw new NullArgumentException(&quot;str&quot;);
 *   }
 *   // do something with the string
 * }
 * </pre>
 *
 * @author 胡海星
 */
public class NullArgumentException extends IllegalArgumentException {

  private static final long serialVersionUID = -5537609091567361359L;

  /**
   * 构造一个新的空参数异常。
   */
  public NullArgumentException() {
    super("The argument must not be null nor empty.");
  }

  /**
   * 使用给定的参数名称实例化。
   *
   * @param argName
   *     为 {@code null} 的参数的名称。
   */
  public NullArgumentException(final String argName) {
    super("The "
        + ((argName == null) || (argName.length() == 0) ? "argument" : argName)
        + " must not be null nor empty.");
  }

}