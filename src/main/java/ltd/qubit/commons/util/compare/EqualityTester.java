////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.compare;

import java.util.function.BiPredicate;

/**
 * A tester to test the equality of two objects.
 *
 * @param <T>
 *      the type of the objects to be tested.
 */
public interface EqualityTester<T> extends BiPredicate<T, T> {
  //  empty
}