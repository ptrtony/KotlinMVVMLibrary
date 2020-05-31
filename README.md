# KotlinMVVMLibrary
使用mvvm框架搭建Android app框架 并使用插件的方式进行初始化操作
使用语言：kotlin
网络框架技术：retrofit + rxjava
自动注册初始化操作库框架使用的是CC库中的autoregister技术
Kodein库的使用 类似于dagger2库功能
结构思想：MVVM框架思想  实现框架使用的技术google 的jetpack 包括viewmodel + livedata + lifecycle
数据库使用：room

模块结构：
YCExtKt 扩展库
YCDevVM mvvm基础类库
YCDevDataBinding 自定义属性控件库
YCDevCore 请求网络相关的库
YCDevComponent 中间桥梁库