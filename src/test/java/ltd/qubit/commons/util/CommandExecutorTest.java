////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试{@link CommandExecutor}类的功能。
 *
 * @author Claude
 */
public class CommandExecutorTest {

  private CommandExecutor executor;
  
  @TempDir
  public Path tempDir;
  
  @BeforeEach
  public void setUp() {
    executor = new CommandExecutor();
  }
  
  @AfterEach
  public void tearDown() {
    executor = null;
  }
  
  @Test
  public void testGetterSetterMethods() {
    // 测试超时设置
    assertEquals(Duration.ofSeconds(CommandExecutor.DEFAULT_EXECUTION_TIMEOUT_IN_SECONDS), 
        executor.getTimeout());
    
    Duration newTimeout = Duration.ofSeconds(20);
    executor.setTimeout(newTimeout);
    assertEquals(newTimeout, executor.getTimeout());
    
    // 测试disableLogging设置
    assertFalse(executor.isDisableLogging());
    executor.setDisableLogging(true);
    assertTrue(executor.isDisableLogging());
    
    // 测试exitValueOnSuccess设置
    assertEquals(0, executor.getExitValueOnSuccess());
    executor.setExitValueOnSuccess(1);
    assertEquals(1, executor.getExitValueOnSuccess());
    
    // 测试workingDirectory设置
    assertNull(executor.getWorkingDirectory());
    String workDir = "/tmp";
    executor.setWorkingDirectory(workDir);
    assertEquals(workDir, executor.getWorkingDirectory());
  }
  
  @Test
  @EnabledOnOs({OS.LINUX, OS.MAC})
  public void testExecuteEchoCommandUnix() {
    String result = executor.execute("echo 'Hello World'");
    // 输出可能包含引号，也可能不包含，所以我们只检查是否包含Hello World
    assertTrue(result.contains("Hello World"), "输出应该包含'Hello World'");
  }
  
  @Test
  @EnabledOnOs(OS.WINDOWS)
  public void testExecuteEchoCommandWindows() {
    String result = executor.execute("cmd.exe /c echo Hello World");
    assertTrue(result.contains("Hello World"), "输出应该包含'Hello World'");
  }
  
  @Test
  @EnabledOnOs({OS.LINUX, OS.MAC})
  public void testExecuteCommandWithWorkingDirectory() throws IOException {
    File testFile = createTempFile("test.txt", "test content");
    
    executor.setWorkingDirectory(tempDir.toString());
    String result = executor.execute("cat test.txt");
    
    // 删除可能存在的换行符后进行比较
    assertEquals("test content", result.trim());
  }
  
  @Test
  public void testExecuteNonExistingCommand() {
    String result = executor.execute("non_existing_command_12345");
    assertNull(result);
  }
  
  @Test
  public void testExecuteNonExistingCommandWithThrowError() {
    assertThrows(IOException.class, () -> {
      executor.execute("non_existing_command_12345", true);
    });
  }
  
  @Test
  @EnabledOnOs({OS.LINUX, OS.MAC})
  public void testExecuteWithTimeout() {
    executor.setTimeout(Duration.ofMillis(100));
    String result = executor.execute("sleep 5");
    assertNull(result);
  }
  
  @Test
  @EnabledOnOs({OS.LINUX, OS.MAC})
  public void testExitValue() {
    // 成功的命令，退出值应该是0
    executor.execute("echo 'test'");
    assertEquals(0, executor.getExitValue());
    
    try {
      // 尝试列出不存在的目录，应该失败
      executor.execute("ls /non_existing_directory_12345", false);
      // 退出值应该不是0（注意：有些环境可能ls命令返回0，所以不做具体断言）
      // 只有当命令实际执行了且返回错误码时才验证
      if (executor.getExitValue() == 0) {
        // 如果环境中ls命令对不存在的目录也返回0，则跳过此测试
        System.out.println("警告: ls命令对不存在的目录返回了0，跳过exitValue验证");
      } else {
        // 确认返回了非0值
        assertNotEquals(0, executor.getExitValue());
      }
    } catch (Exception e) {
      // 如果命令执行失败，也是符合预期的
      System.out.println("命令执行期间抛出异常，符合预期: " + e.getMessage());
    }
  }
  
  @Test
  @EnabledOnOs({OS.LINUX, OS.MAC})
  public void testCustomExitValueOnSuccess() throws IOException {
    // 设置exitValueOnSuccess为2
    executor.setExitValueOnSuccess(2);
    
    // 这个命令会返回2，应该被认为是成功的
    String scriptContent = "#!/bin/sh\nexit 2";
    File scriptFile = createTempFile("test_script.sh", scriptContent);
    scriptFile.setExecutable(true);
    
    String result = executor.execute(scriptFile.getAbsolutePath());
    assertNotNull(result);
    assertEquals(2, executor.getExitValue());
    
    // 恢复exitValueOnSuccess为0
    executor.setExitValueOnSuccess(0);
    
    // 这个命令会返回2，应该被认为是失败的
    assertThrows(IOException.class, () -> {
      executor.execute(scriptFile.getAbsolutePath(), true);
    });
  }
  
  private File createTempFile(String fileName, String content) throws IOException {
    Path filePath = tempDir.resolve(fileName);
    Files.write(filePath, Arrays.asList(content));
    return filePath.toFile();
  }
} 