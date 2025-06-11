////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;

/**
 * 简单的 {@link Resource} 实现，持有资源描述但不指向实际可读的资源。
 * <p>
 * 当 API 期望 {@code Resource} 参数但不一定用于实际读取时，用作占位符。
 * <p>
 * 此类是 {@code org.springframework.core.io.DescriptiveResource} 的副本，
 * 略有修改。用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author 胡海星
 */
public class DescriptiveResource extends AbstractResource {

  private final String description;

  /**
   * 创建新的 DescriptiveResource。
   * @param description 资源描述
   */
  public DescriptiveResource(@Nullable final String description) {
    this.description = (description != null ? description : "");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean exists() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isReadable() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public InputStream getInputStream() throws IOException {
    throw new FileNotFoundException(getDescription()
        + " cannot be opened because it does not point to a readable resource");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return this.description;
  }

  /**
   * 此实现比较底层的描述字符串。
   */
  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof DescriptiveResource)) {
      return false;
    }
    final DescriptiveResource that = (DescriptiveResource) other;
    return this.description.equals(that.description);
  }

  /**
   * 此实现返回底层描述字符串的哈希码。
   */
  @Override
  public int hashCode() {
    return this.description.hashCode();
  }
}