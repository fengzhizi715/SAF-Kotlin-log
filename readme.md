saf-log是完全基于Kotlin开发的Android日志框架，提供极简的日志风格


#下载安装
Gradle:

```groovy
compile 'com.safframework.log:saf-log:1.0.0'
```

Maven:

```groovy
<dependency>
  <groupId>com.safframework.log</groupId>
  <artifactId>saf-log</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```


#特性
支持以下几种日志级别，除此之外还能将任何对象打印成json格式

| 方法名        | 作用          | 备注          |
| ------------- |:-------------:| :-------------:|
| e()       |Error级别打印日志|       |
| w()        |Warn级别打印日志|       |
| i()        |Info级别打印日志|       |
| d()        |Debug级别打印日志|       |
|json()      |将日志以json格式打印出来|       |

以e、w、i、d打印的日志风格如下：
第一行显示线程名<br>
第二行显示类中打印的行数<br>
第三行显示打印的具体内容


```Java
╔════════════════════════════════════════════════════════════════════════════════════════
║ Thread: main
╟────────────────────────────────────────────────────────────────────────────────────────
║ cn.salesuite.saf.aspects.TraceAspect.traceMethod  (TraceAspect.java:35)
╟────────────────────────────────────────────────────────────────────────────────────────
║ loadUser() take [14ms]
╚════════════════════════════════════════════════════════════════════════════════════════
```

json方法可以将String、Map、对象打印成json风格，具体可以参照下图：

![](L_json.png)