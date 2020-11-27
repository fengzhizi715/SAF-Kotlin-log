SAF-Kotlin-log
===

Version 2.5.4
---
2020-11-27
* 更新 Kotlin 版本至 1.4.20
* 优化 file　模块
* 增加打印字节数组的扩展函数

Version 2.5.3
---
2020-10-09
* L 的 printers 使用线程安全的 Set

Version 2.5.2
---
2020-10-03
* 更新 Kotlin 版本至 1.4.10 以及 Kotlin Coroutines 版本

Version 2.5.1
---
2020-07-30
* 修复 FileWriter 的 bug

Version 2.5.0
---
2020-07-29
* 新增基于文件大小生成文件(默认情况下，超过1M会对日志进行分片)

Version 2.4.12
---
2020-07-21
* 更新 Kotlin 版本、Kotlin Coroutines 版本

Version 2.4.11
---
2020-03-29
* 修复依赖的bug

Version 2.4.10
---
2020-03-28
* core 模块去掉 Converter 接口，converter-fastjson、converter-gson 不再依赖 core 模块

Version 2.4.9
---
2020-03-09
* 更新 Kotlin-Coroutines-Utils 库
* 更新 Kotlin 版本、Kotlin Coroutines 版本

Version 2.4.8
---
2020-01-30
* 更新 Kotlin-Coroutines-Utils 库

Version 2.4.7
---
2020-01-24
* file 模块使用的 Coroutines 使用 Kotlin-Coroutines-Utils 库替换

Version 2.4.6
---
2020-01-04
* 优化代码
* 增加 DateWithHourFileNameGenerator 类，用于按照日期和小时进行分割日志文件

Version 2.4.5
---
2019-12-15
* 完善 file 模块 FileWriter 的 appendLog() 方法

Version 2.4.4
---
2019-12-15
* 升级 Kotlin Coroutines 版本
* 完善 file 模块 compress() 方法

Version 2.4.3
---
2019-12-15
* 更新 Okhttp 模块
* 完善 json() 方法

Version 2.4.2
---
2019-12-14
* 更新 file 模块
* 优化相关的代码

Version 2.4.1
---
2019-12-13
* 更新 file 模块

Version 2.4.0
---
2019-12-12
* 修改各个模块的名称、增加 converter 相关的模块用于解析对象成字符串
* core 模块不再依赖 fastjson 库，L 可以根据项目的需要自己实现 converter
* 提供基于 dsl 的方式配置 L
* 优化相关的代码
* 升级 Kotlin 版本

Version 2.3.0
---
2019-12-07
* json() 增加LogLevel、Tag
* 优化 saf_log_okhttp 模块
* 升级Kotlin、Gradle插件版本

Version 2.2.1
---
2019-11-16
* 优化 saf-log-core 模块
* 优化 saf-log-file 模块

Version 2.2.0
---
2019-11-16
* 增加使用特定的 Printer 进行打印日志
* saf-log-file 模块使用 SafeCoroutineScope 创建协程

Version 2.1.3
---
2019-10-03
* 优化 StringHandler
* 增加 saf-log-okhttp 模块，用于打印 okhttp 的 request、response

Version 2.1.2
---
2019-10-03
* Printers 增加equals、hashcode方法

Version 2.1.1
---
2019-10-03
* 优化 StringHandler
* 增加日志中显示线程信息、类信息的开关

Version 2.1.0
---
2019-10-02
* 完善对文件的保存功能
* 增加文件清空策略
* 完善文件命名/生成策略

Version 2.0.0
---
2019-09-21
* 升级Kotlin版本
* 重构 L，增加 Printer、Formatter
* 增加对文件的保存

Version 1.5.6
---
2019-08-13
* 升级Kotlin版本
* 优化代码
* 增加 L 的方法

Version 1.5.5
---
2018-12-31
* 升级Kotlin版本
* 升级appcompat-v7

Version 1.5.4
---
2018-11-25
* 升级Kotlin版本
* appcompat-v7 改成 compileOnly

Version 1.5.3
---
2018-05-25
* 升级Kotlin版本，修复bug

Version 1.5.2
---
2018-04-08
* 升级Kotlin版本，修复bug

Version 1.5.1
---
2018-01-16
* 使用最新版本的gradle

Version 1.5.0
---
2017-11-27
* 对整个项目重构，增加addCustomerHandler()可以自定义Handler来解析Object，然后将其打印处出来。

Version 1.4.0
---
2017-11-26
* json()增加对Bundle、Intent、Reference、Throwable、Uri类型的支持

Version 1.3.0
---
2017-11-25
* 对整个项目进行重构，增加Parser层

Version 1.2.2
---
2017-11-24
* 对L.kt进行重构

Version 1.2.1
---
2017-11-24
* 对L.kt进行重构

Version 1.2.0
---
2017-09-03
 *  Kotlin版本升级到1.1.4
 * json()优化对Map、Set类型的支持

Version 1.1.0
---
2017-09-03
 *  json()修复对List的String、Integer、Float、Double等类型的支持

Version 1.0.9
---
2017-07-26
 *  json()增加对List对象对打印

Version 1.0.8
---
2017-06-30
 *  修复Lext.kt的bug

Version 1.0.7
---
2017-06-30
 *  增加Lext.kt，方便Kotlin项目使用

Version 1.0.6
---
2017-06-24
 *  日志增加可以自定义的Header，Header可以放app的版本信息等等，方便调试

Version 1.0.5
---
2017-03-03
 *  依赖的Kotlin版本升级到1.1.0

Version 1.0.4
---
2017-02-07
 *  修复多行显示不美观的问题

Version 1.0.2
---
2017-01-17
 *  增加一些方法

Version 1.0.1
---
2017-01-11
 *  优化代码，修复已知bug

Version 1.0.0
---
2017-01-10
 *  初始化工程，简单的封装日志框架
