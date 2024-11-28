////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n.message;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import ltd.qubit.commons.error.NoSuchMessageException;
import ltd.qubit.commons.i18n.LocaleContextHolder;
import ltd.qubit.commons.i18n.bundle.MessageSourceResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit test of the {@link ResourceBundleMessageSource}.
 *
 * @author Juergen Hoeller
 * @author Sebastien Deleuze
 * @author Haixing Hu
 */
class ResourceBundleMessageSourceTest {

  @Test
  void messageAccessWithDefaultMessageSource() {
    doTestMessageAccess(false, true, false, false, false);
  }

  @Test
  void messageAccessWithDefaultMessageSourceAndMessageFormat() {
    doTestMessageAccess(false, true, false, false, true);
  }

  @Test
  void messageAccessWithDefaultMessageSourceAndFallbackToGerman() {
    doTestMessageAccess(false, true, true, true, false);
  }

  @Test
  void messageAccessWithDefaultMessageSourceAndFallbackTurnedOff() {
    doTestMessageAccess(false, false, false, false, false);
  }

  @Test
  void messageAccessWithDefaultMessageSourceAndFallbackTurnedOffAndFallbackToGerman() {
    doTestMessageAccess(false, false, true, true, false);
  }

  @Test
  void messageAccessWithReloadableMessageSource() {
    doTestMessageAccess(true, true, false, false, false);
  }

  @Test
  void messageAccessWithReloadableMessageSourceAndMessageFormat() {
    doTestMessageAccess(true, true, false, false, true);
  }

  @Test
  void messageAccessWithReloadableMessageSourceAndFallbackToGerman() {
    doTestMessageAccess(true, true, true, true, false);
  }

  @Test
  void messageAccessWithReloadableMessageSourceAndFallbackTurnedOff() {
    doTestMessageAccess(true, false, false, false, false);
  }

  @Test
  void messageAccessWithReloadableMessageSourceAndFallbackTurnedOffAndFallbackToGerman() {
    doTestMessageAccess(true, false, true, true, false);
  }

  private String[] getBasenames(final boolean reloadable) {
    final String basepath = "ltd/qubit/commons/i18n/message/";
    final String[] basenames;
    if (reloadable) {
      basenames = new String[] {
          "classpath:" + basepath + "messages",
          "classpath:" + basepath + "more-messages"};
    }
    else {
      basenames = new String[] {
          basepath + "messages",
          basepath + "more-messages"};
    }
    return basenames;
  }

  private AbstractResourceBasedMessageSource getMessageSource(final boolean reloadable) {
    if (reloadable) {
      return new ReloadableResourceBundleMessageSource();
    } else {
      return new ResourceBundleMessageSource();
    }
  }

