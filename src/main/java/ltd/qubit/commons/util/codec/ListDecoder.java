////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.util.List;

/**
 * Provides the interface to decode an source object to list of target objects.
 *
 * @param <FROM>
 *     the type of the source object to be decoded.
 * @param <TO>
 *     the type of the elements in the target list to be decoded to.
 * @author Haixing Hu
 */
public interface ListDecoder<FROM, TO> extends Decoder<FROM, List<TO>> {
  //  empty
}