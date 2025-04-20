# JDK 23环境下的测试说明

## ByteBuddy兼容性问题

当在JDK 23环境下运行测试时，可能会遇到以下错误：

```
java.lang.IllegalArgumentException: Java 23 (67) is not supported by the current version of Byte Buddy which officially supports Java 22 (66) - update Byte Buddy or set net.bytebuddy.experimental as a VM property
```

这是因为项目依赖的Mockito框架使用ByteBuddy库来创建模拟对象，而当前版本的ByteBuddy库最高只支持到JDK 22。

## 解决方案

在运行测试时，添加以下JVM参数：

```
-Dnet.bytebuddy.experimental=true
```

例如：

```bash
mvn test -Dnet.bytebuddy.experimental=true
```

此参数告诉ByteBuddy尝试支持更新版本的Java，即使它们尚未正式被支持。

## 长期解决方案

- 等待ByteBuddy和Mockito更新以正式支持JDK 23
- 考虑在项目的持续集成流程中添加此参数

## 注意事项

- 这是一个临时解决方案，直到ByteBuddy官方支持JDK 23
- 由于这是一个实验性支持，可能会有一些不稳定的情况发生 