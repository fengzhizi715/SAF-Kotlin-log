# SAF-Kotlin-log

[![@Tony沈哲 on weibo](https://img.shields.io/badge/weibo-%40Tony%E6%B2%88%E5%93%B2-blue.svg)](http://www.weibo.com/fengzhizi715)
[![License](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![](https://jitpack.io/v/fengzhizi715/SAF-Kotlin-log.svg)](https://jitpack.io/#fengzhizi715/SAF-Kotlin-log)

![](logo.JPG)

它不仅能够打印出漂亮的日志格式，还支持定制的日志格式输出，日志分片等。

它可以在任何使用 Java、Kotlin 开发的 Android 项目中使用。（即使在您的项目中只使用 Java，也无需额外添加 Kotlin 的配置。）

# 功能：

* 日志框架高度可扩展，提供丰富的接口
* 支持自定义 Tag，便于过滤
* 支持多种日志级别
* 支持显示/关闭当前线程信息
* 支持自定义 Header，可以用于标识手机、App 等版本信息
* 提供基于 DSL 的方式配置 L
* 基于责任链模式来打造对象的打印，将对象打印成json风格，方便阅读。默认支持JSON字符串、Collection、Map、Bundle、Intent、Reference、Throwable、Uri等类型的打印，分别做了特别的格式化处理。
* 使用 json() 方法打印字符串时，支持超长日志的打印，解决了 Logcat 4K 字符截断的问题。
* 支持自定义对象的解析处理，将其打印成自己想要的风格。
* 支持字节数组的打印
* 支持自定义日志格式存储到文件，底层基于 Kotlin Coroutines
* 支持使用特定的 Printer 进行打印日志
* 提供日志的打包功能
* 提供单独的基于 OkHttp 的日志拦截器，能够打印 Request、Response 信息，便于网络接口的调试。

## 更详细的功能请查看[wiki](https://github.com/fengzhizi715/SAF-Kotlin-log/wiki)

# 最新版本

模块|最新版本
---|:-------------:
saf-log-core|[![](https://jitpack.io/v/fengzhizi715/SAF-Kotlin-log.svg)](https://jitpack.io/#fengzhizi715/SAF-Kotlin-log)
saf-log-file|[![](https://jitpack.io/v/fengzhizi715/SAF-Kotlin-log.svg)](https://jitpack.io/#fengzhizi715/SAF-Kotlin-log)
saf-log-okhttp|[![](https://jitpack.io/v/fengzhizi715/SAF-Kotlin-log.svg)](https://jitpack.io/#fengzhizi715/SAF-Kotlin-log)
saf-log-converter-fastjson|[![](https://jitpack.io/v/fengzhizi715/SAF-Kotlin-log.svg)](https://jitpack.io/#fengzhizi715/SAF-Kotlin-log)
saf-log-converter-gson|[![](https://jitpack.io/v/fengzhizi715/SAF-Kotlin-log.svg)](https://jitpack.io/#fengzhizi715/SAF-Kotlin-log)
saf-log-debug-view|[![](https://jitpack.io/v/fengzhizi715/SAF-Kotlin-log.svg)](https://jitpack.io/#fengzhizi715/SAF-Kotlin-log)
saf-log-extension|[![](https://jitpack.io/v/fengzhizi715/SAF-Kotlin-log.svg)](https://jitpack.io/#fengzhizi715/SAF-Kotlin-log)

# 下载安装

将它添加到项目的 root build.gradle 中：

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Gradle:

```groovy
implementation 'com.github.fengzhizi715.SAF-Kotlin-log:core:<latest-version>'
```

```groovy
implementation 'com.github.fengzhizi715.SAF-Kotlin-log:file:<latest-version>'
```

```groovy
implementation 'com.github.fengzhizi715.SAF-Kotlin-log:okhttp:<latest-version>'
```

```groovy
implementation 'com.github.fengzhizi715.SAF-Kotlin-log:fastjson:<latest-version>'
```

```groovy
implementation 'com.github.fengzhizi715.SAF-Kotlin-log:gson:<latest-version>'
```

```groovy
implementation 'com.github.fengzhizi715.SAF-Kotlin-log:debug_view:<latest-version>'
```

```groovy
implementation 'com.github.fengzhizi715.SAF-Kotlin-log:extension:<latest-version>'
```


# TODO List：

* 打算提供一个 WebPrinter，支持日志打印到一个基于 Android 的 Web Server
* Debug View 增加属性
* 支持 ndk 的日志存储到文件
* 支持 mmap 存储日志文件


联系方式
===

Wechat：fengzhizi715


> Java与Android技术栈：每周更新推送原创技术文章，欢迎扫描下方的公众号二维码并关注，期待与您的共同成长和进步。

![](https://github.com/fengzhizi715/NetDiscovery/blob/master/images/gzh.jpeg)


ChangeLog
===
[版本更新记录](CHANGELOG.md)

License
-------

    Copyright (C) 2017 - present, Tony Shen.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
