////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

import java.util.Arrays;
import java.util.Random;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试{@link CompressionTools}类的功能。
 *
 * @author Claude
 */
public class CompressionToolsTest {

  @Test
  public void testCompressEmptyData() {
    byte[] emptyData = new byte[0];
    byte[] result = CompressionTools.compress(emptyData, 0, 0, Deflater.BEST_COMPRESSION);
    
    // 验证压缩空数据后仍然为空
    assertEquals(0, result.length);
  }
  
  @Test
  public void testCompressNormalData() {
    byte[] testData = "这是一个测试数据，用于测试压缩工具类。".getBytes();
    byte[] compressed = CompressionTools.compress(testData, 0, testData.length, Deflater.BEST_COMPRESSION);
    
    // 验证压缩后的数据非空且可以被解压回原始数据
    assertNotNull(compressed);
    try {
      byte[] decompressed = CompressionTools.decompress(compressed, 0, compressed.length);
      assertArrayEquals(testData, decompressed);
    } catch (DataFormatException e) {
      fail("解压缩时出现异常: " + e.getMessage());
    }
  }
  
  @Test
  public void testCompressWithDifferentLevels() {
    // 创建一个随机数据
    Random random = new Random(42); // 使用固定的种子以保证结果可重现
    byte[] testData = new byte[10000];
    random.nextBytes(testData);
    
    byte[] bestCompression = CompressionTools.compress(testData, 0, testData.length, Deflater.BEST_COMPRESSION);
    byte[] fastestCompression = CompressionTools.compress(testData, 0, testData.length, Deflater.BEST_SPEED);
    
    // 验证最佳压缩通常会产生更小的数据
    assertTrue(bestCompression.length <= fastestCompression.length);
  }
  
  @Test
  public void testCompressWithOffset() {
    byte[] testData = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes();
    int offset = 10; // 从第10个字节开始
    int length = 10; // 压缩10个字节
    
    byte[] compressed = CompressionTools.compress(testData, offset, length, Deflater.DEFAULT_COMPRESSION);
    
    // 验证压缩后的数据可以被解压缩
    try {
      byte[] decompressed = CompressionTools.decompress(compressed, 0, compressed.length);
      
      // 解压缩后的数据应该等于原始数据中被压缩的部分
      byte[] expectedDecompressed = Arrays.copyOfRange(testData, offset, offset + length);
      assertArrayEquals(expectedDecompressed, decompressed);
    } catch (DataFormatException e) {
      fail("解压缩时出现异常: " + e.getMessage());
    }
  }
  
  @Test
  public void testCompressString() {
    String testString = "这是一个测试字符串，将被压缩然后解压缩。";
    byte[] compressed = CompressionTools.compressString(testString, Deflater.DEFAULT_COMPRESSION);
    
    // 验证空字符串压缩后仍然为空
    byte[] emptyCompressed = CompressionTools.compressString("", Deflater.DEFAULT_COMPRESSION);
    assertEquals(0, emptyCompressed.length);
    
    // 验证压缩后的数据可以被正确解压缩
    try {
      String decompressed = CompressionTools.decompressString(compressed, 0, compressed.length);
      assertEquals(testString, decompressed);
    } catch (DataFormatException e) {
      fail("解压缩时出现异常: " + e.getMessage());
    }
  }
  
  @Test
  public void testDecompressEmptyData() throws DataFormatException {
    byte[] emptyData = new byte[0];
    byte[] result = CompressionTools.decompress(emptyData, 0, 0);
    
    // 验证解压缩空数据后仍然为空
    assertEquals(0, result.length);
  }
  
  @Test
  public void testCompressAndDecompress() {
    String testString = "这是一个较长的测试字符串，将被压缩然后解压缩。重复的内容通常可以获得更好的压缩率。"
        + "这是一个较长的测试字符串，将被压缩然后解压缩。重复的内容通常可以获得更好的压缩率。"
        + "这是一个较长的测试字符串，将被压缩然后解压缩。重复的内容通常可以获得更好的压缩率。";
    
    byte[] original = testString.getBytes();
    byte[] compressed = CompressionTools.compress(original, 0, original.length, Deflater.BEST_COMPRESSION);
    
    try {
      byte[] decompressed = CompressionTools.decompress(compressed, 0, compressed.length);
      
      // 验证解压缩后的数据与原始数据相同
      assertArrayEquals(original, decompressed);
      
      // 使用字符串API测试
      byte[] stringCompressed = CompressionTools.compressString(testString, Deflater.BEST_COMPRESSION);
      String decompressedString = CompressionTools.decompressString(stringCompressed, 0, stringCompressed.length);
      
      // 验证解压缩后的字符串与原始字符串相同
      assertEquals(testString, decompressedString);
    } catch (DataFormatException e) {
      fail("解压缩时出现异常: " + e.getMessage());
    }
  }
  
  @Test
  public void testDecompressStringEmpty() throws DataFormatException {
    byte[] emptyData = new byte[0];
    String result = CompressionTools.decompressString(emptyData, 0, 0);
    
    // 验证解压缩空数据后得到空字符串
    assertEquals("", result);
  }
  
  @Test
  public void testInvalidCompressedData() {
    // 创建一个无效的压缩数据
    byte[] invalidData = new byte[]{0x1, 0x2, 0x3, 0x4, 0x5};
    
    // 验证解压缩无效数据时会抛出异常
    assertThrows(DataFormatException.class, () -> {
      CompressionTools.decompress(invalidData, 0, invalidData.length);
    });
    
    assertThrows(DataFormatException.class, () -> {
      CompressionTools.decompressString(invalidData, 0, invalidData.length);
    });
  }
} 