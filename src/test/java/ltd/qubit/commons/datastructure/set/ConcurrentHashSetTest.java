////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.set;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of {@link ConcurrentHashSet}.
 *
 * @author Haixing Hu
 */
public class ConcurrentHashSetTest {

  private ConcurrentHashSet<String> set;

  @BeforeEach
  void setUp() {
    set = new ConcurrentHashSet<>();
  }

  @Test
  void testConcurrentAdd() throws InterruptedException {
    final int threadCount = 10;
    final int elementsPerThread = 100;
    final CountDownLatch latch = new CountDownLatch(threadCount);
    final ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    for (int i = 0; i < threadCount; i++) {
      executor.execute(() -> {
        for (int j = 0; j < elementsPerThread; j++) {
          set.add(Thread.currentThread().getName() + "-" + j);
        }
        latch.countDown();
      });
    }
    latch.await();
    executor.shutdown();
    assertEquals(threadCount * elementsPerThread, set.size());
  }

  @Test
  void testConcurrentRemove() throws InterruptedException {
    final int threadCount = 10;
    final int elementsPerThread = 100;
    final CountDownLatch latch = new CountDownLatch(threadCount);
    final ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    for (int i = 0; i < threadCount * elementsPerThread; i++) {
      set.add("element-" + i);
    }
    for (int i = 0; i < threadCount; i++) {
      final int threadIndex = i;
      executor.execute(() -> {
        for (int j = 0; j < elementsPerThread; j++) {
          set.remove("element-" + (threadIndex * elementsPerThread + j));
        }
        latch.countDown();
      });
    }
    latch.await();
    executor.shutdown();
    assertEquals(0, set.size());
  }

  // @Test
  // void testConcurrentAddAndRemove() throws InterruptedException {
  //   final int threadCount = 10;
  //   final int elementsPerThread = 100;
  //   final CountDownLatch latch = new CountDownLatch(threadCount * 2);
  //   final ExecutorService executor = Executors.newFixedThreadPool(threadCount * 2);
  //   final AtomicInteger addCounter = new AtomicInteger();
  //   final AtomicInteger removeCounter = new AtomicInteger();
  //   for (int i = 0; i < threadCount; i++) {
  //     executor.execute(() -> {
  //       for (int j = 0; j < elementsPerThread; j++) {
  //         set.add("element-" + addCounter.getAndIncrement());
  //       }
  //       latch.countDown();
  //     });
  //     executor.execute(() -> {
  //       for (int j = 0; j < elementsPerThread; j++) {
  //         set.remove("element-" + removeCounter.getAndIncrement());
  //       }
  //       latch.countDown();
  //     });
  //   }
  //   latch.await();
  //   executor.shutdown();
  //   assertEquals(0, set.size());
  // }
}
