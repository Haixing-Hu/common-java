////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

/**
 * The codec of the {@link Boolean} type which maps {@code true} to "Y" and
 * {@code false} to "N".
 *
 * @author Haixing Hu
 */
public class AlphabetBooleanCodec extends BooleanCodec {

  public AlphabetBooleanCodec() {
    super("Y", "N", true);
  }
}
