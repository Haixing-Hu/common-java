////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
 * A {@link RamFile} object represents a virtual file in RAM.
 *
 * <p>Note that this class is NOT thread safe.
 *
 * @author Haixing Hu
 */
public final class RamFile implements Serializable {

  private static final long serialVersionUID = -1964074757799348292L;

  /**
   * The size in bytes of the blocks in the RamFile.
   */
  public static final int BLOCK_SIZE = 4096;

  private final ArrayList<byte[]> blocks; // the data is stored in blocks
  private long length;       // the length of this RamFile
  private long lastModified; // the last modified time of this RamFile

  public RamFile() {
    blocks = new ArrayList<>();
    length = 0;
    lastModified = System.currentTimeMillis();
  }

  public long getLength() {
    return length;
  }

  public void setLength(final long length) {
    this.length = length;
  }

  public long getLastModified() {
    return lastModified;
  }

  public void setLastModified(final long lastModified) {
    this.lastModified = lastModified;
  }

  public long getOccupiedSize() {
    return ((long) blocks.size()) * ((long) BLOCK_SIZE);
  }

  public byte[] addBlock() {
    final byte[] block = new byte[BLOCK_SIZE];
    synchronized (this) {
      blocks.add(block);
    }
    return block;
  }

  public byte[] getBlock(final int blockIndex) {
    return blocks.get(blockIndex);
  }

  public int getBlockCount() {
    return blocks.size();
  }

  /**
   * Copy the current contents of this {@link RamFile} to a specified {@link
   * OutputStream}.
   *
   * @param out
   *     a specified {@link OutputStream} where to write the data.
   * @throws IOException
   *     if any I/O error occurs.
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