  protected void doTestMessageAccess(final boolean reloadable,
      final boolean fallbackToSystemLocale, final boolean expectGermanFallback,
      final boolean useCodeAsDefaultMessage, final boolean alwaysUseMessageFormat) {
    final String[] basenames = getBasenames(reloadable);
    final AbstractResourceBasedMessageSource ms = getMessageSource(reloadable);
    ms.setBasenames(basenames);
    if (!fallbackToSystemLocale) {
      ms.setFallbackToSystemLocale(false);
    }
    if (useCodeAsDefaultMessage) {
      ms.setUseCodeAsDefaultMessage(true);
    }
    if (alwaysUseMessageFormat) {
      ms.setAlwaysUseMessageFormat(true);
    }
    Locale.setDefault(expectGermanFallback ? Locale.GERMAN : Locale.CANADA);

    assertThat(ms.getMessage("code1", null, Locale.ENGLISH))
        .isEqualTo("message1");
    final Object expected = (fallbackToSystemLocale && expectGermanFallback ? "nachricht2" : "message2");
    assertThat(ms.getMessage("code2", null, Locale.ENGLISH))
        .isEqualTo(expected);
    assertThat(ms.getMessage("code2", null, Locale.GERMAN))
        .isEqualTo("nachricht2");
    assertThat(ms.getMessage("code2", null, new Locale("DE", "at")))
        .isEqualTo("nochricht2");
    assertThat(ms.getMessage("code2", null, new Locale("DE", "at", "oo")))
        .isEqualTo("noochricht2");
    if (reloadable) {
      assertThat(ms.getMessage("code2", null, Locale.GERMANY))
          .isEqualTo("nachricht2xml");
    }
    final MessageSourceAccessor accessor = new MessageSourceAccessor(ms);
    LocaleContextHolder.setLocale(new Locale("DE", "at"));
    try {
      assertThat(accessor.getMessage("code2")).isEqualTo("nochricht2");
    }
    finally {
      LocaleContextHolder.setLocale(null);
    }

    assertThat(ms.getMessage("code3", null, Locale.ENGLISH))
        .isEqualTo("message3");
    MessageSourceResolvable resolvable = new DefaultMessageSourceResolvable("code3");
    assertThat(ms.getMessage(resolvable, Locale.ENGLISH))
        .isEqualTo("message3");
    resolvable = new DefaultMessageSourceResolvable(new String[] {"code4", "code3"});
    assertThat(ms.getMessage(resolvable, Locale.ENGLISH))
        .isEqualTo("message3");

    assertThat(ms.getMessage("code3", null, Locale.ENGLISH))
        .isEqualTo("message3");
    resolvable = new DefaultMessageSourceResolvable(new String[] {"code4", "code3"});
    assertThat(ms.getMessage(resolvable, Locale.ENGLISH))
        .isEqualTo("message3");

    final Object[] args = new Object[] {"Hello", new DefaultMessageSourceResolvable(new String[] {"code1"})};
    assertThat(ms.getMessage("hello", args, Locale.ENGLISH))
        .isEqualTo("Hello, message1");

    // test default message without and with args
    assertThat(ms.getMessage(null, null, null, Locale.ENGLISH))
        .isNull();
    assertThat(ms.getMessage(null, null, "default", Locale.ENGLISH))
        .isEqualTo("default");
    assertThat(ms.getMessage(null, args, "default", Locale.ENGLISH))
        .isEqualTo("default");
    assertThat(ms.getMessage(null, null, "{0}, default", Locale.ENGLISH))
        .isEqualTo("{0}, default");
    assertThat(ms.getMessage(null, args, "{0}, default", Locale.ENGLISH))
        .isEqualTo("Hello, default");

    // test resolvable with default message, without and with args
    resolvable = new DefaultMessageSourceResolvable(null, null, "default");
    assertThat(ms.getMessage(resolvable, Locale.ENGLISH)).isEqualTo("default");
    resolvable = new DefaultMessageSourceResolvable(null, args, "default");
    assertThat(ms.getMessage(resolvable, Locale.ENGLISH)).isEqualTo("default");
    resolvable = new DefaultMessageSourceResolvable(null, null, "{0}, default");
    assertThat(ms.getMessage(resolvable, Locale.ENGLISH)).isEqualTo("{0}, default");
    resolvable = new DefaultMessageSourceResolvable(null, args, "{0}, default");
    assertThat(ms.getMessage(resolvable, Locale.ENGLISH)).isEqualTo("Hello, default");

    // test message args
    assertThat(ms.getMessage("hello", new Object[]{"Arg1", "Arg2"}, Locale.ENGLISH))
        .isEqualTo("Arg1, Arg2");
    assertThat(ms.getMessage("hello", null, Locale.ENGLISH))
        .isEqualTo("{0}, {1}");

    if (alwaysUseMessageFormat) {
      assertThat(ms.getMessage("escaped", null, Locale.ENGLISH))
          .isEqualTo("I'm");
    }
    else {
      assertThat(ms.getMessage("escaped", null, Locale.ENGLISH))
          .isEqualTo("I''m");
    }
    assertThat(ms.getMessage("escaped", new Object[]{"some arg"}, Locale.ENGLISH))
        .isEqualTo("I'm");

    if (useCodeAsDefaultMessage) {
      assertThat(ms.getMessage("code4", null, Locale.GERMAN))
          .isEqualTo("code4");
    }
    else {
      assertThatExceptionOfType(NoSuchMessageException.class)
          .isThrownBy(() -> ms.getMessage("code4", null, Locale.GERMAN));
    }
  }

  @Test
  void resourceBundleMessageSourceStandalone() {
    final ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages");
    assertThat(ms.getMessage("code1", null, Locale.ENGLISH)).isEqualTo("message1");
    assertThat(ms.getMessage("code2", null, Locale.GERMAN)).isEqualTo("nachricht2");
  }

