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
import java.util.Map;

import javax.annotation.Nonnull;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import ltd.qubit.commons.net.ApiBuilder;
import ltd.qubit.commons.net.HttpClientBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 向HTTP请求添加多个头部的拦截器。
 *
 * @author 胡海星
 * @see HttpClientBuilder
 * @see ApiBuilder
 */
public class AddHeadersInterceptor implements Interceptor {

  private final Map<String, String> headers;

  /**
   * 创建新的 {@link AddHeadersInterceptor} 实例。
   *
   * @param headers
   *     要添加的头部映射。
   */
  public AddHeadersInterceptor(final Map<String, String> headers) {
    this.headers = requireNonNull("headers", headers);
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
    final Request.Builder builder = chain.request().newBuilder();
    for (final Map.Entry<String, String> entry : headers.entrySet()) {
      builder.addHeader(entry.getKey(), entry.getValue());
    }
    final Request request = builder.build();
    return chain.proceed(request);
  }
}