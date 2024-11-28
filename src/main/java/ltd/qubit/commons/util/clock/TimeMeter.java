////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.clock;

import java.time.Clock;
import java.util.concurrent.TimeUnit;

import ltd.qubit.commons.lang.DoubleUtils;
import ltd.qubit.commons.util.HumanReadable;

/**
 * A simple meter used to measure the time elapsed.
 * <p>
 * It can use a customized {@link Clock} to measure the time. If no clock is
 * specified, it uses the system default clock.
 *
 * @author Haixing Hu
 */
public class TimeMeter {

  private Clock clock;
  private long startTime;
  private long endTime;

  /**
   * Constructs a new meter with the system default clock.
   */
  public TimeMeter() {
    this.startTime = 0;
    this.endTime = 0;
    this.clock = Clock.systemUTC();
  }

  /**
   * Constructs a new meter with the specified clock.
   *
   * @param clock
   *     the clock used by this meter.
   */
  public TimeMeter(final Clock clock) {
    this.startTime = 0;
    this.endTime = 0;
    this.clock = clock;
  }

  /**
   * Returns the clock used by this meter.
   *
   * @return
   *     the clock used by this meter.
   */
  public Clock getClock() {
    return clock;
  }

  /**
   * Sets the clock used by this meter.
   *
   * @param clock
   *     the clock used by this meter.
   */
  public void setClock(final Clock clock) {
    this.clock = clock;
  }

  /**
   * Starts this meter.
   */
  public void start() {
    this.startTime = clock.millis();
  }

  /**
   * Stops this meter.
   */
  public void stop() {
    this.endTime = clock.millis();
  }

  /**
   * Resets this meter.
   */
  public void reset() {
    this.startTime = 0;
    this.endTime = 0;
  }

  /**
   * Returns the duration in milliseconds.
   *
   * @return
   *     the duration in milliseconds.
   */
  public long duration() {
    final long end = (this.endTime == 0 ? clock.millis() : this.endTime);
    return end - this.startTime;
  }

  /**
   * Returns the duration in seconds.
   *
   * @return
   *     the duration in seconds.
   */
  public long elapsedSeconds() {
    return TimeUnit.MILLISECONDS.toSeconds(duration());
  }

  /**
   * Returns the duration in minutes.
   *
   * @return
   *     the duration in minutes.
   */
  public long elapsedMinutes() {
    return TimeUnit.MILLISECONDS.toMinutes(duration());
  }

  /**
   * Returns the duration as a human-readable string.
   *
   * @return
   *     the duration as a human-readable string.
   */
  public String readableDuration() {
    return HumanReadable.formatDuration(duration(), TimeUnit.MILLISECONDS);
  }

  /**
   * Returns the speed of the specified count per minute.
   *
   * @param count
   *     the count.
   * @return
   *     the string representation of the speed of the specified count per
   *     minute, or "N/A" if the elapsed time is zero.
   */
  public String speedPerMinute(final int count) {
    final long seconds = elapsedSeconds();
    if (seconds == 0) {
      return "N/A";
    } else {
      return DoubleUtils.roundToString(((double)count / seconds) * 60.0, 2) + "/m";
    }
  }

  /**
   * Returns the speed of the specified count per second.
   *
   * @param count
   *     the count.
   * @return
   *     the string representation of the speed of the specified count per
   *     second, or "N/A" if the elapsed time is zero.
   */
  public String speedPerSecond(final int count) {
    final long seconds = elapsedSeconds();
    if (seconds == 0) {
      return "N/A";
    } else {
      return DoubleUtils.roundToString((double)count / seconds, 2) + "/s";
    }
  }
}