  @Test
  void resourceBundleMessageSourceWithWhitespaceInBasename() {
    final ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages  ");
    assertThat(ms.getMessage("code1", null, Locale.ENGLISH)).isEqualTo("message1");
    assertThat(ms.getMessage("code2", null, Locale.GERMAN)).isEqualTo("nachricht2");
  }

  @Test
  void resourceBundleMessageSourceWithDefaultCharset() {
    final ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages");
    ms.setDefaultEncoding("ISO-8859-1");
    assertThat(ms.getMessage("code1", null, Locale.ENGLISH)).isEqualTo("message1");
    assertThat(ms.getMessage("code2", null, Locale.GERMAN)).isEqualTo("nachricht2");
  }

  @Test
  void resourceBundleMessageSourceWithInappropriateDefaultCharset() {
    final ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages");
    ms.setDefaultEncoding("argh");
    ms.setFallbackToSystemLocale(false);
    assertThatExceptionOfType(NoSuchMessageException.class)
        .isThrownBy(() -> ms.getMessage("code1", null, Locale.ENGLISH));
  }

  @Test
  void reloadableResourceBundleMessageSourceStandalone() {
    final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages");
    assertThat(ms.getMessage("code1", null, Locale.ENGLISH)).isEqualTo("message1");
    assertThat(ms.getMessage("code2", null, Locale.GERMAN)).isEqualTo("nachricht2");
  }

  @Test
  void reloadableResourceBundleMessageSourceWithCacheSeconds() throws InterruptedException {
    final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages");
    ms.setCacheMillis(100);
    // Initial cache attempt
    assertThat(ms.getMessage("code1", null, Locale.ENGLISH)).isEqualTo("message1");
    assertThat(ms.getMessage("code2", null, Locale.GERMAN)).isEqualTo("nachricht2");
    Thread.sleep(200);
    // Late enough for a re-cache attempt
    assertThat(ms.getMessage("code1", null, Locale.ENGLISH)).isEqualTo("message1");
    assertThat(ms.getMessage("code2", null, Locale.GERMAN)).isEqualTo("nachricht2");
  }

  @Test
  void reloadableResourceBundleMessageSourceWithNonConcurrentRefresh() throws InterruptedException {
    final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages");
    ms.setCacheMillis(100);
    ms.setConcurrentRefresh(false);
    // Initial cache attempt
    assertThat(ms.getMessage("code1", null, Locale.ENGLISH)).isEqualTo("message1");
    assertThat(ms.getMessage("code2", null, Locale.GERMAN)).isEqualTo("nachricht2");
    Thread.sleep(200);
    // Late enough for a re-cache attempt
    assertThat(ms.getMessage("code1", null, Locale.ENGLISH)).isEqualTo("message1");
    assertThat(ms.getMessage("code2", null, Locale.GERMAN)).isEqualTo("nachricht2");
  }

  @Test
  void reloadableResourceBundleMessageSourceWithCommonMessages() {
    final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    final Properties commonMessages = new Properties();
    commonMessages.setProperty("warning", "Do not do {0}");
    ms.setCommonMessages(commonMessages);
    ms.setBasename("ltd/qubit/commons/i18n/message/messages");
    assertThat(ms.getMessage("code1", null, Locale.ENGLISH)).isEqualTo("message1");
    assertThat(ms.getMessage("code2", null, Locale.GERMAN)).isEqualTo("nachricht2");
    assertThat(ms.getMessage("warning", new Object[]{"this"}, Locale.ENGLISH)).isEqualTo("Do not do this");
    assertThat(ms.getMessage("warning", new Object[]{"that"}, Locale.GERMAN)).isEqualTo("Do not do that");
  }

  @Test
  void reloadableResourceBundleMessageSourceWithWhitespaceInBasename() {
    final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages  ");
    assertThat(ms.getMessage("code1", null, Locale.ENGLISH)).isEqualTo("message1");
    assertThat(ms.getMessage("code2", null, Locale.GERMAN)).isEqualTo("nachricht2");
  }

  @Test
  void reloadableResourceBundleMessageSourceWithDefaultCharset() {
    final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages");
    ms.setDefaultEncoding("ISO-8859-1");
    assertThat(ms.getMessage("code1", null, Locale.ENGLISH)).isEqualTo("message1");
    assertThat(ms.getMessage("code2", null, Locale.GERMAN)).isEqualTo("nachricht2");
  }

