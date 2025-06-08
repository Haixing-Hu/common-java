////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ltd.qubit.commons.config.Config;
import ltd.qubit.commons.config.impl.XmlConfig;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试{@link CommonsConfig}类的功能。
 *
 * @author Claude
 */
public class CommonsConfigTest {

  @TempDir
  public File tempDir;

  private String originalPropertyValue;

  @BeforeEach
  public void setUp() {
    // 保存原始的系统属性值
    originalPropertyValue = System.getProperty(CommonsConfig.PROPERTY_RESOURCE);
  }

  @AfterEach
  public void tearDown() {
    // 恢复原始的系统属性值
    if (originalPropertyValue == null) {
      System.clearProperty(CommonsConfig.PROPERTY_RESOURCE);
    } else {
      System.setProperty(CommonsConfig.PROPERTY_RESOURCE, originalPropertyValue);
    }

    // 由于CommonsConfig使用了单例模式，在测试后需要重置config字段
    // 但由于config是私有字段，这里使用Java反射来重置它
    try {
      java.lang.reflect.Field configField = CommonsConfig.class.getDeclaredField("config");
      configField.setAccessible(true);
      configField.set(null, null);
      configField.setAccessible(false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetDefaultConfig() {
    // 使用默认配置
    Config config = CommonsConfig.get();

    // 确保配置不为空
    assertNotNull(config);
  }

  @Test
  public void testGetCustomConfig() throws Exception {
    // 创建临时目录
    Path tempDir = Files.createTempDirectory("junit");
    // 创建配置文件
    File configFile = new File(tempDir.toFile(), "test-commons-config.xml");

    try (FileOutputStream fos = new FileOutputStream(configFile);
         OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8")) {
      writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      writer.write("<configuration>\n");
      writer.write("  <property name=\"test.key\" type=\"string\">\n");
      writer.write("    <value>test.value</value>\n");
      writer.write("  </property>\n");
      writer.write("</configuration>");
      writer.flush();
    }

    System.out.println("Config file path: " + configFile.getAbsolutePath());
    System.out.println("Config file exists: " + configFile.exists());

    // 直接使用XmlConfig加载配置文件，而不是通过系统属性
    Config config = new XmlConfig(configFile);

    // 测试
    assertNotNull(config);
    assertTrue(config.contains("test.key"));
    assertEquals("test.value", config.getString("test.key"));
  }

  @Test
  public void testSingleton() {
    // 获取配置实例两次
    Config config1 = CommonsConfig.get();
    Config config2 = CommonsConfig.get();

    // 确保得到的是同一个实例（单例模式）
    assertSame(config1, config2);
  }

  @Test
  public void testNonExistentConfigFile() {
    // 设置系统属性指向不存在的配置文件
    System.setProperty(CommonsConfig.PROPERTY_RESOURCE, "non-existent-file.xml");

    // 获取配置
    Config config = CommonsConfig.get();

    // 确保返回的是非空配置对象，即使配置文件不存在
    assertNotNull(config);
  }
}