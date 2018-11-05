# SAF-Kotlin-log

[![@Tony沈哲 on weibo](https://img.shields.io/badge/weibo-%40Tony%E6%B2%88%E5%93%B2-blue.svg)](http://www.weibo.com/fengzhizi715)
[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/saf-log/images/download.svg) ](https://bintray.com/fengzhizi715/maven/saf-log/_latestVersion)
[![License](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
<a href="http://www.methodscount.com/?lib=com.safframework.log%3Asaf-log%3A1.5.1"><img src="https://img.shields.io/badge/Methods and size-198 | 49 KB-e91e63.svg"/></a>

![](logo.JPG)

它是完全基于Kotlin开发的Android日志框架，提供极简的API，能够打印出漂亮的日志格式。它基于最新的Android Studio版本、Kotlin版本和Gradle版本构建。


# 下载安装
Gradle:

```groovy
compile 'com.safframework.log:saf-log:1.5.4'
```

# 功能：
* 支持自定义tag
* 支持多种日志级别
* 打印的日志，除了可以显示日志内容之外，还能显示当前线程名称、在程序中所打印日志的位置
* 支持自定义Header
* 基于责任链模式来打造对象的打印，将对象打印成json风格，方便阅读。默认支持JSON字符串、集合、Map、Bundle、Intent、Reference、Throwable、Uri等类型的打印，分别做了特别的格式化处理。
* 支持自定义对象的解析处理，将其打印成自己想要的风格。

# 使用方法：

它可以在任何使用Java、Kotlin开发的Android项目中使用。如果您的项目中只使用Java，也无需额外添加kotlin的配置。

## 1.tag使用

如果不考虑显示日志的tag，可以直接使用，L会提供默认的tag。

```java
String s = "abcd";
L.i(s);
```

如果需要使用tag，可以在Activity的onCreate()中添加如下的代码，类名则对应是tag的名称

```java
L.init(this.getClass());

```

当然，init()除了支持传递Class对象，还支持传String对象。

## 2.日志级别

支持以下几种日志级别，除此之外还能将任何对象打印成json格式

| 方法名        | 作用          |
| ------------- |:-------------:|
| e()       |Error级别打印日志|
| w()        |Warn级别打印日志|
| i()        |Info级别打印日志|
| d()        |Debug级别打印日志|
|json()      |将日志以json格式打印出来|

还可以设置全局的日志级别，最好在Application中进行全局的配置。

java中的使用方法：

```java
L.setLogLevel(L.LogLevel.INFO);
```

kotlin中的使用方法：

```kotlin
L.logLevel= L.LogLevel.INFO
```
## 3.打印日志

以e、w、i、d打印的日志风格如下：<br>
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

json方法的使用

```java
Object obj = ...
L.json(obj);
```

## 4.支持各种对象类型的打印
多种对象类型都可以使用json()方法打印成json风格。
### 4.1 将List、Set格式化打印
```java
        User u = new User();
        u.userName = "tony";
        u.password = "123456";

        List<User> list = new ArrayList<>();
        list.add(u);
        list.add(u);
        L.json(list);

        List<String> ids = new ArrayList<>();
        ids.add("123");
        ids.add("456");
        L.json(ids);

        List<Double> idd = new ArrayList<>();
        idd.add(123D);
        idd.add(456D);
        L.json(idd);
```
打印效果：

![](images/List.png)

### 4.2 将Map格式化打印
```java
        User u = new User();
        u.userName = "tony";
        u.password = "123456";
        
        Map<String,User> map = new HashMap<>();
        map.put("tony",u);
        map.put("tt",u);
        L.json(map);

        Map<String,String> map2 = new HashMap<>();
        map2.put("tony","shen");
        map2.put("tt","ziyu");
        L.json(map2);

        Map<String,Boolean> map3 = new HashMap<>();
        map3.put("tony",true);
        map3.put("tt",false);
        L.json(map3);
```
打印效果：

![](images/Map.png)

### 4.3 将JSON字符串格式化打印
```java
        String jsonString = "{\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"firstName\": \"Bill\",\n" +
                "            \"lastName\": \"Gates\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"firstName\": \"George\",\n" +
                "            \"lastName\": \"Bush\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"firstName\": \"Thomas\",\n" +
                "            \"lastName\": \"Carter\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        L.json(jsonString);
``` 
打印效果：

![](images/Json_String.png)

### 4.4 将Reference格式化打印
```java
        User u = new User();
        u.userName = "tony";
        u.password = "123456";
        
        L.json(new WeakReference<User>(u));
```
打印效果：

![](images/Reference.png)

### 4.5 将Throwable格式化打印
```java
L.json(new NullPointerException("this object is null"));
```
打印效果：

![](images/Throwable.png)

### 4.6 将Bundle格式化打印
```java
        User u = new User();
        u.userName = "tony";
        u.password = "123456";
        
        Bundle bundle = new Bundle();
        bundle.putString("key1","this is key1");
        bundle.putInt("key2",100);
        bundle.putBoolean("key3",true);
        bundle.putSerializable("key4",u);
        L.json(bundle);
```
打印效果：

![](images/Bundle.png)

### 4.7 将Intent格式化打印
```java
        User u = new User();
        u.userName = "tony";
        u.password = "123456";
        
        Bundle bundle = new Bundle();
        bundle.putString("key1","this is key1");
        bundle.putInt("key2",100);
        bundle.putBoolean("key3",true);
        bundle.putSerializable("key4",u);

        Intent i = new Intent(this,MainActivity.class);
        i.putExtras(bundle);
        L.json(i);
```
打印效果：

![](images/Intent.png)

### 4.8 将Uri格式化打印
```java
        Uri uri = Uri.parse("http://www.java2s.com:8080/yourpath/fileName.htm?stove=10&path=32&id=4#harvic");
        L.json(uri);
```
打印效果：

![](images/Uri.png)

## 5.自定义Header

我们公司之前的产品是做deep link以及移动端广告相关的sdk。从开发这些产品中，我们获得的经验是日志里要是能够记录手机的一些信息，方便根据机型和操作系统版本来调试bug就好了。

由此产生了L的Header。通常情况下，把手机的一些信息放入Header中，方便调试时一眼识别手机出的机型、操作系统版本号、App版本号等等。一旦定义好Header，所有使用L的日志上都会显示Header的内容。

## 6.针对Kotlin项目的优化
借助Kotlin的扩展函数的特性，任何对象都可以使用json()方法来打印其自身。


联系方式
===

Wechat：fengzhizi715


> Java与Android技术栈：每周更新推送原创技术文章，欢迎扫描下方的公众号二维码并关注，期待与您的共同成长和进步。

![](https://user-gold-cdn.xitu.io/2018/7/24/164cc729c7c69ac1?w=344&h=344&f=jpeg&s=9082)


ChangeLog
===
[版本更新记录](CHANGELOG.md)

License
-------

    Copyright (C) 2017 Tony Shen.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
