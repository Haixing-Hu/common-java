////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

/**
 * The interface of configurations for retry operations.
 *
 * @author Haixing Hu
 */
public interface RetryOptions {

  /**
   * The configuration key of the maximum number of attempts.
   * <p>
   * Note that the number of attempts is one more than the number of retries.
   * That is, if the number of attempts is 1, then there is no retry. If the
   * number of attempts is 2, then there is one retry. If the number of attempts
   * is 3, then there are two retries, and so on.
   */
  String KEY_MAX_ATTEMPTS = "retry.max_attempts";

  /**
   * The configuration key of the minimum delay between retries, in seconds.
   */
  String KEY_RETRY_MIN_DELAY = "retry.min_delay";

  /**
   * The configuration key of the maximum delay between retries, in seconds.
   */
  String KEY_RETRY_MAX_DELAY = "retry.max_delay";

  /**
   * The default maximum number of attempts for the retry mechanism.
   */
  int DEFAULT_MAX_ATTEMPTS = 5;

  /**
   * The default minimum interval between retries in seconds.
   */
  int DEFAULT_RETRY_MIN_DELAY = 1;

  /**
   * The default maximum interval between retries in seconds.
   */
  int DEFAULT_RETRY_MAX_DELAY = 60;

  /**
   * Gets the maximum number of attempts.
   *
   * @return
   *     the maximum number of attempts.
   */
  int getMaxAttempts();

  /**
   * Sets the maximum number of attempts.
   *
   * @param maxAttempts
   *     the maximum number of attempts.
   * @return
   *     this object, to support method chaining.
   */
  RetryOptions setMaxAttempts(int maxAttempts);

  /**
   * Gets the minimum delay between retries, in seconds.
   *
   * @return
   *     the minimum delay between retries, in seconds.
   */
  int getRetryMinDelay();

  /**
   * Sets the minimum delay between retries, in seconds.
   *
   * @param retryMinDelay
   *     the minimum delay between retries, in seconds.
   * @return
   *     this object, to support method chaining.
   */
  RetryOptions setRetryMinDelay(int retryMinDelay);

  /**
   * Gets the maximum delay between retries, in seconds.
   *
   * @return
   *     the maximum delay between retries, in seconds.
   */
  int getRetryMaxDelay();

  /**
   * Sets the maximum delay between retries, in seconds.
   *
   * @param retryMaxDelay
   *     the maximum delay between retries, in seconds.
   * @return
   *     this object, to support method chaining.
   */
  RetryOptions setRetryMaxDelay(int retryMaxDelay);
}