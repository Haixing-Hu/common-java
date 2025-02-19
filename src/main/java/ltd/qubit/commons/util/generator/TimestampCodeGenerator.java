/// /////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

/**
 * The code generator which adds a timestamp to the generated code.
 * <p>
 * This code generator generates codes with the following pattern:
 * <pre><code>
 *   [prefix] + '-' + [timestamp]
 * </code></pre>
 * For example,
 * <pre><code>
 *   “INV-1646827600000”
 *   “INV-1646827600001”
 * </code></pre>
 *
 * @author Haixing Hu
 */
public class TimestampCodeGenerator implements CodeGenerator {

  @Override
  public String generate(final String prefix) {
    return prefix + "-" + System.currentTimeMillis();
  }
}
