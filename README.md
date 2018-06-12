# Awesome Android

> 该项目旨在：
> 1. 整理开发中经常使用到的一些库和控件，并将其封装为可以通用的模块；
> 2. 整理Android设备上经典的Material Design风格的布局页面；
> 3. Android开发中比较复杂的部分，比如多媒体的示例；
> 4. 设计和使用Mvvm+Dagger+Retrofit的架构模式。

该项目使用了Mvvm+Dagger+Retrofit的架构模式，功能点主要有：

1. 基于《全民直播》API设计的视频直播功能，可以在线视频播放，并使用了支持包里的Palette来提取图片的颜色；
2. 基于《果壳网》API设计的新闻客户端，包含基本的"列表-详情"结构；
3. 整理了Material设计相关的布局页面；
4. 该项目的Commons模块中整理和封装了许多开发中经常用到的一些类和库，比如时间库Joda time和Preety time，文件访问库Commons-io等等。

后续会不断完善

## 1、《全民直播》API的使用：

使用了pldroid-player作为视频播放的工具，使用了Mvvm+Dagger+Retrofit的架构模式，并且在列表页面中使用Palette来提取图片的颜色。

<div style="display:flex;" >
<img  src="images/1_0.png" width="24%" >
<img style="margin-left:10px;" src="images/1_1.png" width="24%" >
<img style="margin-left:10px;" src="images/1_2.png" width="24%" >
<img style="margin-left:10px;" src="images/1_3.png" width="24%" >
</div>

## 2、《果壳网》的使用

使用了Mvvm+Dagger+Retrofit的架构模式，新闻的内容详情使用Webview来进行展示。

<div style="display:flex;" >
<img  src="images/2_1.png" width="24%" >
<img style="margin-left:10px;" src="images/2_2.png" width="24%" >
</div>

## 3、Material Design布局的整理

主要包括：

1. Navigation 布局
2. Tabbed 布局
3. Bottom sheet 布局
4. Scrolling 布局
5. Collapse 布局

<div style="display:flex;" >
<img src="images/3_1.png" width="19%" >
<img style="margin-left:10px;" src="images/3_2.png" width="19%" >
<img style="margin-left:10px;" src="images/3_3.png" width="19%" >
<img style="margin-left:10px;" src="images/3_4.png" width="19%" >
<img style="margin-left:10px;" src="images/3_5.png" width="19%" >
</div>


