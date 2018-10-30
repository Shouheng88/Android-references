# Awesome Android

> 该项主要用来收集和整理开发过程中经常用到的三方库和控件，并包含一些演示代码。[可以参考下文来了解更多的内容]

## 代码清单

### 1、整体结构

```
/-----
     /----- advanced           IPC, AIDL
     /----- animations         CircularReveal, TapTargetView, Ripple, etc
     /----- client             整体 APP 打包
     /----- commons            公共库
     /----- eyepetizer         开眼视频, MVP
     /----- guokr              果壳新闻
     /----- knife-annotations  ButterKnife 注解
     /----- knife-api          ButterKnife API
     /----- knife-compiler     ButterKnife 编译器
     /----- layout             MaterialDesign
     /----- libraries          指纹识别, EventBus, WorkManager, Knife etc
     /----- live               全民直播
```

注：各个模块借助 `ARouter` 实现了模块化开发，可以通过修改 [gradle.properties](client/gradle.properties) 中的属性来实现各个模块独立打包

### 2、视频直播

对应于 `live` 模块，该模块主要用来演示**视频直播**相关的功能：

基于《全民直播》的 API 设计的在线视频直播功能；使用了支持包里的 `Palette` 来提取图片的颜色；`MVVM` 架构设计 (在该项目中的使用不符合规范，谨慎参考)；使用了pldroid-player作为视频播放的工具。

部分截图：

<div style="display:flex;" >
    <img  src="images/1_0.png" width="24%" >
    <img style="margin-left:10px;" src="images/1_1.png" width="24%" >
    <img style="margin-left:10px;" src="images/1_2.png" width="24%" >
    <img style="margin-left:10px;" src="images/1_3.png" width="24%" >
</div>

### 3、果壳新闻

对应于 `guokr` 模块，该模块主要用来演示`OkHttp + Retrofit + RexJava`的开发方式：

基于《果壳网》 API 设计的新闻客户端，包含基本的"列表-详情"结构；MVVM 架构设计 (在该项目中的使用不符合规范，谨慎参考)。

<div style="display:flex;" >
    <img  src="images/2_1.png" width="24%" >
    <img style="margin-left:10px;" src="images/2_2.png" width="24%" >
</div>

### 4、开眼视频

对应于 `eyepetizer` 模块，该模块主要用来演示**小视频**类型的 APP 相关的功能，同时演示 `MVP` 架构模式在 Android 端的使用方式：

MVP 架构设计；基于《开眼视频》的 API 设计视频浏览客户端。

注：项目比较小，功能比较少，主要用来演示核心的网络视频播放功能。

### 5、MaterialDesign

对应于 `layout` 模块，该模块主要用来整理 MaterialDesign 相关的布局和控件，目前包含的布局有：

`Navigation`、`Tabbed`、`Bottom sheet`、`Scrolling`、`Collapse`、`Support 28` 中的部分控件。

<div style="display:flex;" >
    <img src="images/3_1.png" width="19%" >
    <img style="margin-left:10px;" src="images/3_2.png" width="19%" >
    <img style="margin-left:10px;" src="images/3_3.png" width="19%" >
    <img style="margin-left:10px;" src="images/3_4.png" width="19%" >
    <img style="margin-left:10px;" src="images/3_5.png" width="19%" >
</div>

### 6、其他

1. 自定义类似于 `ButterKnife` 的库，文件路径包含 [knife-annotation](knife-annotation)、[knife-api](knife-api) 和 [knife-compiler](knife-compiler)

    该部分内容需要使用 Java 中的注解以及注解处理，你可以通过这篇文章来了解这部分功能如何实现：[《Java 注解及其在 Android 中的应用》](https://juejin.im/post/5b824b8751882542f105447d)

2. 使用 `RxJava2` 搭建一个 `EventBus`，文件路径在 [rxbus](commons\src\main\java\me\shouheng\commons\rxbus)

    该部分使用 `RxJava2` 实现一个类似于 `EventBus` 的全局通信的框架，相关的知识可以通过这篇文章进行了解：[《RxJava2 系列 (3)：使用 Subject》](https://juejin.im/post/5b801dfa51882542cb409905)

3. 在该项目中使用了 `MVP` 和 `MVVM` 两种架构设计方式，同时使用了 `ARouter` 来实现了模块化开发，你可以通过这篇文章来了解相关的知识：[《Android 架构设计：MVC、MVP、MVVM和组件化》](https://juejin.im/post/5b7c1706f265da436d7e408e)

4. 如果你希望了解 `OkHttp` 的源码相关的知识，请参考我的这篇文章：[《Andriod 网络框架 OkHttp 源码解析》](https://juejin.im/post/5bc89fbc5188255c713cb8a5)


4. 如果你希望了解 `Retrofit` 的源码相关的知识，其中使用了哪些设计模式等等，请参考我的这篇文章：[《Android 网络框架 Retrofit 源码解析》](https://juejin.im/post/5bd05d5c6fb9a05d2b6dfc46)


