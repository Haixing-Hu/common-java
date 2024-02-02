////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

/**
 * Thrown to indicate that an argument was {@code null} and should not have
 * been. This exception supplements the standard
 * {@code IllegalArgumentException} by providing a more semantically rich
 * description of the problem.
 *
 * <p>{@code NullArgumentException} represents the case where a method takes
 * in a parameter that must not be {@code null}. Some coding standards
 * would use {@code NullPointerException} for this case, others will use
 * {@code IllegalArgumentException}. Thus this exception would be used in
 * place of {@code IllegalArgumentException}, yet it still extends it.
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
 * @author Haixing Hu
 */
public class NullArgumentException extends IllegalArgumentException {

  private static final long serialVersionUID = -5537609091567361359L;

  public NullArgumentException() {
    super("The argument must not be null nor empty.");
  }

  /**
   * Instantiates with the given argument name.
   *
   * @param argName
   *          the name of the argument that was {@code null}.
   */
  public NullArgumentException(final String argName) {
    super("The "
        + ((argName == null) || (argName.length() == 0) ? "argument" : argName)
        + " must not be null nor empty.");
  }

}
