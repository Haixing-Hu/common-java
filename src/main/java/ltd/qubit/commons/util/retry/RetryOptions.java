////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.retry;

import java.io.Serializable;

/**
 * 重试操作的配置接口。
 *
 * @author 胡海星
 */
public interface RetryOptions extends Serializable {

  /**
   * 最大尝试次数的配置键。
   * <p>
   * 请注意,尝试次数比重试次数多一次。也就是说,如果尝试次数为1,则不重试。
   * 如果尝试次数为2,则重试一次。如果尝试次数为3,则重试两次,依此类推。
   */
  String KEY_MAX_RETRY_ATTEMPTS = "retry.max_attempts";

  /**
   * 重试之间的最小延迟(以秒为单位)的配置键。
   */
  String KEY_MIN_RETRY_DELAY = "retry.min_delay";

  /**
   * 重试之间的最大延迟(以秒为单位)的配置键。
   */
  String KEY_MAX_RETRY_DELAY = "retry.max_delay";

  /**
   * 重试机制的默认最大尝试次数。
   */
  int DEFAULT_MAX_RETRY_ATTEMPTS = 5;

  /**
   * 重试之间的默认最小间隔(以秒为单位)。
   */
  int DEFAULT_MIN_RETRY_DELAY = 1;

  /**
   * 重试之间的默认最大间隔(以秒为单位)。
   */
  int DEFAULT_MAX_RETRY_DELAY = 60;

  /**
   * 获取最大尝试次数。
   *
   * @return
   *     最大尝试次数。
   */
  int getMaxRetryAttempts();

  /**
   * 设置最大尝试次数。
   *
   * @param maxRetryAttempts
   *     最大尝试次数。
   * @return
   *     此对象,以支持方法链接。
   */
  RetryOptions setMaxRetryAttempts(int maxRetryAttempts);

  /**
   * 获取重试之间的最小延迟(以秒为单位)。
   *
   * @return
   *     重试之间的最小延迟(以秒为单位)。
   */
  int getMinRetryDelay();

  /**
   * 设置重试之间的最小延迟(以秒为单位)。
   *
   * @param minRetryDelay
   *     重试之间的最小延迟(以秒为单位)。
   * @return
   *     此对象,以支持方法链接。
   */
  RetryOptions setMinRetryDelay(int minRetryDelay);

  /**
   * 获取重试之间的最大延迟(以秒为单位)。
   *
   * @return
   *     重试之间的最大延迟(以秒为单位)。
   */
  int getMaxRetryDelay();

  /**
   * 设置重试之间的最大延迟(以秒为单位)。
   *
   * @param maxRetryDelay
   *     重试之间的最大延迟(以秒为单位)。
   * @return
   *     此对象,以支持方法链接。
   */
  RetryOptions setMaxRetryDelay(int maxRetryDelay);
}