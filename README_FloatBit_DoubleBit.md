# FloatBit 和 DoubleBit 工具类

这两个工具类提供了对IEEE 754标准的单精度和双精度浮点数的底层位操作功能。

## 特性

- **位操作**: 提供浮点数与其二进制表示之间的转换
- **位字段提取**: 可以提取符号位、指数位、尾数位等
- **浮点数检测**: 可以检测零值、无穷大、NaN、标准化数、非标准化数等
- **浮点数构造**: 可以从符号、指数、尾数构造浮点数
- **IEEE 754标准**: 完全符合IEEE 754标准的单精度和双精度浮点数格式

## 使用示例

### FloatBit 使用示例

```java
import ltd.qubit.commons.math.FloatBit;

public class FloatBitExample {
    public static void main(String[] args) {
        // 基本位操作
        float value = 3.14159f;
        int bits = FloatBit.toBits(value);
        float restored = FloatBit.fromBits(bits);
        System.out.println("原值: " + value + ", 还原值: " + restored);
        
        // 提取位字段
        System.out.println("符号位: " + FloatBit.getSignBit(bits));
        System.out.println("指数: " + FloatBit.getExponent(bits));
        System.out.println("实际指数: " + FloatBit.getActualExponent(bits));
        System.out.println("尾数: " + FloatBit.getMantissa(bits));
        
        // 检测特殊值
        System.out.println("是否为零: " + FloatBit.isZero(0.0f));
        System.out.println("是否为正零: " + FloatBit.isPositiveZero(0.0f));
        System.out.println("是否为负零: " + FloatBit.isNegativeZero(-0.0f));
        System.out.println("是否为无穷大: " + FloatBit.isInfinite(Float.POSITIVE_INFINITY));
        System.out.println("是否为NaN: " + FloatBit.isNaN(Float.NaN));
        System.out.println("是否为标准化数: " + FloatBit.isNormalized(1.0f));
        System.out.println("是否为非标准化数: " + FloatBit.isDenormalized(Float.MIN_VALUE));
        
        // 构造浮点数
        float composed = FloatBit.compose(0, 0, 0); // 构造1.0f
        System.out.println("构造的1.0f: " + composed);
        
        float negativeOne = FloatBit.compose(1, 0, 0); // 构造-1.0f
        System.out.println("构造的-1.0f: " + negativeOne);
        
        // 符号操作
        System.out.println("符号: " + FloatBit.sign(value));
        System.out.println("复制符号: " + FloatBit.copySign(5.0f, -2.0f));
        
        // 下一个/上一个值
        float next = FloatBit.nextUp(1.0f);
        float prev = FloatBit.nextDown(1.0f);
        System.out.println("1.0f的下一个值: " + next);
        System.out.println("1.0f的上一个值: " + prev);
    }
}
```

### DoubleBit 使用示例

```java
import ltd.qubit.commons.math.DoubleBit;

public class DoubleBitExample {
    public static void main(String[] args) {
        // 基本位操作
        double value = Math.PI;
        long bits = DoubleBit.toBits(value);
        double restored = DoubleBit.fromBits(bits);
        System.out.println("原值: " + value + ", 还原值: " + restored);
        
        // 提取位字段
        System.out.println("符号位: " + DoubleBit.getSignBit(bits));
        System.out.println("指数: " + DoubleBit.getExponent(bits));
        System.out.println("实际指数: " + DoubleBit.getActualExponent(bits));
        System.out.println("尾数: " + DoubleBit.getMantissa(bits));
        
        // 检测特殊值
        System.out.println("是否为零: " + DoubleBit.isZero(0.0));
        System.out.println("是否为正零: " + DoubleBit.isPositiveZero(0.0));
        System.out.println("是否为负零: " + DoubleBit.isNegativeZero(-0.0));
        System.out.println("是否为无穷大: " + DoubleBit.isInfinite(Double.POSITIVE_INFINITY));
        System.out.println("是否为NaN: " + DoubleBit.isNaN(Double.NaN));
        System.out.println("是否为标准化数: " + DoubleBit.isNormalized(1.0));
        System.out.println("是否为非标准化数: " + DoubleBit.isDenormalized(Double.MIN_VALUE));
        
        // 构造浮点数
        double composed = DoubleBit.compose(0, 0, 0); // 构造1.0
        System.out.println("构造的1.0: " + composed);
        
        double negativeOne = DoubleBit.compose(1, 0, 0); // 构造-1.0
        System.out.println("构造的-1.0: " + negativeOne);
        
        // 符号操作
        System.out.println("符号: " + DoubleBit.sign(value));
        System.out.println("复制符号: " + DoubleBit.copySign(5.0, -2.0));
        
        // 下一个/上一个值
        double next = DoubleBit.nextUp(1.0);
        double prev = DoubleBit.nextDown(1.0);
        System.out.println("1.0的下一个值: " + next);
        System.out.println("1.0的上一个值: " + prev);
    }
}
```

## IEEE 754 浮点数格式

### 单精度浮点数 (float) - 32位
- 符号位: 1位 (第31位)
- 指数位: 8位 (第30-23位)
- 尾数位: 23位 (第22-0位)
- 指数偏移量: 127

### 双精度浮点数 (double) - 64位
- 符号位: 1位 (第63位)
- 指数位: 11位 (第62-52位)
- 尾数位: 52位 (第51-0位)
- 指数偏移量: 1023

## 常量

两个类都提供了大量有用的常量，如：

- `BITS`: 总位数
- `SIGN_BITS`: 符号位数
- `EXPONENT_BITS`: 指数位数
- `MANTISSA_BITS`: 尾数位数
- `EXPONENT_BIAS`: 指数偏移量
- `SIGN_BIT_MASK`: 符号位掩码
- `EXPONENT_MASK`: 指数位掩码
- `MANTISSA_MASK`: 尾数位掩码
- 等等...

## 应用场景

这些工具类在以下场景中特别有用：

1. **科学计算**: 需要精确控制浮点数表示的场合
2. **数值算法**: 实现高精度数值算法时
3. **调试和分析**: 分析浮点数精度问题
4. **序列化**: 需要精确保存和恢复浮点数的二进制表示
5. **教学**: 理解IEEE 754浮点数标准

## 注意事项

- 这些类提供的是底层位操作，使用时需要对IEEE 754标准有基本了解
- 位操作可能会绕过Java的一些浮点数检查，使用时要谨慎
- 某些操作（如构造浮点数）可能产生非法的浮点数表示，需要注意验证 