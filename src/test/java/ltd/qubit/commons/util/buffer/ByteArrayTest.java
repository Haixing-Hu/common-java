////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.buffer;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit tests for {@link ByteBuffer}.
 */
public class ByteArrayTest {

  @Test
  public void testConstructor() {
    final ByteBuffer buffer = new ByteBuffer(16);
    assertEquals(16, buffer.capacity());
    assertEquals(0, buffer.length());
    assertNotNull(buffer.buffer());
    assertEquals(16, buffer.buffer().length);
    try {
      new ByteBuffer(- 1);
      fail("IllegalArgumentException should have been thrown");
    } catch (final IllegalArgumentException ex) {
      // expected
    }
  }

  @Test
  public void testSimpleAppend() {
    final ByteBuffer buffer = new ByteBuffer(16);
    assertEquals(16, buffer.capacity());
    assertEquals(0, buffer.length());
    final byte[] b1 = buffer.toArray();
    assertNotNull(b1);
    assertEquals(0, b1.length);
    assertTrue(buffer.isEmpty());
    assertFalse(buffer.isFull());

    final byte[] tmp = new byte[] { 1, 2, 3, 4 };
    buffer.append(tmp, 0, tmp.length);
    assertEquals(16, buffer.capacity());
    assertEquals(4, buffer.length());
    assertFalse(buffer.isEmpty());
    assertFalse(buffer.isFull());

    final byte[] b2 = buffer.toArray();
    assertNotNull(b2);
    assertEquals(4, b2.length);
    for (int i = 0; i < tmp.length; i++) {
      assertEquals(tmp[i], b2[i]);
      assertEquals(tmp[i], buffer.at(i));
    }
    buffer.clear();
    assertEquals(16, buffer.capacity());
    assertEquals(0, buffer.length());
    assertTrue(buffer.isEmpty());
    assertFalse(buffer.isFull());
  }

  @Test
  public void testExpandAppend() {
    final ByteBuffer buffer = new ByteBuffer(4);
    assertEquals(4, buffer.capacity());

    final byte[] tmp = new byte[] { 1, 2, 3, 4 };
    buffer.append(tmp, 0, 2);
    buffer.append(tmp, 0, 4);
    buffer.append(tmp, 0, 0);

    assertEquals(6, buffer.length());

    buffer.append(tmp, 0, 4);

    assertEquals(10, buffer.length());
  }

