////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The code generator which adds a date and a sequence number to the generated code.
 * <p>
 * This code generator generates codes with the following pattern:
 * <pre><code>
 *   [prefix] + '-' + [local-date] + '-' + [sequence]
 * </code></pre>
 * 例如：
 * <pre><code>
 *   "INV-2022-01-01-1"
 *   "INV-2022-01-01-2"
 * </code></pre>
 *
 * @author Haixing Hu
 */
public class DateSequenceCodeGenerator implements CodeGenerator {

  private final Map<LocalDate, AtomicLong> sequence = new ConcurrentHashMap<>();

  private Clock clock;

  public DateSequenceCodeGenerator() {
    this(Clock.systemDefaultZone());
  }

  public DateSequenceCodeGenerator(final Clock clock) {
    this.clock = clock;
  }

  public Clock getClock() {
    return clock;
  }

  public void setClock(final Clock clock) {
    this.clock = clock;
  }

  @Override
  public String generate(final String prefix) {
    final LocalDate today = LocalDate.now(clock);
    final AtomicLong seq = sequence.computeIfAbsent(today, k -> new AtomicLong(0));
    final long value = seq.incrementAndGet();
    return prefix + "-" + today + "-" + value;
  }
}