////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.beans.Introspector;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link ModuleResource}.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 6.1
 */
class ModuleResourceTest {

  private static final String existingPath = "java/beans/Introspector.class";
  private static final String nonExistingPath = "org/example/NonExistingClass.class";

  @Test
  void existingClassFileResource() throws IOException {
    // Check expected behavior of ClassPathResource first.
    final ClassPathResource cpr = new ClassPathResource(existingPath);
    assertExistingResource(cpr);
    assertThat(cpr.getDescription())
        .startsWith("class path resource")
        .contains(cpr.getPath());

    final ModuleResource mr = new ModuleResource(Introspector.class.getModule(), existingPath);
    assertExistingResource(mr);
    assertThat(mr.getDescription())
        .startsWith("module resource")
        .contains(mr.getModule().getName(), mr.getPath());
    System.err.println(mr.getDescription());

    assertThat(mr.getContentAsByteArray())
        .isEqualTo(cpr.getContentAsByteArray());
    assertThat(mr.contentLength())
        .isEqualTo(cpr.contentLength());
  }

  private static void assertExistingResource(final Resource resource) {
    assertThat(resource.exists()).isTrue();
    assertThat(resource.isReadable()).isTrue();
    assertThat(resource.isOpen()).isFalse();
    assertThat(resource.isFile()).isFalse();
    assertThat(resource.getFilename()).isEqualTo("Introspector.class");
  }

  @Test
  void nonExistingResource() {
    final ModuleResource mr = new ModuleResource(Introspector.class.getModule(), nonExistingPath);
    assertThat(mr.exists()).isFalse();
    assertThat(mr.isReadable()).isFalse();
    assertThat(mr.isOpen()).isFalse();
    assertThat(mr.isFile()).isFalse();
    assertThat(mr.getFilename())
        .isEqualTo("NonExistingClass.class");
    assertThat(mr.getDescription())
        .startsWith("module resource")
        .contains(mr.getModule().getName(), mr.getPath());

    assertThatExceptionOfType(FileNotFoundException.class)
        .isThrownBy(mr::getContentAsByteArray);
    assertThatExceptionOfType(FileNotFoundException.class)
        .isThrownBy(mr::contentLength);
  }

  @Test
  void equalsAndHashCode() {
    final Resource resource1 = new ModuleResource(Introspector.class.getModule(), existingPath);
    final Resource resource2 = new ModuleResource(Introspector.class.getModule(), existingPath);
    final Resource resource3 = new ModuleResource(Introspector.class.getModule(), nonExistingPath);
    assertThat(resource1).isEqualTo(resource1);
    assertThat(resource1).isEqualTo(resource2);
    assertThat(resource2).isEqualTo(resource1);
    assertThat(resource1).isNotEqualTo(resource3);
    assertThat(resource1).hasSameHashCodeAs(resource2);
    assertThat(resource1).doesNotHaveSameHashCodeAs(resource3);
  }

}
