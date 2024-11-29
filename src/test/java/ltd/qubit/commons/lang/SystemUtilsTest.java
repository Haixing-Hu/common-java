////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.text.jackson.module.type.TypeRegister;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
      assertFalse(SystemUtils.IS_JAVA_1);
      assertFalse(SystemUtils.IS_JAVA_2);
      assertFalse(SystemUtils.IS_JAVA_3);
      assertFalse(SystemUtils.IS_JAVA_4);
      assertFalse(SystemUtils.IS_JAVA_5);
      assertFalse(SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.1")) {
      assertTrue(SystemUtils.IS_JAVA_1);
      assertFalse(SystemUtils.IS_JAVA_2);
      assertFalse(SystemUtils.IS_JAVA_3);
      assertFalse(SystemUtils.IS_JAVA_4);
      assertFalse(SystemUtils.IS_JAVA_5);
      assertFalse(SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.2")) {
      assertFalse(SystemUtils.IS_JAVA_1);
      assertTrue(SystemUtils.IS_JAVA_2);
      assertFalse(SystemUtils.IS_JAVA_3);
      assertFalse(SystemUtils.IS_JAVA_4);
      assertFalse(SystemUtils.IS_JAVA_5);
      assertFalse(SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.3")) {
      assertFalse(SystemUtils.IS_JAVA_1);
      assertFalse(SystemUtils.IS_JAVA_2);
      assertTrue(SystemUtils.IS_JAVA_3);
      assertFalse(SystemUtils.IS_JAVA_4);
      assertFalse(SystemUtils.IS_JAVA_5);
      assertFalse(SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.4")) {
      assertFalse(SystemUtils.IS_JAVA_1);
      assertFalse(SystemUtils.IS_JAVA_2);
      assertFalse(SystemUtils.IS_JAVA_3);
      assertTrue(SystemUtils.IS_JAVA_4);
      assertFalse(SystemUtils.IS_JAVA_5);
      assertFalse(SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.5")) {
      assertFalse(SystemUtils.IS_JAVA_1);
      assertFalse(SystemUtils.IS_JAVA_2);
      assertFalse(SystemUtils.IS_JAVA_3);
      assertFalse(SystemUtils.IS_JAVA_4);
      assertTrue(SystemUtils.IS_JAVA_5);
      assertFalse(SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.6")) {
      assertFalse(SystemUtils.IS_JAVA_1);
      assertFalse(SystemUtils.IS_JAVA_2);
      assertFalse(SystemUtils.IS_JAVA_3);
      assertFalse(SystemUtils.IS_JAVA_4);
      assertFalse(SystemUtils.IS_JAVA_5);
      assertTrue(SystemUtils.IS_JAVA_6);
    } else if (javaVersion.startsWith("1.7")) {
      assertFalse(SystemUtils.IS_JAVA_1);
      assertFalse(SystemUtils.IS_JAVA_2);
      assertFalse(SystemUtils.IS_JAVA_3);
      assertFalse(SystemUtils.IS_JAVA_4);
      assertFalse(SystemUtils.IS_JAVA_5);
      assertFalse(SystemUtils.IS_JAVA_6);
      assertTrue(SystemUtils.IS_JAVA_7);
    } else if (javaVersion.startsWith("1.8")) {
      assertFalse(SystemUtils.IS_JAVA_1);
      assertFalse(SystemUtils.IS_JAVA_2);
      assertFalse(SystemUtils.IS_JAVA_3);
      assertFalse(SystemUtils.IS_JAVA_4);
      assertFalse(SystemUtils.IS_JAVA_5);
      assertFalse(SystemUtils.IS_JAVA_6);
      assertFalse(SystemUtils.IS_JAVA_7);
      assertTrue(SystemUtils.IS_JAVA_8);
    } else if (javaVersion.startsWith("1.9")) {
      assertFalse(SystemUtils.IS_JAVA_1);
      assertFalse(SystemUtils.IS_JAVA_2);
      assertFalse(SystemUtils.IS_JAVA_3);
      assertFalse(SystemUtils.IS_JAVA_4);
      assertFalse(SystemUtils.IS_JAVA_5);
      assertFalse(SystemUtils.IS_JAVA_6);
      assertFalse(SystemUtils.IS_JAVA_7);
      assertFalse(SystemUtils.IS_JAVA_8);
      assertTrue(SystemUtils.IS_JAVA_9);
    } else {
      System.out.println("Can't test IS_JAVA value");
    }
  }

  @Test
  public void testIS_OS() {
    final String osName = System.getProperty("os.name");
    if (osName == null) {
      assertFalse(SystemUtils.IS_OS_WINDOWS);
      assertFalse(SystemUtils.IS_OS_UNIX);
      assertFalse(SystemUtils.IS_OS_SOLARIS);
      assertFalse(SystemUtils.IS_OS_LINUX);
      assertFalse(SystemUtils.IS_OS_MAC_OSX);
    } else if (osName.startsWith("Windows")) {
      assertFalse(SystemUtils.IS_OS_UNIX);
      assertTrue(SystemUtils.IS_OS_WINDOWS);
    } else if (osName.startsWith("Solaris")) {
      assertTrue(SystemUtils.IS_OS_SOLARIS);
      assertTrue(SystemUtils.IS_OS_UNIX);
      assertFalse(SystemUtils.IS_OS_WINDOWS);
    } else if (osName.toLowerCase().startsWith("linux")) {
      assertTrue(SystemUtils.IS_OS_LINUX);
      assertTrue(SystemUtils.IS_OS_UNIX);
      assertFalse(SystemUtils.IS_OS_WINDOWS);
    } else if (osName.startsWith("Mac OS X")) {
      assertTrue(SystemUtils.IS_OS_MAC_OSX);
      assertTrue(SystemUtils.IS_OS_UNIX);
      assertFalse(SystemUtils.IS_OS_WINDOWS);
    } else if (osName.startsWith("OS/2")) {
      assertTrue(SystemUtils.IS_OS_OS2);
      assertFalse(SystemUtils.IS_OS_UNIX);
      assertFalse(SystemUtils.IS_OS_WINDOWS);
    } else if (osName.startsWith("SunOS")) {
      assertTrue(SystemUtils.IS_OS_SUN_OS);
      assertTrue(SystemUtils.IS_OS_UNIX);
      assertFalse(SystemUtils.IS_OS_WINDOWS);
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
    assertTrue(SystemUtils.isJavaVersionAtLeast(version));
    version -= 0.1f;
    assertTrue(SystemUtils.isJavaVersionAtLeast(version));
    version += 0.2f;
    assertFalse(SystemUtils.isJavaVersionAtLeast(version));
  }

  @Test
  public void testJavaVersionAtLeastInt() {
    int version = SystemUtils.JAVA_VERSION_INT;
    assertTrue(SystemUtils.isJavaVersionAtLeast(version));
    version -= 10;
    assertTrue(SystemUtils.isJavaVersionAtLeast(version));
    version += 20;
    assertFalse(SystemUtils.isJavaVersionAtLeast(version));
  }

  @Test
  public void testJavaVersionMatches() {
    assertFalse(getJavaVersionMatches(null, "1.1"));
    assertFalse(getJavaVersionMatches(null, "1.2"));
    assertFalse(getJavaVersionMatches(null, "1.3"));
    assertFalse(getJavaVersionMatches(null, "1.4"));
    assertFalse(getJavaVersionMatches(null, "1.5"));

    assertTrue(getJavaVersionMatches("1.1", "1.1"));
    assertFalse(getJavaVersionMatches("1.1", "1.2"));
    assertFalse(getJavaVersionMatches("1.1", "1.3"));
    assertFalse(getJavaVersionMatches("1.1", "1.4"));
    assertFalse(getJavaVersionMatches("1.1", "1.5"));

    assertFalse(getJavaVersionMatches("1.2", "1.1"));
    assertTrue(getJavaVersionMatches("1.2", "1.2"));
    assertFalse(getJavaVersionMatches("1.2", "1.3"));
    assertFalse(getJavaVersionMatches("1.2", "1.4"));
    assertFalse(getJavaVersionMatches("1.2", "1.5"));

    assertFalse(getJavaVersionMatches("1.3.0", "1.1"));
    assertFalse(getJavaVersionMatches("1.3.0", "1.2"));
    assertTrue(getJavaVersionMatches("1.3.0", "1.3"));
    assertFalse(getJavaVersionMatches("1.3.0", "1.4"));
    assertFalse(getJavaVersionMatches("1.3.0", "1.5"));

    assertFalse(getJavaVersionMatches("1.3.1", "1.1"));
    assertFalse(getJavaVersionMatches("1.3.1", "1.2"));
    assertTrue(getJavaVersionMatches("1.3.1", "1.3"));
    assertFalse(getJavaVersionMatches("1.3.1", "1.4"));
    assertFalse(getJavaVersionMatches("1.3.1", "1.5"));

    assertFalse(getJavaVersionMatches("1.4.0", "1.1"));
    assertFalse(getJavaVersionMatches("1.4.0", "1.2"));
    assertFalse(getJavaVersionMatches("1.4.0", "1.3"));
    assertTrue(getJavaVersionMatches("1.4.0", "1.4"));
    assertFalse(getJavaVersionMatches("1.4.0", "1.5"));

    assertFalse(getJavaVersionMatches("1.4.1", "1.1"));
    assertFalse(getJavaVersionMatches("1.4.1", "1.2"));
    assertFalse(getJavaVersionMatches("1.4.1", "1.3"));
    assertTrue(getJavaVersionMatches("1.4.1", "1.4"));
    assertFalse(getJavaVersionMatches("1.4.1", "1.5"));

    assertFalse(getJavaVersionMatches("1.5.0", "1.1"));
    assertFalse(getJavaVersionMatches("1.5.0", "1.2"));
    assertFalse(getJavaVersionMatches("1.5.0", "1.3"));
    assertFalse(getJavaVersionMatches("1.5.0", "1.4"));
    assertTrue(getJavaVersionMatches("1.5.0", "1.5"));

    assertFalse(getJavaVersionMatches("1.6.0", "1.1"));
    assertFalse(getJavaVersionMatches("1.6.0", "1.2"));
    assertFalse(getJavaVersionMatches("1.6.0", "1.3"));
    assertFalse(getJavaVersionMatches("1.6.0", "1.4"));
    assertFalse(getJavaVersionMatches("1.6.0", "1.5"));
  }

  @Test
  public void testOSMatches() {
    assertFalse(getOsMatches(null, "Windows"));
    assertTrue(getOsMatches("Windows 95", "Windows"));
    assertTrue(getOsMatches("Windows NT", "Windows"));
    assertFalse(getOsMatches("OS/2", "Windows"));
  }

  @Test
  public void testOSMatches2() {
    assertFalse(getOsMatches(null, null, "Windows 9", "4.1"));
    assertFalse(getOsMatches("Windows 95", "4.0", "Windows 9", "4.1"));
    assertTrue(getOsMatches("Windows 95", "4.1", "Windows 9", "4.1"));
    assertTrue(getOsMatches("Windows 98", "4.1", "Windows 9", "4.1"));
    assertFalse(getOsMatches("Windows NT", "4.0", "Windows 9", "4.1"));
    assertFalse(getOsMatches("OS/2", "4.0", "Windows 9", "4.1"));
  }

  @Test
  public void testOsInfo() {
    System.out.println("OS NAME:" + SystemUtils.OS_NAME);
    System.out.println("OS VERSION:" + SystemUtils.OS_VERSION);
    System.out.println("OS ARCH:" + SystemUtils.OS_ARCH);
  }

  @Test
  public void testLoadInstance() throws IOException, ClassNotFoundException {
    final String resource = "META-INF/services/" + TypeRegister.class.getName();
    @SuppressWarnings("rawtypes")
    final List<TypeRegister> registerList = SystemUtils.loadInstance(TypeRegister.class,
        resource, this.getClass().getClassLoader());
    assertNotEquals(0, registerList.size());
    for (final TypeRegister<?> register : registerList) {
      assertNotNull(register);
      System.out.println(register);
    }
  }
}
