////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ltd.qubit.commons.lang.OverridingClassLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.io.CleanupMode.NEVER;

/**
 * Tests for {@link ClassPathResource}.
 * <p>
 * These also originally served as regression tests for the bugs described in
 * SPR-6888 and SPR-9413.
 *
 * @author Chris Beams
 * @author Sam Brannen
 * @author Haixing Hu
 */
class ClassPathResourceTest {

  private static final String PACKAGE_PATH = "cn/njzhyl/commons/io/resource";
  private static final String NONEXISTENT_RESOURCE_NAME = "nonexistent.xml";
  private static final String ABSOLUTE_PATH_TO_NONEXISTENT_RESOURCE = PACKAGE_PATH + '/' + NONEXISTENT_RESOURCE_NAME;
  private static final String ABSOLUTE_PATH_TO_NONEXISTENT_RESOURCE_WITH_LEADING_SLASH = '/' + ABSOLUTE_PATH_TO_NONEXISTENT_RESOURCE;

  @Nested
  class EqualsAndHashCode {

    @Test
    void equalsAndHashCode() {
      final Resource resource1 = new ClassPathResource("cn/njzhyl/commons/io/resource/Resource.class");
      final Resource resource2 = new ClassPathResource("cn/njzhyl/commons/../commons/io/resource/./Resource.class");
      final Resource resource3 = new ClassPathResource("cn/njzhyl/commons/").createRelative("../commons/io/resource/./Resource.class");

      assertThat(resource2).isEqualTo(resource1);
      assertThat(resource3).isEqualTo(resource1);
      assertThat(resource2).hasSameHashCodeAs(resource1);
      assertThat(resource3).hasSameHashCodeAs(resource1);

      // Check whether equal/hashCode works in a HashSet.
      final HashSet<Resource> resources = new HashSet<>();
      resources.add(resource1);
      resources.add(resource2);
      assertThat(resources).hasSize(1);
    }

    @Test
    void resourcesWithDifferentInputPathsAreEqual() {
      final Resource resource1 = new ClassPathResource("cn/njzhyl/commons/io/resource/Resource.class", getClass().getClassLoader());
      final ClassPathResource resource2 = new ClassPathResource("cn/njzhyl/commons/../commons/io/resource/./Resource.class", getClass().getClassLoader());
      assertThat(resource2).isEqualTo(resource1);
    }

    @Test
    void resourcesWithEquivalentAbsolutePathsFromTheSameClassLoaderAreEqual() {
      final ClassPathResource resource1 = new ClassPathResource("Resource.class", getClass());
      final ClassPathResource resource2 = new ClassPathResource("cn/njzhyl/commons/io/resource/Resource.class", getClass().getClassLoader());
      assertThat(resource1.getPath()).isEqualTo(resource2.getPath());
      assertThat(resource1).isEqualTo(resource2);
      assertThat(resource2).isEqualTo(resource1);
    }

    @Test
    void resourcesWithEquivalentAbsolutePathsHaveSameHashCode() {
      final ClassPathResource resource1 = new ClassPathResource("Resource.class", getClass());
      final ClassPathResource resource2 = new ClassPathResource("cn/njzhyl/commons/io/resource/Resource.class", getClass().getClassLoader());
      assertThat(resource1.getPath()).isEqualTo(resource2.getPath());
      assertThat(resource1).hasSameHashCodeAs(resource2);
    }

    @Test
    void resourcesWithEquivalentAbsolutePathsFromDifferentClassLoadersAreNotEqual() {
      class SimpleThrowawayClassLoader extends OverridingClassLoader {
        SimpleThrowawayClassLoader(final ClassLoader parent) {
          super(parent);
        }
      }

      final ClassPathResource resource1 = new ClassPathResource("Resource.class", getClass());
      final ClassPathResource resource2 = new ClassPathResource("cn/njzhyl/commons/io/resource/Resource.class",
          new SimpleThrowawayClassLoader(getClass().getClassLoader()));
      assertThat(resource1.getPath()).isEqualTo(resource2.getPath());
      assertThat(resource1).isNotEqualTo(resource2);
      assertThat(resource2).isNotEqualTo(resource1);
    }

    @Test
    void relativeResourcesAreEqual() throws Exception {
      final Resource resource = new ClassPathResource("dir/");
      final Resource relative = resource.createRelative("subdir");
      assertThat(relative).isEqualTo(new ClassPathResource("dir/subdir"));
    }
  }


  @Nested
  class GetInputStream {

    @Test
    void withStringConstructorRaisesExceptionForNonexistentResource() {
      assertExceptionContainsAbsolutePath(new ClassPathResource(ABSOLUTE_PATH_TO_NONEXISTENT_RESOURCE));
    }

    @Test
    void withClassLoaderConstructorRaisesExceptionForNonexistentResource() {
      assertExceptionContainsAbsolutePath(new ClassPathResource(ABSOLUTE_PATH_TO_NONEXISTENT_RESOURCE, getClass().getClassLoader()));
    }

    @Test
    void withClassLiteralConstructorRaisesExceptionForNonexistentRelativeResource() {
      assertExceptionContainsAbsolutePath(new ClassPathResource(NONEXISTENT_RESOURCE_NAME, getClass()));
    }

