////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;
import java.util.function.Predicate;

/**
 * The interface of generators used to generate the code of entities.
 *
 * @author Haixing Hu
 */
public interface CodeGenerator {
  /**
   * Generates a code of an entity.
   *
   * @param prefix
   *     the prefix of the code.
   * @return
   *     the code of entities.
   */
  String generate(String prefix);

  /**
   * Generates a unique code of an entity.
   *
   * @param prefix
   *     the prefix of the code.
   * @param existingChecker
   *     the predicate used to check whether the generated code already exists.
   * @return
   *     the unique code of entities.
   */
  default String generateUnique(final String prefix,
      final Predicate<String> existingChecker) {
    String code;
    do {
      code = generate(prefix);
    } while (existingChecker.test(code));
    return code;
  }
}