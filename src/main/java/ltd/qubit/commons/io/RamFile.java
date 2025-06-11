////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * {@link RamFile} 对象表示内存中的虚拟文件。
 *
 * <p>注意：此类不是线程安全的。
 *
 * @author 胡海星
 */
public final class RamFile implements Serializable {

  private static final long serialVersionUID = -1964074757799348292L;

  /**
   * RamFile 中块的大小（以字节为单位）。
   */
  public static final int BLOCK_SIZE = 4096;

  private final ArrayList<byte[]> blocks; // the data is stored in blocks
  private long length;       // the length of this RamFile
  private long lastModified; // the last modified time of this RamFile

  /**
   * 构造一个新的 RamFile。
   */
  public RamFile() {
    blocks = new ArrayList<>();
    length = 0;
    lastModified = System.currentTimeMillis();
  }

  /**
   * 获取文件的长度。
   *
   * @return 文件的长度（以字节为单位）。
   */
  public long getLength() {
    return length;
  }

  /**
   * 设置文件的长度。
   *
   * @param length 文件的新长度（以字节为单位）。
   */
  public void setLength(final long length) {
    this.length = length;
  }

  /**
   * 获取文件的最后修改时间。
   *
   * @return 文件的最后修改时间戳。
   */
  public long getLastModified() {
    return lastModified;
  }

  /**
   * 设置文件的最后修改时间。
   *
   * @param lastModified 文件的新最后修改时间戳。
   */
  public void setLastModified(final long lastModified) {
    this.lastModified = lastModified;
  }

  /**
   * 获取文件占用的总大小。
   *
   * @return 文件占用的总大小（以字节为单位）。
   */
  public long getOccupiedSize() {
    return ((long) blocks.size()) * ((long) BLOCK_SIZE);
  }

  /**
   * 添加一个新的数据块。
   *
   * @return 新添加的数据块。
   */
  public byte[] addBlock() {
    final byte[] block = new byte[BLOCK_SIZE];
    synchronized (this) {
      blocks.add(block);
    }
    return block;
  }

  /**
   * 获取指定索引的数据块。
   *
   * @param blockIndex 数据块的索引。
   * @return 指定索引的数据块。
   */
  public byte[] getBlock(final int blockIndex) {
    return blocks.get(blockIndex);
  }

  /**
   * 获取数据块的数量。
   *
   * @return 数据块的数量。
   */
  public int getBlockCount() {
    return blocks.size();
  }

  /**
   * 将此 {@link RamFile} 的当前内容复制到指定的 {@link OutputStream}。
   *
   * @param out
   *     写入数据的指定 {@link OutputStream}。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public void writeTo(final OutputStream out) throws IOException {
    long pos = 0;
    int blockIndex = 0;
    while (pos < length) {
      int bytes = BLOCK_SIZE;
      final long nextPos = pos + bytes;
      if (nextPos > length) { // at the last buffer
        bytes = (int) (length - pos);
      }
      final byte[] block = getBlock(blockIndex++);
      out.write(block, 0, bytes);
      pos = nextPos;
    }
  }
}