////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io;

import java.io.OutputStream;

/**
 * A {@link SeekableOutputStream} object is an {@link OutputStream} supporting
 * the operations of {@link Seekable} interface.
 *
 * @author Haixing Hu
 */
public abstract class SeekableOutputStream extends OutputStream
    implements Seekable {
  //  empty
}
