////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.InputStream;

/**
 * A {@link SeekableInputStream} object is an {@link InputStream} supporting the
 * operations of {@link Seekable} interface.
 *
 * @author Haixing Hu
 */
public abstract class SeekableInputStream extends InputStream
    implements Seekable {
  // empty
}
