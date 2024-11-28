////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.io.io.FileUtils;
import ltd.qubit.commons.lang.SystemUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static ltd.qubit.commons.io.io.FileUtils.getPath;

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
  public void testGenerateRandomFile() {
    final int count = 100;
    final String[] filenames = new String[count];
    for (int i = 0; i < count; ++i) {
      filenames[i] = FileUtils.getRandomFileName("prefix", ".txt");
    }
    for (int i = 0; i < count; ++i) {
      System.out.println("filename[" + i + "] = " + filenames[i]);
      assertEquals("prefix", filenames[i].substring(0, "prefix".length()));
      assertEquals(".txt", filenames[i].substring(filenames[i].length() - ".txt".length()));
      for (int j = i + 1; j < count; ++j) {
        assertNotEquals(filenames[i], filenames[j]);
      }
    }
  }
}
