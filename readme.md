# SAF-Kotlin-log

[![@Tony沈哲 on weibo](https://img.shields.io/badge/weibo-%40Tony%E6%B2%88%E5%93%B2-blue.svg)](http://www.weibo.com/fengzhizi715)
[ ![Download](https://api.bintray.com/packages/fengzhizi715/maven/saf-log/images/download.svg) ](https://bintray.com/fengzhizi715/maven/saf-log/_latestVersion)
[![License](https://img.shields.io/badge/license-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
<a href="http://www.methodscount.com/?lib=com.safframework.log%3Asaf-log%3A1.5.0"><img src="https://img.shields.io/badge/Methods and size-core: 198 | deps: 23979 | 48 KB-e91e63.svg"/></a>

![](logo.JPG)

它是完全基于Kotlin开发的Android日志框架，提供极简的日志风格


# 下载安装
Gradle:

```groovy
compile 'com.safframework.log:saf-log:1.5.0'
```

Maven:

```groovy
<dependency>
  <groupId>com.safframework.log</groupId>
  <artifactId>saf-log</artifactId>
  <version>1.5.0</version>
  <type>pom</type>
</dependency>
```

# 功能：
* 支持自定义tag
* 支持多种日志级别
* 打印的日志，除了可以显示日志内容之外，还能显示当前线程名称、在程序中所打印日志的位置
* 支持跳转到源码处
* 支持自定义Header
* 支持对象的打印，将对象打印成json风格，方便阅读。还针对集合、Bundle、Intent、Reference、Throwable、Uri等类型的打印做了特别的格式化处理。

# 使用方法

使用本库时，无需添加kotlin的配置。它可以在任何android项目中使用。

## 1.tag使用

如果不考虑显示日志的tag，可以直接使用

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

| 方法名        | 作用          | 备注          |
| ------------- |:-------------:| :-------------:|
| e()       |Error级别打印日志|       |
| w()        |Warn级别打印日志|       |
| i()        |Info级别打印日志|       |
| d()        |Debug级别打印日志|       |
|json()      |将日志以json格式打印出来|       |

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
多种对象类型都可以使用json()打印成json风格。
### 4.1 List、Set
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

### 4.2 Map
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

### 4.3 JSON字符串
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

### 4.4 Reference
```java
        User u = new User();
        u.userName = "tony";
        u.password = "123456";
        
        L.json(new WeakReference<User>(u));
```
打印效果：
![](images/Reference.png)

### 4.5 Throwable
```java
L.json(new NullPointerException("this object is null"));
```
打印效果：
![](images/Throwable.png)

### 4.6 Bundle
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

### 4.7 Intent
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

### 4.8 Uri
```java
        Uri uri = Uri.parse("http://www.java2s.com:8080/yourpath/fileName.htm?stove=10&path=32&id=4#harvic");
        L.json(uri);
```
打印效果：
![](images/Uri.png)

## 5.自定义Header
通常情况下，可以在Header中传递一些手机等信息，方便调试时一眼识别手机的机型、操作系统版本号、App版本号等。

一旦定义好Header，所有的日志上都会显示Header的内容。

## 6.针对Kotlin项目的优化




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
