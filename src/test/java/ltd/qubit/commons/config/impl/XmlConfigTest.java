////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.io.IoUtils;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.net.Url;
import ltd.qubit.commons.net.UrlUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for the {@link XmlConfig} class.
 *
 * @author Haixing Hu
 */
public class XmlConfigTest extends ConfigTestBase {

  private static final String PACKAGE_PATH = "ltd/qubit/commons/config/impl/";

  @Test
  public void testDefaultConstructor() {
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
  }

  @Test
  public void testConstructorString() {
    final XmlConfig config = new XmlConfig(PACKAGE_PATH + BOOLEAN_CONFIG);
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorStringClass() {
    final XmlConfig config = new XmlConfig(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorStringClassLoader() {
    final XmlConfig config = new XmlConfig(PACKAGE_PATH + BOOLEAN_CONFIG,
        XmlConfigTest.class.getClassLoader());
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorUrl() throws MalformedURLException {
    final URL the_url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    final Url url = new Url(the_url);
    final XmlConfig config = new XmlConfig(url);
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorURL() {
    final URL url = SystemUtils
        .getResource(BOOLEAN_CONFIG, XmlConfigTest.class);
    final XmlConfig config = new XmlConfig(url);
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorURI() throws URISyntaxException {
    final URL url = SystemUtils
        .getResource(BOOLEAN_CONFIG, XmlConfigTest.class);
    final XmlConfig config = new XmlConfig(url.toURI());
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorFile() throws URISyntaxException {
    final URL url = SystemUtils
        .getResource(BOOLEAN_CONFIG, XmlConfigTest.class);
    final File file = new File(url.toURI());
    final XmlConfig config = new XmlConfig(file);
    assertNotNull(config);
    verifyBooleanProperties(config);
  }

  @Test
  public void testConstructorInputStream() throws IOException {
    final URL url = SystemUtils
        .getResource(BOOLEAN_CONFIG, XmlConfigTest.class);
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      final XmlConfig config = new XmlConfig(in);
      assertNotNull(config);
      verifyBooleanProperties(config);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  @Test
  public void testConstructorReader() throws IOException {
    final URL url = SystemUtils
        .getResource(BOOLEAN_CONFIG, XmlConfigTest.class);
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      final InputStreamReader reader = new InputStreamReader(in, UTF_8);
      final XmlConfig config = new XmlConfig(reader);
      assertNotNull(config);
      verifyBooleanProperties(config);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  @Test
  public void testLoadString() {
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(PACKAGE_PATH + BOOLEAN_CONFIG);
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadStringClass() {
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(PACKAGE_PATH + BOOLEAN_CONFIG, XmlConfigTest.class);
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadStringClassLoader() {
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(PACKAGE_PATH + BOOLEAN_CONFIG,
        XmlConfigTest.class.getClassLoader());
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadUrl() throws MalformedURLException {
    final URL the_url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    final Url url = new Url(the_url);
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(url);
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadURL() {
    final URL url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(url);
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadURI() throws URISyntaxException {
    final URL url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(url.toURI());
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadFile() throws URISyntaxException {
    final URL url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    final File file = new File(url.toURI());
    final XmlConfig config = new XmlConfig();
    assertNotNull(config);
    config.addString("xxxx", "hello world");
    config.load(file);
    verifyBooleanProperties(config);
  }

  @Test
  public void testLoadInputStream() throws IOException {
    final URL url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      final XmlConfig config = new XmlConfig();
      assertNotNull(config);
      config.addString("xxxx", "hello world");
      config.load(in);
      verifyBooleanProperties(config);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  @Test
  public void testLoadReader() throws IOException {
    final URL url = SystemUtils.getResource(BOOLEAN_CONFIG,
        XmlConfigTest.class);
    InputStream in = null;
    try {
      in = UrlUtils.openStream(url);
      final InputStreamReader reader = new InputStreamReader(in, UTF_8);
      final XmlConfig config = new XmlConfig();
      assertNotNull(config);
      config.addString("xxxx", "hello world");
      config.load(reader);
      verifyBooleanProperties(config);
    } finally {
      IoUtils.closeQuietly(in);
    }
  }

  @Test
  public void testStoreOutputStream() throws IOException {
    final XmlConfig config = new XmlConfig(STRING_CONFIG,
        XmlConfigTest.class);
    assertNotNull(config);

    final File file = File.createTempFile("test", ".xml");
    final FileOutputStream out = new FileOutputStream(file);
    config.store(out);
    out.close();

    final XmlConfig config2 = new XmlConfig(file);
    assertEquals(config, config2);
  }

  @Test
  public void testStorePrintStream() throws IOException {
    final XmlConfig config = new XmlConfig(STRING_CONFIG,
        XmlConfigTest.class);
    assertNotNull(config);
    final File file = File.createTempFile("test", ".xml");
    final PrintStream out = new PrintStream(file);
    config.store(out);
    out.close();
    final XmlConfig config2 = new XmlConfig(file);
    assertEquals(config, config2);
  }

  @Test
  public void testStoreWriter() {
    final XmlConfig config = new XmlConfig(STRING_CONFIG,
        XmlConfigTest.class);
    assertNotNull(config);
    final StringWriter writer = new StringWriter();
    config.store(writer);
    final String str = writer.toString();
    final StringReader reader = new StringReader(str);
    final XmlConfig config2 = new XmlConfig(reader);
    assertEquals(config, config2);
  }

  @Test
  public void testStoreFile() throws IOException {
    final XmlConfig config = new XmlConfig(STRING_CONFIG,
        XmlConfigTest.class);
    assertNotNull(config);

    final File file = File.createTempFile("test", ".xml");
    config.store(file);

    final XmlConfig config2 = new XmlConfig(file);
    assertEquals(config, config2);
  }
}
