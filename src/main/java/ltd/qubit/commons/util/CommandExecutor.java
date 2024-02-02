////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2024.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import javax.annotation.Nullable;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.io.FileUtils;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A utility class for executing external commands.
 *
 * @author Haixing Hu
 */
public class CommandExecutor {

  /**
   * The default timeout for executing external commands, in seconds.
   */
  public static final int DEFAULT_EXECUTION_TIMEOUT_IN_SECONDS = 10;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private Duration timeout = Duration.ofSeconds(DEFAULT_EXECUTION_TIMEOUT_IN_SECONDS);

  private int exitValueOnSuccess = 0;

  private int exitValue = 0;

  @Nullable
  private String workingDirectory = null;

  /**
   * Gets the timeout for executing external commands.
   *
   * @return
   *     the timeout for executing external commands.
   */
  public Duration getTimeout() {
    return timeout;
  }

  /**
   * Sets the timeout for executing external commands.
   *
   * @param timeout
   *     the timeout for executing external commands.
   */
  public void setTimeout(final Duration timeout) {
    this.timeout = requireNonNull("timeout", timeout);
  }

  /**
   * Gets the exit value of the process to be considered successful.
   *
   * @return
   *     the exit value of the process to be considered successful.
   */
  public int getExitValueOnSuccess() {
    return exitValueOnSuccess;
  }

  /**
   * Sets the exit value of the process to be considered successful.
   * <p>
   * If a different exit value is returned by the process then execution will
   * throw an ExecuteException.
   *
   * @param exitValueOnSuccess
   *     the exit value of the process to be considered successful.
   */
  public void setExitValueOnSuccess(final int exitValueOnSuccess) {
    this.exitValueOnSuccess = exitValueOnSuccess;
  }

  /**
   * Gets the exit value of the last executed process.
   *
   * @return
   *     the exit value of the last executed process.
   */
  public int getExitValue() {
    return exitValue;
  }

  /**
   * Gets the path of the working directory for executing external commands.
   * <p>
   * If this property is {@code null}, the default working directory is used.
   * The default working directory is the temporary directory of the system.
   * If the system property {@code java.io.tmpdir} is not defined, then the
   * current directory "." is used as the default working directory.
   *
   * @return
   *     the path of the working directory for executing external commands, or
   *     {@code null} if the default working directory is used.
   */
  @Nullable
  public String getWorkingDirectory() {
    return workingDirectory;
  }

  /**
   * Sets the path of the working directory for executing external commands.
   * <p>
   * If this property is {@code null}, the default working directory is used.
   * The default working directory is the temporary directory of the system.
   * If the system property {@code java.io.tmpdir} is not defined, then the
   * current directory "." is used as the default working directory.
   *
   * @param workingDirectory
   *     the path of the working directory for executing external commands, or
   *     {@code null} to use the default working directory.
   */
  public void setWorkingDirectory(@Nullable final String workingDirectory) {
    this.workingDirectory = workingDirectory;
  }

  /**
   * Executes an external command.
   *
   * @param cmd
   *     the line of command to be executed.
   * @return
   *     the output of the command, or {@code null} if the command execution
   *     failed. If the command executed successfully without any output,
   *     this function returns an empty string.
   */
  public String execute(final String cmd) {
    logger.info("Executing the command: {}", cmd);
    final CommandLine commandLine = CommandLine.parse(cmd);
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final PumpStreamHandler streamHandler = new PumpStreamHandler(out);
    final File workingDir = findWorkingDirectory();
    final DefaultExecutor executor = DefaultExecutor
        .builder()
        .setExecuteStreamHandler(streamHandler)
        .setWorkingDirectory(workingDir)
        .get();
    final ExecuteWatchdog watchdog = ExecuteWatchdog
        .builder()
        .setTimeout(timeout)
        .get();
    executor.setWatchdog(watchdog);
    executor.setExitValue(exitValueOnSuccess);
    try {
      final long before = System.currentTimeMillis();
      exitValue = executor.execute(commandLine);
      final long after = System.currentTimeMillis();
      logger.info("Finished executing command in {} milliseconds.", after - before);
    } catch (final IOException e) {
      logger.error("Failed to execute the command {}: {}", cmd, e.getMessage(), e);
      return null;
    }
    final String output = out.toString();
    if (exitValue != exitValueOnSuccess) {
      logger.error("Execution of the command failed with exit value {}: {}",
          exitValue, cmd);
      logger.error("The output of the command is: {}", output);
      return null;
    }
    logger.debug("The output of the command is: {}", output);
    return output;
  }

  private File findWorkingDirectory() {
    if (workingDirectory != null) {
      return new File(workingDirectory);
    } else {
      try {
        return FileUtils.getTempDirectory();
      } catch (final IOException e) {
        logger.warn("Cannot get the temporary directory: {}, "
            + "use the current directory instead.", e.getMessage(), e);
        return new File(".");
      }
    }
  }
}