  @Test
  public void testInvalidAppend() {
    final ByteBuffer buffer = new ByteBuffer(4);
    buffer.append((byte[]) null, 0, 0);

    final byte[] tmp = new byte[] { 1, 2, 3, 4 };
    try {
      buffer.append(tmp, - 1, 0);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 0, - 1);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 0, 8);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 10, Integer.MAX_VALUE);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 2, 4);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
  }

  @Test
  public void testAppendOneByte() {
    final ByteBuffer buffer = new ByteBuffer(4);
    assertEquals(4, buffer.capacity());

    final byte[] tmp = new byte[] { 1, 127, - 1, - 128, 1, - 2 };
    for (final byte element : tmp) {
      buffer.append(element);
    }
    assertEquals(8, buffer.capacity());
    assertEquals(6, buffer.length());

    for (int i = 0; i < tmp.length; i++) {
      assertEquals(tmp[i], buffer.at(i));
    }
  }

  @Test
  public void testSetLength() {
    final ByteBuffer buffer = new ByteBuffer(4);
    buffer.setLength(2);
    assertEquals(2, buffer.length());
  }

  @Test
  public void testSetInvalidLength() {
    final ByteBuffer buffer = new ByteBuffer(4);
    try {
      buffer.setLength(- 2);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    //    try {
    //      buffer.setLength(200);
    //      fail("IndexOutOfBoundsException should have been thrown");
    //    } catch (final IndexOutOfBoundsException ex) {
    //      // expected
    //    }
  }

  @Test
  public void testReserve() {
    final ByteBuffer buffer = new ByteBuffer(4);
    buffer.reserve(2, true);
    assertEquals(4, buffer.capacity());
    buffer.reserve(8, false);
    assertEquals(8, buffer.capacity());
  }

  private static final byte COLON = (byte) ':';
  private static final byte COMMA = (byte) ',';

  @Test
  public void testIndexOf() throws Exception {
    final byte[] bytes = "name1: value1; name2: value2".getBytes(StandardCharsets.US_ASCII);
    final int index1 = 5;
    final int index2 = 20;

    final ByteBuffer buffer = new ByteBuffer(16);
    buffer.append(bytes, 0, bytes.length);

    assertEquals(index1, buffer.indexOf(COLON));
    assertEquals(- 1, buffer.indexOf(COMMA));
    assertEquals(index1, buffer.indexOf(COLON, - 1, 11));
    assertEquals(index1, buffer.indexOf(COLON, 0, 1000));
    assertEquals(- 1, buffer.indexOf(COLON, 2, 1));
    assertEquals(index2, buffer.indexOf(COLON, index1 + 1, buffer.length()));
  }

  @Test
  public void testAppendCharArrayAsAscii() throws Exception {
    final String s1 = "stuff";
    final String s2 = " and more stuff";
    final char[] b1 = s1.toCharArray();
    final char[] b2 = s2.toCharArray();

    final ByteBuffer buffer = new ByteBuffer(8);
    buffer.append(b1, 0, b1.length);
    buffer.append(b2, 0, b2.length);

    assertEquals(s1 + s2, new String(buffer.toArray(), StandardCharsets.US_ASCII));
  }

  @Test
  public void testAppendNullCharArray() {
    final ByteBuffer buffer = new ByteBuffer(8);
    buffer.append((char[]) null, 0, 0);
    assertEquals(0, buffer.length());
  }

  @Test
  public void testAppendEmptyCharArray() {
    final ByteBuffer buffer = new ByteBuffer(8);
    buffer.append(new char[] {}, 0, 0);
    assertEquals(0, buffer.length());
  }

  @Test
  public void testAppendNullCharArrayBuffer() {
    final ByteBuffer buffer = new ByteBuffer(8);
    buffer.append((CharBuffer) null, 0, 0);
    assertEquals(0, buffer.length());
  }

  @Test
  public void testInvalidAppendCharArrayAsAscii() {
    final ByteBuffer buffer = new ByteBuffer(4);
    buffer.append((char[]) null, 0, 0);

    final char[] tmp = new char[] { '1', '2', '3', '4' };
    try {
      buffer.append(tmp, - 1, 0);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 0, - 1);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 0, 8);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 10, Integer.MAX_VALUE);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
    try {
      buffer.append(tmp, 2, 4);
      fail("IndexOutOfBoundsException should have been thrown");
    } catch (final IndexOutOfBoundsException ex) {
      // expected
    }
  }

  @Test
  public void testSerialization() throws Exception {
    final ByteBuffer orig = new ByteBuffer(32);
    orig.append(1);
    orig.append(2);
    orig.append(3);
    final ByteArrayOutputStream outbuffer = new ByteArrayOutputStream();
    final ObjectOutputStream outstream = new ObjectOutputStream(outbuffer);
    outstream.writeObject(orig);
    outstream.close();
    final byte[] raw = outbuffer.toByteArray();
    final ByteArrayInputStream inbuffer = new ByteArrayInputStream(raw);
    final ObjectInputStream instream = new ObjectInputStream(inbuffer);
    final ByteBuffer clone = (ByteBuffer) instream.readObject();
    assertEquals(orig.capacity(), clone.capacity());
    assertEquals(orig.length(), clone.length());
    final byte[] data = clone.toArray();
    assertNotNull(data);
    assertEquals(3, data.length);
    assertEquals(1, data[0]);
    assertEquals(2, data[1]);
    assertEquals(3, data[2]);
  }

}
