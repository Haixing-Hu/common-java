////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.io.error.InvalidFormatException;
import ltd.qubit.commons.io.serialize.BinarySerialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit tests for {@link Host}.
 */
public class HostTest  {

  @Test
  public void testConstructor() throws URISyntaxException, MalformedURLException {
    final Host host1 = new Host("somehost");
    assertEquals("somehost", host1.hostname());
    assertEquals(-1, host1.port());
    assertEquals("http", host1.scheme());
    final Host host2 = new Host("somehost", 8080);
    assertEquals("somehost", host2.hostname());
    assertEquals(8080, host2.port());
    assertEquals("http", host2.scheme());
    final Host host3 = new Host("somehost", - 1);
    assertEquals("somehost", host3.hostname());
    assertEquals(-1, host3.port());
    assertEquals("http", host3.scheme());
    final Host host4 = new Host("https", "somehost", 443);
    assertEquals("somehost", host4.hostname());
    assertEquals(443, host4.port());
    assertEquals("https", host4.scheme());
    try {
      new Host(null, null, - 1);
      fail("NullPointerException should have been thrown");
    } catch (final NullPointerException ex) {
      // expected
    } catch (final IllegalArgumentException ex2) {
      // @Nonnull will throw IllegalArgumentException for openJDK8
    }

    //  construct using URL
    assertEquals(new Host("localhost"),
        new Host(new URL("http://localhost/abcd")));
    assertEquals(new Host("localhost"),
        new Host(new URL("http://localhost/abcd%3A")));

    assertEquals(new Host("local_host"),
        new Host(new URL("http://local_host/abcd")));
    assertEquals(new Host("local_host"),
        new Host(new URL("http://local_host/abcd%3A")));

    assertEquals(new Host("localhost", 8),
        new Host(new URL("http://localhost:8/abcd")));
    assertEquals(new Host("local_host", 8),
        new Host(new URL("http://local_host:8/abcd")));

    assertEquals(new Host("localhost"),
        new Host(new URL("http://localhost:/abcd")));
    assertEquals(new Host("local_host"),
        new Host(new URL("http://local_host:/abcd")));

    assertEquals(new Host("localhost", 8080),
        new Host(new URL("http://user:pass@localhost:8080/abcd")));
    assertEquals(new Host("local_host", 8080),
        new Host(new URL("http://user:pass@local_host:8080/abcd")));

    assertEquals(new Host("localhost", 8080),
        new Host(new URL("http://@localhost:8080/abcd")));
    assertEquals(new Host("local_host", 8080),
        new Host(new URL("http://@local_host:8080/abcd")));

    //  construct using URI
    assertEquals(new Host("localhost"),
        new Host(new URI("http://localhost/abcd")));
    assertEquals(new Host("localhost"),
        new Host(new URI("http://localhost/abcd%3A")));

    assertEquals(new Host("local_host"),
        new Host(new URI("http://local_host/abcd")));
    assertEquals(new Host("local_host"),
        new Host(new URI("http://local_host/abcd%3A")));

    assertEquals(new Host("localhost", 8),
        new Host(new URI("http://localhost:8/abcd")));
    assertEquals(new Host("local_host", 8),
        new Host(new URI("http://local_host:8/abcd")));

    // URI seems to OK with missing port number
    assertEquals(new Host("localhost"),
        new Host(new URI("http://localhost:/abcd")));
    assertEquals(new Host("local_host"),
        new Host(new URI("http://local_host:/abcd")));

    assertEquals(new Host("localhost", 8080),
        new Host(new URI("http://user:pass@localhost:8080/abcd")));
    assertEquals(new Host("local_host", 8080),
        new Host(new URI("http://user:pass@local_host:8080/abcd")));

    assertEquals(new Host("localhost", 8080),
        new Host(new URI("http://@localhost:8080/abcd")));
    assertEquals(new Host("local_host", 8080),
        new Host(new URI("http://@local_host:8080/abcd")));
  }

