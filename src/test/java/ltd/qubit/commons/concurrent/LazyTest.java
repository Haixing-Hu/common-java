////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.*;

class LazyTest {

  @Test
  void testGetAndSupplier() {
    AtomicInteger counter = new AtomicInteger(0);
    Lazy<Integer> lazy = Lazy.of(counter::incrementAndGet);
    assertEquals(1, lazy.get());
    assertEquals(1, lazy.get()); // 不会重复调用supplier
  }

  @Test
  void testRefresh() {
    AtomicInteger counter = new AtomicInteger(0);
    Lazy<Integer> lazy = Lazy.of(counter::incrementAndGet);
    assertEquals(1, lazy.get());
    lazy.refresh();
    assertEquals(2, lazy.get());
  }

  @Test
  void testRelease() {
    AtomicInteger counter = new AtomicInteger(0);
    AtomicReference<Integer> released = new AtomicReference<>(null);
    Lazy<Integer> lazy = Lazy.of(counter::incrementAndGet, released::set);
    assertEquals(1, lazy.get());
    lazy.release();
    assertEquals(1, released.get()); // releaser应接收到释放前的值
    assertEquals(2, lazy.get());
  }

  @Test
  void testReleaserCalled() {
    AtomicInteger counter = new AtomicInteger(0);
    AtomicReference<Integer> released = new AtomicReference<>();
    Lazy<Integer> lazy = Lazy.of(counter::incrementAndGet, released::set);
    assertEquals(1, lazy.get());
    lazy.release();
    assertEquals(1, released.get());
  }

  @Test
  void testThreadSafety() throws InterruptedException {
    AtomicInteger counter = new AtomicInteger(0);
    Lazy<Integer> lazy = Lazy.of(counter::incrementAndGet);
    int threadCount = 10;
    CountDownLatch latch = new CountDownLatch(threadCount);
    AtomicReference<Integer> value = new AtomicReference<>();
    for (int i = 0; i < threadCount; i++) {
      new Thread(() -> {
        value.set(lazy.get());
        latch.countDown();
      }).start();
    }
    latch.await();
    assertEquals(1, value.get());
    assertEquals(1, counter.get());
  }

  @Test
  void testNullSupplierThrows() {
    assertThrows(IllegalArgumentException.class, () -> Lazy.of(null));
  }

  @Test
  void testNullReleaserThrows() {
    assertThrows(IllegalArgumentException.class, () -> Lazy.of(() -> 1, null));
  }
}