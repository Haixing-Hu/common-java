////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.lang.SystemUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static ltd.qubit.commons.io.FilenameUtils.getBasename;
import static ltd.qubit.commons.io.FilenameUtils.getDotExtension;
import static ltd.qubit.commons.io.FilenameUtils.getExtension;
import static ltd.qubit.commons.io.FilenameUtils.getFilenameFromPath;

public class FilenameUtilsTest {

  @Test
  public void testGetBaseNamePathBaseCases() {
    assertEquals("bar", getBasename(Paths.get("a/b/c/bar.foo")));
    assertEquals("foo", getBasename(Paths.get("foo")));
    assertNull(getBasename(Paths.get("")));
    assertEquals("", getBasename(Paths.get(".")));
    for (final File f : File.listRoots()) {
      assertNull(getBasename(f.toPath()));
    }
    if (SystemUtils.IS_OS_WINDOWS) {
      assertNull(getBasename(Paths.get("C:\\")));
    }
  }

  @Test
  public void testGetBaseNamePathCornerCases() {
    assertNull(getBasename((Path) null));
    assertEquals("foo", getBasename(Paths.get("foo.")));
    assertEquals("", getBasename(Paths.get("bar/.foo")));
  }

  @Test
  public void testGetBaseNameFileBaseCases() {
    assertEquals("bar", getBasename(new File("a/b/c/bar.foo")));
    assertEquals("foo", getBasename(new File("foo")));
    assertNull(getBasename(new File("")));
    assertEquals("", getBasename(new File(".")));
    for (final File f : File.listRoots()) {
      assertNull(getBasename(f));
    }
    if (SystemUtils.IS_OS_WINDOWS) {
      assertNull(getBasename(new File("C:\\")));
    }
  }

  @Test
  public void testGetBaseNameFileCornerCases() {
    assertNull(getBasename((File) null));
    assertNull(getBasename(new File("")));
    assertEquals("foo", getBasename(new File("foo.")));
    assertEquals("", getBasename(new File("bar/.foo")));
  }

  @Test
  public void testGetBaseNameStringBaseCases() {
    assertEquals("bar", getBasename("a/b/c/bar.foo"));
    assertEquals("foo", getBasename("foo"));
  }

  @Test
  public void testGetBaseNameStringCornerCases() {
    assertNull(getBasename((String) null));
    assertNull(getBasename(""));
    assertEquals("foo", getBasename("foo."));
    assertEquals("", getBasename("bar/.foo"));
  }

  @Test
  public void testGetExtensionPathBaseCases() {
    assertEquals("foo", getExtension(Paths.get("a/b/c/bar.foo")));
    assertEquals("", getExtension(Paths.get("foo")));
    assertNull(getExtension(Paths.get("")));
    assertEquals("", getExtension(Paths.get(".")));
    for (final File f : File.listRoots()) {
      assertNull(getExtension(f.toPath()));
    }
    if (SystemUtils.IS_OS_WINDOWS) {
      assertNull(getExtension(Paths.get("C:\\")));
    }
  }

  @Test
  public void testGetExtensionPathCornerCases() {
    assertNull(getExtension((String) null));
    assertNull(getExtension(Paths.get("")));
    assertEquals("", getExtension(Paths.get("foo.")));
    assertEquals("foo", getExtension(Paths.get("bar/.foo")));
  }

  @Test
  public void testGetExtensionFileBaseCases() {
    assertEquals("foo", getExtension(new File("a/b/c/bar.foo")));
    assertEquals("", getExtension(new File("foo")));
    assertNull(getExtension(new File("")));
    assertEquals("", getExtension(new File(".")));
    for (final File f : File.listRoots()) {
      assertNull(getExtension(f));
    }
    if (SystemUtils.IS_OS_WINDOWS) {
      assertNull(getExtension(new File("C:\\")));
    }
  }

  @Test
  public void testGetExtensionFileCornerCases() {
    assertNull(getExtension((File) null));
    assertNull(getExtension(new File("")));
    assertEquals("", getExtension(new File("foo.")));
    assertEquals("foo", getExtension(new File("bar/.foo")));
  }

  @Test
  public void testGetExtensionStringBaseCases() {
    assertEquals("foo", getExtension("a/b/c/bar.foo"));
    assertEquals("", getExtension("foo"));
  }

  @Test
  public void testGetExtensionStringCornerCases() {
    assertNull(getExtension((String) null));
    assertEquals("", getExtension("foo."));
    assertEquals("foo", getExtension("bar/.foo"));
  }

  @Test
  public void testGetDotExtensionPathBaseCases() {
    assertEquals(".foo", getDotExtension(Paths.get("a/b/c/bar.foo")));
    assertEquals("", getDotExtension(Paths.get("foo")));
    assertNull(getDotExtension(Paths.get("")));
    assertEquals("", getDotExtension(Paths.get(".")));
    for (final File f : File.listRoots()) {
      assertNull(getDotExtension(f.toPath()));
    }
    if (SystemUtils.IS_OS_WINDOWS) {
      assertNull(getDotExtension(Paths.get("C:\\")));
    }
  }

  @Test
  public void testGetDotExtensionPathCornerCases() {
    assertNull(getDotExtension((String) null));
    assertNull(getDotExtension(Paths.get("")));
    assertEquals("", getDotExtension(Paths.get("foo.")));
    assertEquals(".foo", getDotExtension(Paths.get("bar/.foo")));
  }

  @Test
  public void testGetDotExtensionFileBaseCases() {
    assertEquals(".foo", getDotExtension(new File("a/b/c/bar.foo")));
    assertEquals("", getDotExtension(new File("foo")));
    assertNull(getDotExtension(new File("")));
    assertEquals("", getDotExtension(new File(".")));
    for (final File f : File.listRoots()) {
      assertNull(getDotExtension(f));
    }
    if (SystemUtils.IS_OS_WINDOWS) {
      assertNull(getDotExtension(new File("C:\\")));
    }
  }

  @Test
  public void testGetDotExtensionFileCornerCases() {
    assertNull(getDotExtension((File) null));
    assertNull(getDotExtension(new File("")));
    assertEquals("", getDotExtension(new File("foo.")));
    assertEquals(".foo", getDotExtension(new File("bar/.foo")));
  }

  @Test
  public void testGetDotExtensionStringBaseCases() {
    assertEquals(".foo", getDotExtension("a/b/c/bar.foo"));
    assertEquals("", getDotExtension("foo"));
  }

  @Test
  public void testGetDotExtensionStringCornerCases() {
    assertNull(getDotExtension((String) null));
    assertEquals("", getDotExtension("foo."));
    assertEquals(".foo", getDotExtension("bar/.foo"));
  }


  @Test
  public void testGetFilenameFromPath() {
    if (SystemUtils.IS_OS_WINDOWS) {
      assertEquals("file.txt", getFilenameFromPath("c:\\tmp\\apt\\file.txt"));
    } else if (SystemUtils.IS_OS_UNIX) {
      assertEquals("file.txt", getFilenameFromPath("/tmp/apt/file.txt"));
    }
  }
}