  @Test
  public void testHashCode() {
    final Host host1 = new Host("http", "somehost", 8080);
    final Host host2 = new Host("http", "somehost", 80);
    final Host host3 = new Host("http", "someotherhost", 8080);
    final Host host4 = new Host("http", "somehost", 80);
    final Host host5 = new Host("http", "SomeHost", 80);
    final Host host6 = new Host("myhttp", "SomeHost", 80);

    assertTrue(host1.hashCode() == host1.hashCode());
    assertTrue(host1.hashCode() != host2.hashCode());
    assertTrue(host1.hashCode() != host3.hashCode());
    assertTrue(host2.hashCode() == host4.hashCode());
    assertTrue(host2.hashCode() == host5.hashCode());
    assertTrue(host5.hashCode() != host6.hashCode());
  }

  @Test
  public void testEquals() {
    final Host host1 = new Host("http", "somehost", 8080);
    final Host host2 = new Host("http", "somehost", 80);
    final Host host3 = new Host("http", "someotherhost", 8080);
    final Host host4 = new Host("http", "somehost", 80);
    final Host host5 = new Host("http", "SomeHost", 80);
    final Host host6 = new Host("myhttp", "SomeHost", 80);

    assertTrue(host1.equals(host1));
    assertFalse(host1.equals(host2));
    assertFalse(host1.equals(host3));
    assertTrue(host2.equals(host4));
    assertTrue(host2.equals(host5));
    assertFalse(host5.equals(host6));
    assertFalse(host1.equals(null));
    assertFalse(host1.equals("http://somehost"));
  }

  @Test
  public void testToString() {
    final Host host1 = new Host("somehost");
    assertEquals("http://somehost", host1.toString());
    final Host host2 = new Host("somehost", - 1);
    assertEquals("http://somehost", host2.toString());
    final Host host3 = new Host("somehost", - 1);
    assertEquals("http://somehost", host3.toString());
    final Host host4 = new Host("somehost", 8888);
    assertEquals("http://somehost:8888", host4.toString());
    final Host host5 = new Host("myhttp", "somehost", - 1);
    assertEquals("myhttp://somehost", host5.toString());
    final Host host6 = new Host("myhttp", "somehost", 80);
    assertEquals("myhttp://somehost:80", host6.toString());
  }

  @Test
  public void testToHostString() {
    final Host host1 = new Host("somehost");
    assertEquals("somehost", host1.toHostString());
    final Host host2 = new Host("somehost");
    assertEquals("somehost", host2.toHostString());
    final Host host3 = new Host("somehost", - 1);
    assertEquals("somehost", host3.toHostString());
    final Host host4 = new Host("somehost", 8888);
    assertEquals("somehost:8888", host4.toHostString());
  }

  @Test
  public void testSerialization() throws Exception {
    final Host orig = new Host("https", "somehost", 8080);
    final ByteArrayOutputStream outbuffer = new ByteArrayOutputStream();
    final ObjectOutputStream outstream = new ObjectOutputStream(outbuffer);
    outstream.writeObject(orig);
    outstream.close();
    final byte[] raw = outbuffer.toByteArray();
    final ByteArrayInputStream inbuffer = new ByteArrayInputStream(raw);
    final ObjectInputStream instream = new ObjectInputStream(inbuffer);
    final Host clone = (Host) instream.readObject();
    assertEquals(orig, clone);
  }

  @Test
  public void testBinarySerialization() throws IOException {
    Host host1;
    Host host2;
    byte[] data;

    host1 = null;
    data = BinarySerialization.serialize(Host.class, host1);
    host2 = BinarySerialization.deserialize(Host.class, data, true);
    assertEquals(host1, host2);

    host1 = null;
    data = BinarySerialization.serialize(Host.class, host1);
    try {
      host2 = BinarySerialization.deserialize(Host.class, data, false);
      fail("should throw");
    } catch (final InvalidFormatException e) {
      // pass
    }

    host1 = new Host("http", "www.sina.com.cn");
    data = BinarySerialization.serialize(Host.class, host1);
    host2 = BinarySerialization.deserialize(Host.class, data, false);
    assertEquals(host1, host2);

    host1 = new Host("www.sina.com.cn");
    data = BinarySerialization.serialize(Host.class, host1);
    host2 = BinarySerialization.deserialize(Host.class, data, false);
    assertEquals(host1, host2);

    host1 = new Host("http", "www.sina.com.cn", 8080);
    data = BinarySerialization.serialize(Host.class, host1);
    host2 = BinarySerialization.deserialize(Host.class, data, false);
    assertEquals(host1, host2);
  }

}