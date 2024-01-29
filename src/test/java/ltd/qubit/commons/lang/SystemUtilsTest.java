////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static ltd.qubit.commons.lang.SystemUtils.getJavaVersionAsFloat;
import static ltd.qubit.commons.lang.SystemUtils.getJavaVersionAsInt;
import static ltd.qubit.commons.lang.SystemUtils.getJavaVersionMatches;
import static ltd.qubit.commons.lang.SystemUtils.getOsMatches;

/**
 * Unit test for the Systems class.
 *
 * @author Haixing Hu
 */
public class SystemUtilsTest {

  @Test
  public void testIS_JAVA() {
    final String javaVersion = System.getProperty("java.version");
    if (javaVersion == null) {
      assertEquals(false, SystemUtils.IS_JAVA_1);
      assertEquals(false, SystemUtils.IS_JAVA_2);
      assertEquals(false, SystemUtils.IS_JAVA_3);
      assertEquals(false, SystemUtils.IS_JAVA_4);
      assertEquals(false, SystemUtils.IS_JAVA_5);
      assertEquals(false, SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.1")) {
      assertEquals(true, SystemUtils.IS_JAVA_1);
      assertEquals(false, SystemUtils.IS_JAVA_2);
      assertEquals(false, SystemUtils.IS_JAVA_3);
      assertEquals(false, SystemUtils.IS_JAVA_4);
      assertEquals(false, SystemUtils.IS_JAVA_5);
      assertEquals(false, SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.2")) {
      assertEquals(false, SystemUtils.IS_JAVA_1);
      assertEquals(true, SystemUtils.IS_JAVA_2);
      assertEquals(false, SystemUtils.IS_JAVA_3);
      assertEquals(false, SystemUtils.IS_JAVA_4);
      assertEquals(false, SystemUtils.IS_JAVA_5);
      assertEquals(false, SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.3")) {
      assertEquals(false, SystemUtils.IS_JAVA_1);
      assertEquals(false, SystemUtils.IS_JAVA_2);
      assertEquals(true, SystemUtils.IS_JAVA_3);
      assertEquals(false, SystemUtils.IS_JAVA_4);
      assertEquals(false, SystemUtils.IS_JAVA_5);
      assertEquals(false, SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.4")) {
      assertEquals(false, SystemUtils.IS_JAVA_1);
      assertEquals(false, SystemUtils.IS_JAVA_2);
      assertEquals(false, SystemUtils.IS_JAVA_3);
      assertEquals(true, SystemUtils.IS_JAVA_4);
      assertEquals(false, SystemUtils.IS_JAVA_5);
      assertEquals(false, SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.5")) {
      assertEquals(false, SystemUtils.IS_JAVA_1);
      assertEquals(false, SystemUtils.IS_JAVA_2);
      assertEquals(false, SystemUtils.IS_JAVA_3);
      assertEquals(false, SystemUtils.IS_JAVA_4);
      assertEquals(true, SystemUtils.IS_JAVA_5);
      assertEquals(false, SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.6")) {
      assertEquals(false, SystemUtils.IS_JAVA_1);
      assertEquals(false, SystemUtils.IS_JAVA_2);
      assertEquals(false, SystemUtils.IS_JAVA_3);
      assertEquals(false, SystemUtils.IS_JAVA_4);
      assertEquals(false, SystemUtils.IS_JAVA_5);
      assertEquals(true, SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.7")) {
      assertEquals(false, SystemUtils.IS_JAVA_1);
      assertEquals(false, SystemUtils.IS_JAVA_2);
      assertEquals(false, SystemUtils.IS_JAVA_3);
      assertEquals(false, SystemUtils.IS_JAVA_4);
      assertEquals(false, SystemUtils.IS_JAVA_5);
      assertEquals(false, SystemUtils.IS_JAVA_6);
      assertEquals(true, SystemUtils.IS_JAVA_7);
    } else if (javaVersion.startsWith("1.8")) {
      assertEquals(false, SystemUtils.IS_JAVA_1);
      assertEquals(false, SystemUtils.IS_JAVA_2);
      assertEquals(false, SystemUtils.IS_JAVA_3);
      assertEquals(false, SystemUtils.IS_JAVA_4);
      assertEquals(false, SystemUtils.IS_JAVA_5);
      assertEquals(false, SystemUtils.IS_JAVA_6);
      assertEquals(false, SystemUtils.IS_JAVA_7);
      assertEquals(true, SystemUtils.IS_JAVA_8);
    } else if (javaVersion.startsWith("1.9")) {
      assertEquals(false, SystemUtils.IS_JAVA_1);
      assertEquals(false, SystemUtils.IS_JAVA_2);
      assertEquals(false, SystemUtils.IS_JAVA_3);
      assertEquals(false, SystemUtils.IS_JAVA_4);
      assertEquals(false, SystemUtils.IS_JAVA_5);
      assertEquals(false, SystemUtils.IS_JAVA_6);
      assertEquals(false, SystemUtils.IS_JAVA_7);
      assertEquals(false, SystemUtils.IS_JAVA_8);
      assertEquals(true, SystemUtils.IS_JAVA_9);
    } else {
      System.out.println("Can't test IS_JAVA value");
    }
  }

  @Test
  public void testIS_OS() {
    final String osName = System.getProperty("os.name");
    if (osName == null) {
      assertEquals(false, SystemUtils.IS_OS_WINDOWS);
      assertEquals(false, SystemUtils.IS_OS_UNIX);
      assertEquals(false, SystemUtils.IS_OS_SOLARIS);
      assertEquals(false, SystemUtils.IS_OS_LINUX);
      assertEquals(false, SystemUtils.IS_OS_MAC_OSX);
    } else if (osName.startsWith("Windows")) {
      assertEquals(false, SystemUtils.IS_OS_UNIX);
      assertEquals(true, SystemUtils.IS_OS_WINDOWS);
    } else if (osName.startsWith("Solaris")) {
      assertEquals(true, SystemUtils.IS_OS_SOLARIS);
      assertEquals(true, SystemUtils.IS_OS_UNIX);
      assertEquals(false, SystemUtils.IS_OS_WINDOWS);
    } else if (osName.toLowerCase().startsWith("linux")) {
      assertEquals(true, SystemUtils.IS_OS_LINUX);
      assertEquals(true, SystemUtils.IS_OS_UNIX);
      assertEquals(false, SystemUtils.IS_OS_WINDOWS);
    } else if (osName.startsWith("Mac OS X")) {
      assertEquals(true, SystemUtils.IS_OS_MAC_OSX);
      assertEquals(true, SystemUtils.IS_OS_UNIX);
      assertEquals(false, SystemUtils.IS_OS_WINDOWS);
    } else if (osName.startsWith("OS/2")) {
      assertEquals(true, SystemUtils.IS_OS_OS2);
      assertEquals(false, SystemUtils.IS_OS_UNIX);
      assertEquals(false, SystemUtils.IS_OS_WINDOWS);
    } else if (osName.startsWith("SunOS")) {
      assertEquals(true, SystemUtils.IS_OS_SUN_OS);
      assertEquals(true, SystemUtils.IS_OS_UNIX);
      assertEquals(false, SystemUtils.IS_OS_WINDOWS);
    } else {
      System.out.println("Can't test IS_OS value");
    }
  }

  @Test
  public void testJavaVersionAsFloat() {
    assertEquals(0f, getJavaVersionAsFloat(null), 0.000001f);
    assertEquals(1f, getJavaVersionAsFloat("1.1"), 0.000001f);
    assertEquals(2f, getJavaVersionAsFloat("1.2"), 0.000001f);
    assertEquals(3f, getJavaVersionAsFloat("1.3.0"), 0.000001f);
    assertEquals(3.1f, getJavaVersionAsFloat("1.3.1"), 0.000001f);
    assertEquals(4f, getJavaVersionAsFloat("1.4.0"), 0.000001f);
    assertEquals(4.1f, getJavaVersionAsFloat("1.4.1"), 0.000001f);
    assertEquals(5f, getJavaVersionAsFloat("1.5.0"), 0.000001f);
    assertEquals(6f, getJavaVersionAsFloat("1.6.0"), 0.000001f);
    assertEquals(3.1f, getJavaVersionAsFloat("JavaVM-1.3.1"), 0.000001f);  // HP-UX
    assertEquals(0f, getJavaVersionAsFloat("XXX-1.3.x"), 0.000001f);  // error
    assertEquals(8f, getJavaVersionAsFloat("1.8.0_242"), 0.000001f);
    assertEquals(8.2f, getJavaVersionAsFloat("1.8.2_161-b12"), 0.000001f);
    assertEquals(9.04f, getJavaVersionAsFloat("9.0.4+11"), 0.000001f);
    assertEquals(10.81f, getJavaVersionAsFloat("10.8.1_242"), 0.000001f);
    assertEquals(13.02f, getJavaVersionAsFloat("13.0.2"), 0.000001f); // open JDK
  }

  @Test
  public void testJavaVersionAsInt() {
    assertEquals(0, getJavaVersionAsInt(null));
    assertEquals(100, getJavaVersionAsInt("1.1"));
    assertEquals(200, getJavaVersionAsInt("1.2"));
    assertEquals(300, getJavaVersionAsInt("1.3.0"));
    assertEquals(310, getJavaVersionAsInt("1.3.1"));
    assertEquals(400, getJavaVersionAsInt("1.4.0"));
    assertEquals(410, getJavaVersionAsInt("1.4.1"));
    assertEquals(500, getJavaVersionAsInt("1.5.0"));
    assertEquals(600, getJavaVersionAsInt("1.6.0"));
    assertEquals(680, getJavaVersionAsInt("1.6.8_234"));
    assertEquals(310, getJavaVersionAsInt("JavaVM-1.3.1"));  // HP-UX
    assertEquals(0, getJavaVersionAsInt("XXX-1.3.x"));   // error
    assertEquals(800, getJavaVersionAsInt("1.8.0_242"));
    assertEquals(904, getJavaVersionAsInt("9.0.4+11"));
    assertEquals(1081, getJavaVersionAsInt("10.8.1_242"));
    assertEquals(1302, getJavaVersionAsInt("13.0.2")); // open JDK
  }

  @Test
  public void testJavaVersionAtLeastFloat() {
    float version = SystemUtils.JAVA_VERSION_FLOAT;
    assertEquals(true, SystemUtils.isJavaVersionAtLeast(version));
    version -= 0.1f;
    assertEquals(true, SystemUtils.isJavaVersionAtLeast(version));
    version += 0.2f;
    assertEquals(false, SystemUtils.isJavaVersionAtLeast(version));
  }

  @Test
  public void testJavaVersionAtLeastInt() {
    int version = SystemUtils.JAVA_VERSION_INT;
    assertEquals(true, SystemUtils.isJavaVersionAtLeast(version));
    version -= 10;
    assertEquals(true, SystemUtils.isJavaVersionAtLeast(version));
    version += 20;
    assertEquals(false, SystemUtils.isJavaVersionAtLeast(version));
  }

  @Test
  public void testJavaVersionMatches() {
    assertEquals(false, getJavaVersionMatches(null, "1.1"));
    assertEquals(false, getJavaVersionMatches(null, "1.2"));
    assertEquals(false, getJavaVersionMatches(null, "1.3"));
    assertEquals(false, getJavaVersionMatches(null, "1.4"));
    assertEquals(false, getJavaVersionMatches(null, "1.5"));

    assertEquals(true, getJavaVersionMatches("1.1", "1.1"));
    assertEquals(false, getJavaVersionMatches("1.1", "1.2"));
    assertEquals(false, getJavaVersionMatches("1.1", "1.3"));
    assertEquals(false, getJavaVersionMatches("1.1", "1.4"));
    assertEquals(false, getJavaVersionMatches("1.1", "1.5"));

    assertEquals(false, getJavaVersionMatches("1.2", "1.1"));
    assertEquals(true, getJavaVersionMatches("1.2", "1.2"));
    assertEquals(false, getJavaVersionMatches("1.2", "1.3"));
    assertEquals(false, getJavaVersionMatches("1.2", "1.4"));
    assertEquals(false, getJavaVersionMatches("1.2", "1.5"));

    assertEquals(false, getJavaVersionMatches("1.3.0", "1.1"));
    assertEquals(false, getJavaVersionMatches("1.3.0", "1.2"));
    assertEquals(true, getJavaVersionMatches("1.3.0", "1.3"));
    assertEquals(false, getJavaVersionMatches("1.3.0", "1.4"));
    assertEquals(false, getJavaVersionMatches("1.3.0", "1.5"));

    assertEquals(false, getJavaVersionMatches("1.3.1", "1.1"));
    assertEquals(false, getJavaVersionMatches("1.3.1", "1.2"));
    assertEquals(true, getJavaVersionMatches("1.3.1", "1.3"));
    assertEquals(false, getJavaVersionMatches("1.3.1", "1.4"));
    assertEquals(false, getJavaVersionMatches("1.3.1", "1.5"));

    assertEquals(false, getJavaVersionMatches("1.4.0", "1.1"));
    assertEquals(false, getJavaVersionMatches("1.4.0", "1.2"));
    assertEquals(false, getJavaVersionMatches("1.4.0", "1.3"));
    assertEquals(true, getJavaVersionMatches("1.4.0", "1.4"));
    assertEquals(false, getJavaVersionMatches("1.4.0", "1.5"));

    assertEquals(false, getJavaVersionMatches("1.4.1", "1.1"));
    assertEquals(false, getJavaVersionMatches("1.4.1", "1.2"));
    assertEquals(false, getJavaVersionMatches("1.4.1", "1.3"));
    assertEquals(true, getJavaVersionMatches("1.4.1", "1.4"));
    assertEquals(false, getJavaVersionMatches("1.4.1", "1.5"));

    assertEquals(false, getJavaVersionMatches("1.5.0", "1.1"));
    assertEquals(false, getJavaVersionMatches("1.5.0", "1.2"));
    assertEquals(false, getJavaVersionMatches("1.5.0", "1.3"));
    assertEquals(false, getJavaVersionMatches("1.5.0", "1.4"));
    assertEquals(true, getJavaVersionMatches("1.5.0", "1.5"));

    assertEquals(false, getJavaVersionMatches("1.6.0", "1.1"));
    assertEquals(false, getJavaVersionMatches("1.6.0", "1.2"));
    assertEquals(false, getJavaVersionMatches("1.6.0", "1.3"));
    assertEquals(false, getJavaVersionMatches("1.6.0", "1.4"));
    assertEquals(false, getJavaVersionMatches("1.6.0", "1.5"));
  }

  @Test
  public void testOSMatches() {
    assertEquals(false, getOsMatches(null, "Windows"));
    assertEquals(true, getOsMatches("Windows 95", "Windows"));
    assertEquals(true, getOsMatches("Windows NT", "Windows"));
    assertEquals(false, getOsMatches("OS/2", "Windows"));
  }

  @Test
  public void testOSMatches2() {
    assertEquals(false, getOsMatches(null, null, "Windows 9", "4.1"));
    assertEquals(false, getOsMatches("Windows 95", "4.0", "Windows 9", "4.1"));
    assertEquals(true, getOsMatches("Windows 95", "4.1", "Windows 9", "4.1"));
    assertEquals(true, getOsMatches("Windows 98", "4.1", "Windows 9", "4.1"));
    assertEquals(false, getOsMatches("Windows NT", "4.0", "Windows 9", "4.1"));
    assertEquals(false, getOsMatches("OS/2", "4.0", "Windows 9", "4.1"));
  }

  @Test
  public void testOsInfo() {
    System.out.println("OS NAME:" + SystemUtils.OS_NAME);
    System.out.println("OS VERSION:" + SystemUtils.OS_VERSION);
    System.out.println("OS ARCH:" + SystemUtils.OS_ARCH);
  }
}
