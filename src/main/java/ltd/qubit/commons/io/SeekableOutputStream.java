////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.OutputStream;

/**
 * {@link SeekableOutputStream} 对象是一个支持 {@link Seekable} 接口操作的 {@link OutputStream}。
 *
 * @author 胡海星
 */
public abstract class SeekableOutputStream extends OutputStream
    implements Seekable {
  //  empty
}