  @Test
  void reloadableResourceBundleMessageSourceWithInappropriateDefaultCharset() {
    final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages");
    ms.setDefaultEncoding("unicode");
    final Properties fileCharsets = new Properties();
    fileCharsets.setProperty("ltd/qubit/commons/i18n/message/messages_de", "unicode");
    ms.setFileEncodings(fileCharsets);
    ms.setFallbackToSystemLocale(false);
    assertThatExceptionOfType(NoSuchMessageException.class).isThrownBy(() ->
        ms.getMessage("code1", null, Locale.ENGLISH));
  }

  @Test
  void reloadableResourceBundleMessageSourceWithInappropriateEnglishCharset() {
    final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages");
    ms.setFallbackToSystemLocale(false);
    final Properties fileCharsets = new Properties();
    fileCharsets.setProperty("ltd/qubit/commons/i18n/message/messages", "unicode");
    ms.setFileEncodings(fileCharsets);
    assertThatExceptionOfType(NoSuchMessageException.class).isThrownBy(() ->
        ms.getMessage("code1", null, Locale.ENGLISH));
  }

  @Test
  void reloadableResourceBundleMessageSourceWithInappropriateGermanCharset() {
    final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages");
    ms.setFallbackToSystemLocale(false);
    final Properties fileCharsets = new Properties();
    fileCharsets.setProperty("ltd/qubit/commons/i18n/message/messages_de", "unicode");
    ms.setFileEncodings(fileCharsets);
    assertThat(ms.getMessage("code1", null, Locale.ENGLISH)).isEqualTo("message1");
    assertThat(ms.getMessage("code2", null, Locale.GERMAN)).isEqualTo("message2");
  }

  @Test
  void reloadableResourceBundleMessageSourceFileNameCalculation() {
    final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();

    List<String> filenames = ms.calculateFilenamesForLocale("messages", Locale.ENGLISH);
    assertThat(filenames).containsExactly("messages_en");

    filenames = ms.calculateFilenamesForLocale("messages", Locale.UK);
    assertThat(filenames).containsExactly("messages_en_GB", "messages_en");

    filenames = ms.calculateFilenamesForLocale("messages", new Locale("en", "GB", "POSIX"));
    assertThat(filenames).containsExactly("messages_en_GB_POSIX","messages_en_GB", "messages_en");

    filenames = ms.calculateFilenamesForLocale("messages", new Locale("en", "", "POSIX"));
    assertThat(filenames).containsExactly("messages_en__POSIX", "messages_en");

    filenames = ms.calculateFilenamesForLocale("messages", new Locale("", "UK", "POSIX"));
    assertThat(filenames).containsExactly("messages__UK_POSIX", "messages__UK");

    filenames = ms.calculateFilenamesForLocale("messages", new Locale("", "", "POSIX"));
    assertThat(filenames).isEmpty();
  }

  @Test
  void reloadableResourceBundleMessageSourceWithCustomFileExtensions() {
    final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages");
    ms.setFileExtensions(List.of(".toskip", ".custom"));
    assertThat(ms.getMessage("code1", null, Locale.ENGLISH)).isEqualTo("message1");
    assertThat(ms.getMessage("code2", null, Locale.GERMAN)).isEqualTo("nachricht2");
  }

  @Test
  void reloadableResourceBundleMessageSourceWithEmptyCustomFileExtensions() {
    final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    assertThatThrownBy(() -> ms.setFileExtensions(Collections.emptyList()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("At least one file extension is required");
  }

  @Test
  void reloadableResourceBundleMessageSourceWithInvalidCustomFileExtensions() {
    final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
    assertThatThrownBy(() -> ms.setFileExtensions(List.of("invalid")))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("File extension 'invalid' should start with '.'");
  }

  @Test
  void messageSourceResourceBundle() {
    final ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
    ms.setBasename("ltd/qubit/commons/i18n/message/messages");
    final MessageSourceResourceBundle rbe = new MessageSourceResourceBundle(ms, Locale.ENGLISH);
    assertThat(rbe.getString("code1")).isEqualTo("message1");
    assertThat(rbe.containsKey("code1")).isTrue();
    final MessageSourceResourceBundle rbg = new MessageSourceResourceBundle(ms, Locale.GERMAN);
    assertThat(rbg.getString("code2")).isEqualTo("nachricht2");
    assertThat(rbg.containsKey("code2")).isTrue();
  }

  @AfterEach
  void tearDown() {
    ResourceBundle.clearCache();
  }

}
