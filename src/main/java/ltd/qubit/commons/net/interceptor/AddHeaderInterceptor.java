////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net.interceptor;

import java.io.IOException;

import javax.annotation.Nonnull;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 向HTTP请求添加头部的拦截器。
 *
 * @author 胡海星
 * @see ltd.qubit.commons.net.HttpClientBuilder
 * @see ltd.qubit.commons.net.ApiBuilder
 */
public class AddHeaderInterceptor implements Interceptor {

  private final String name;
  private final String value;

  /**
   * 创建新的 {@link AddHeaderInterceptor} 实例。
   *
   * @param name
   *     要添加的头部名称。
   * @param value
   *     要添加的头部值。
   */
  public AddHeaderInterceptor(final String name, final String value) {
    this.name = requireNonNull("name", name);
    this.value = requireNonNull("value", value);
  }

  /**
   * 拦截HTTP请求并添加头部。
   *
   * @param chain
   *     拦截器链。
   * @return
   *     拦截后的响应。
   * @throws IOException
   *     如果发生IO错误。
   */
  @Nonnull
  @Override
  public Response intercept(final Chain chain) throws IOException {
    final Request request = chain
        .request()
        .newBuilder()
        .addHeader(name, value)
        .build();
    return chain.proceed(request);
  }
}