    @Test
    void withClassLiteralConstructorRaisesExceptionForNonexistentAbsoluteResource() {
      assertExceptionContainsAbsolutePath(new ClassPathResource(ABSOLUTE_PATH_TO_NONEXISTENT_RESOURCE, getClass()));
    }

    private void assertExceptionContainsAbsolutePath(final ClassPathResource resource) {
      assertThatExceptionOfType(FileNotFoundException.class)
          .isThrownBy(resource::getInputStream)
          .withMessageContaining(ABSOLUTE_PATH_TO_NONEXISTENT_RESOURCE);
    }
  }


  @Nested
  class GetDescription {

    @Test
    void withStringConstructor() {
      assertDescription(new ClassPathResource(ABSOLUTE_PATH_TO_NONEXISTENT_RESOURCE));
    }

    @Test
    void withStringConstructorAndLeadingSlash() {
      assertDescription(new ClassPathResource(ABSOLUTE_PATH_TO_NONEXISTENT_RESOURCE_WITH_LEADING_SLASH));
    }

    @Test
    void withClassLiteralConstructor() {
      assertDescription(new ClassPathResource(NONEXISTENT_RESOURCE_NAME, getClass()));
    }

    @Test
    void withClassLiteralConstructorAndLeadingSlash() {
      assertDescription(new ClassPathResource(ABSOLUTE_PATH_TO_NONEXISTENT_RESOURCE_WITH_LEADING_SLASH, getClass()));
    }

    @Test
    void withClassLoaderConstructor() {
      assertDescription(new ClassPathResource(ABSOLUTE_PATH_TO_NONEXISTENT_RESOURCE, getClass().getClassLoader()));
    }

    @Test
    void withClassLoaderConstructorAndLeadingSlash() {
      assertDescription(new ClassPathResource(ABSOLUTE_PATH_TO_NONEXISTENT_RESOURCE_WITH_LEADING_SLASH, getClass().getClassLoader()));
    }

    private void assertDescription(final ClassPathResource resource) {
      assertThat(resource.getDescription()).isEqualTo("class path resource [%s]", ABSOLUTE_PATH_TO_NONEXISTENT_RESOURCE);
    }
  }


  @Nested
  class GetPath {

    @Test
    void dropsLeadingSlashForClassLoaderAccess() {
      assertThat(new ClassPathResource("/test.html").getPath()).isEqualTo("test.html");
      assertThat(((ClassPathResource) new ClassPathResource("").createRelative("/test.html")).getPath()).isEqualTo("test.html");
    }

    @Test
    void convertsToAbsolutePathForClassRelativeAccess() {
      assertThat(new ClassPathResource("/test.html", getClass()).getPath()).isEqualTo("test.html");
      assertThat(new ClassPathResource("", getClass()).getPath()).isEqualTo(PACKAGE_PATH + "/");
      assertThat(((ClassPathResource) new ClassPathResource("", getClass()).createRelative("/test.html")).getPath()).isEqualTo("test.html");
      assertThat(((ClassPathResource) new ClassPathResource("", getClass()).createRelative("test.html")).getPath()).isEqualTo(PACKAGE_PATH + "/test.html");
    }
  }

  @Test
  void directoryNotReadable() throws Exception {
    final Resource fileDir = new ClassPathResource("example/type");
    assertThat(fileDir.getURL()).asString().startsWith("file:");
    assertThat(fileDir.exists()).isTrue();
    assertThat(fileDir.isReadable()).isFalse();

    final Resource jarDir = new ClassPathResource("org/slf4j");
    assertThat(jarDir.getURL()).asString().startsWith("jar:");
    assertThat(jarDir.exists()).isTrue();
    assertThat(jarDir.isReadable()).isFalse();
  }

  @Test
    // Since the JAR file created in this test cannot be deleted on MS windows,
    // we use `cleanup = NEVER`.
  void emptyFileReadable(@TempDir(cleanup = NEVER) final File tempDir) throws IOException {
    final File file = new File(tempDir, "empty.txt");
    assertThat(file.createNewFile()).isTrue();
    assertThat(file.isFile()).isTrue();

    try (final URLClassLoader fileClassLoader = new URLClassLoader(new URL[]{tempDir.toURI().toURL()})) {
      final Resource emptyFile = new ClassPathResource("empty.txt", fileClassLoader);
      assertThat(emptyFile.exists()).isTrue();
      assertThat(emptyFile.isReadable()).isTrue();
      assertThat(emptyFile.contentLength()).isEqualTo(0);
      file.delete();
    }

    final File jarFile = new File(tempDir, "test.jar");
    try (final ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(jarFile))) {
      zipOut.putNextEntry(new ZipEntry("empty2.txt"));
      zipOut.closeEntry();
    }
    assertThat(jarFile.isFile()).isTrue();

    try (final URLClassLoader jarClassLoader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()})) {
      final Resource emptyJarEntry = new ClassPathResource("empty2.txt", jarClassLoader);
      assertThat(emptyJarEntry.exists()).isTrue();
      assertThat(emptyJarEntry.isReadable()).isTrue();
      assertThat(emptyJarEntry.contentLength()).isEqualTo(0);
    }

    jarFile.deleteOnExit();
    tempDir.deleteOnExit();
  }

}
