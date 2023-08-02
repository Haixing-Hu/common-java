////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import ltd.qubit.commons.lang.SystemUtils;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.io.FileUtils.getFilenameFromPath;
import static ltd.qubit.commons.io.FileUtils.getPath;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileUtilsTest {

  @Test
  public void testGetPath() {
    if (SystemUtils.IS_OS_WINDOWS) {
      assertEquals("c:\\tmp\\file.txt", getPath("c:\\tmp", "file", "txt"));
      assertEquals("c:\\tmp\\file", getPath("c:\\tmp", "file", null));
      assertEquals("c:\\tmp\\file", getPath("c:\\tmp", "file", ""));
      assertEquals("c:\\tmp\\file.txt", getPath("c:\\tmp\\", "file", "txt"));
      assertEquals("c:\\tmp\\file", getPath("c:\\tmp\\", "file", null));
      assertEquals("c:\\tmp\\file", getPath("c:\\tmp\\", "file", ""));
    } else if (SystemUtils.IS_OS_UNIX) {
      assertEquals("/tmp/file.txt", getPath("/tmp", "file", "txt"));
      assertEquals("/tmp/file", getPath("/tmp", "file", null));
      assertEquals("/tmp/file", getPath("/tmp", "file", ""));
      assertEquals("/tmp/file.txt", getPath("/tmp/", "file", "txt"));
      assertEquals("/tmp/file", getPath("/tmp/", "file", null));
      assertEquals("/tmp/file", getPath("/tmp/", "file", ""));
    }
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
