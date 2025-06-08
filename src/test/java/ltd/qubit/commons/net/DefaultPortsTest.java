////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.CommonsConfig;
import ltd.qubit.commons.config.Config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test of the {@link DefaultPorts} class.
 *
 * @author Haixing Hu
 */
public class DefaultPortsTest {

  @Test
  public void testDefaultPorts() {
    assertEquals(21, DefaultPorts.get("ftp"));
    assertEquals(70, DefaultPorts.get("gopher"));
    assertEquals(80, DefaultPorts.get("http"));
    assertEquals(443, DefaultPorts.get("https"));
    assertEquals(110, DefaultPorts.get("pop3"));
    assertEquals(143, DefaultPorts.get("imap"));
    assertEquals(993, DefaultPorts.get("imaps"));
    assertEquals(995, DefaultPorts.get("pop3s"));
    assertEquals(25, DefaultPorts.get("smtp"));
    assertEquals(465, DefaultPorts.get("smtps"));
    assertEquals(587, DefaultPorts.get("submission"));
  }

  @Test
  public void testUnknownScheme() {
    assertEquals(-1, DefaultPorts.get("unknown"));
    assertEquals(-1, DefaultPorts.get("nonexistent"));
    assertEquals(-1, DefaultPorts.get(""));
  }

  @Test
  public void testNullScheme() {
    assertEquals(-1, DefaultPorts.get(null));
  }

  @Test
  public void testLazyInitialization() {
    // 第一次调用会触发懒加载
    final int port1 = DefaultPorts.get("http");
    assertEquals(80, port1);

    // 后续调用应该使用已缓存的数据
    final int port2 = DefaultPorts.get("http");
    assertEquals(80, port2);

    // 测试不同的端口
    assertEquals(443, DefaultPorts.get("https"));
  }

  @Test
  public void testThreadSafety() throws InterruptedException {
    final int threadCount = 10;
    final int operationsPerThread = 100;
    final ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    final CountDownLatch latch = new CountDownLatch(threadCount);
    final AtomicInteger successCount = new AtomicInteger(0);
    final AtomicInteger errorCount = new AtomicInteger(0);

    // 启动多个线程同时访问 DefaultPorts.get()
    for (int i = 0; i < threadCount; i++) {
      executor.submit(() -> {
        try {
          for (int j = 0; j < operationsPerThread; j++) {
            final int httpPort = DefaultPorts.get("http");
            final int httpsPort = DefaultPorts.get("https");
            final int ftpPort = DefaultPorts.get("ftp");

            if (httpPort == 80 && httpsPort == 443 && ftpPort == 21) {
              successCount.incrementAndGet();
            } else {
              errorCount.incrementAndGet();
            }
          }
        } finally {
          latch.countDown();
        }
      });
    }

    // 等待所有线程完成
    assertTrue(latch.await(30, TimeUnit.SECONDS));
    executor.shutdown();

    // 验证所有操作都成功
    assertEquals(threadCount * operationsPerThread, successCount.get());
    assertEquals(0, errorCount.get());
  }

  @Test
  public void testConcurrentInitialization() throws InterruptedException {
    // 这个测试难以直接验证，但我们可以确保多线程同时调用时不会出现异常
    final int threadCount = 20;
    final ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    final CountDownLatch startLatch = new CountDownLatch(1);
    final CountDownLatch finishLatch = new CountDownLatch(threadCount);
    final AtomicInteger successCount = new AtomicInteger(0);

    // 启动多个线程，让它们同时开始调用
    for (int i = 0; i < threadCount; i++) {
      executor.submit(() -> {
        try {
          startLatch.await(); // 等待信号，同时开始
          final int port = DefaultPorts.get("http");
          if (port == 80) {
            successCount.incrementAndGet();
          }
        } catch (final Exception e) {
          // 记录异常但不抛出
          e.printStackTrace();
        } finally {
          finishLatch.countDown();
        }
      });
    }

    // 发出开始信号
    startLatch.countDown();

    // 等待所有线程完成
    assertTrue(finishLatch.await(10, TimeUnit.SECONDS));
    executor.shutdown();

    // 验证所有线程都成功获取了正确的端口
    assertEquals(threadCount, successCount.get());
  }

  @Test
  public void testConfigurationProperty() {
    // 测试配置属性的使用
    final Config config = CommonsConfig.get();
    final String defaultResource = config.getString(DefaultPorts.PROPERTY_RESOURCE,
        DefaultPorts.DEFAULT_RESOURCE);
    assertEquals(DefaultPorts.DEFAULT_RESOURCE, defaultResource);
  }

  @Test
  public void testCommonProtocols() {
    // 测试常见协议的端口
    assertEquals(53, DefaultPorts.get("dns"));
    assertEquals(22, DefaultPorts.get("ssh"));
    assertEquals(23, DefaultPorts.get("telnet"));
    assertEquals(69, DefaultPorts.get("tftp"));
    assertEquals(161, DefaultPorts.get("snmp"));
    assertEquals(162, DefaultPorts.get("snmptrap"));
  }

  @Test
  public void testCaseSensitivity() {
    // 测试协议名称的大小写敏感性
    assertEquals(80, DefaultPorts.get("http"));
    assertEquals(-1, DefaultPorts.get("HTTP")); // 假设配置文件中使用小写
    assertEquals(-1, DefaultPorts.get("Http"));
  }

  @Test
  public void testRepeatedAccess() {
    // 测试重复访问相同的协议
    final String scheme = "https";
    final int expectedPort = 443;

    for (int i = 0; i < 1000; i++) {
      assertEquals(expectedPort, DefaultPorts.get(scheme));
    }
  }
}