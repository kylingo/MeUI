# Android基础

## 五种布局
- FrameLayout，框架布局，最简单
- LinearLayout，线性布局，最常用
- RelativeLayout，相对布局，最常用
- AbsoluteLayout，绝对布局，基本不用
- TableLayout，表格布局，特定情况用

布局选择：
- View的绘制进行measure, layout, draw，分别对应onMeasure(), onLayout, onDraw()，而他们的性能差异主要在onMeasure
- RelativeLayout将对所有的子View进行两次measure，而LinearLayout在使用weight属性进行布局时也会对子View进行两次measure，如果他们位于整个View树的顶端时并可能进行多层的嵌套时，位于底层的View将会进行大量的measure操作，大大降低程序性能。因此，应尽量将RelativeLayout和LinearLayout置于View树的底层，并减少嵌套，顶层尽量采用FrameLayout。

## Activity生命周期
- onCreate
- onStart
- onResume
- onPause
- onStop
- onDestroy

几种状态变更：
- 退居后台：当前Activity转到新的Activity界面或按Home键回到主屏， onPause()—>onStop()，进入停滞状态。
  - 当系统内存不足时，系统会杀死这个Activity，但是这个Activity的应用还在任务栈，只是指向的对象为null。若再次启动Activity，会重新走一遍生命周期。
- 回到前台：onRestart()—>onStart()—>onResume()，再次回到运行状态。
- 锁屏：onPause()->onStop()；解锁：onStart()->onResume()

## Activity启动模式
- standard，标准模式，每次启动重新创建一个实例，执行生命周期。
- singleTop，栈顶复用模式，如果Activiy已经位于栈顶，则不会创建实例，只执行onNewIntent，否则和标准模式一样。
- singleTask，栈内复用模式，创建这样的Activity的时候,系统会先确认它所需任务栈已经创建,否则先创建任务栈.然后放入Activity,如果栈中已经有一个Activity实例,那么这个Activity就会被调到栈顶,onNewIntent(),并且singleTask会清理在当前Activity上面的所有Activity.(clear top)
- singleInstance : 加强版的singleTask模式,这种模式的Activity只能单独位于一个任务栈内,由于栈内复用的特性,后续请求均不会创建新的Activity,除非这个独特的任务栈被系统销毁了

Android:launchMode="standard/singleInstance/singleTask/singleTop"来控制Acivity任务栈。Activity的堆栈管理以ActivityRecord为单位,所有的ActivityRecord都放在一个List里面.可以认为一个ActivityRecord就是一个Activity栈

## Activity缓存
要注意的几点：
- 布局中的每一个View默认实现了onSaveInstanceState()方法，这样的话，这个UI的任何改变都会自动地存储和在activity重新创建的时候自动地恢复。但是这种情况只有在你为这个UI提供了唯一的ID之后才起作用，如果没有提供ID，app将不会存储它的状态。

- 由于默认的onSaveInstanceState()方法的实现帮助UI存储它的状态，所以如果你需要覆盖这个方法去存储额外的状态信息，你应该在执行任何代码之前都调用父类的onSaveInstanceState()方法（super.onSaveInstanceState()）。 既然有现成的可用，那么我们到底还要不要自己实现onSaveInstanceState()?这得看情况了，如果你自己的派生类中有变量影响到UI，或你程序的行为，当然就要把这个变量也保存了，那么就需要自己实现，否则就不需要。

- 由于onSaveInstanceState()方法调用的不确定性，你应该只使用这个方法去记录activity的瞬间状态（UI的状态）。不应该用这个方法去存储持久化数据。当用户离开这个activity的时候应该在onPause()方法中存储持久化数据（例如应该被存储到数据库中的数据）。

- onSaveInstanceState()如果被调用，这个方法会在onStop()前被触发，但系统并不保证是否在onPause()之前或者之后触发。

- onSaveInstanceState方法和onRestoreInstanceState方法“不一定”是成对的被调用的，（本人注：我昨晚调试时就发现原来不一定成对被调用的！）

## Fragment生命周期
- onAttach
- onCreate
- onCreateView
- onViewCreated
- onActivityCreated
- ...like activity
- onDestroyView
- onDestroy
- onDetach

## Intent可以传递的数据类型
- 基本类型
- String和CharSequence
- 实现了Serializable或Parcelable的类
- 上述类型的数组或列表

## Service启动方法
- bindService，进行Service与Context的关联并启动，并且Service的生命周期依附于Context(不求同时同分同秒生！但求同时同分同秒屎！！)。
- startService，方法去启动一个Service，此时Service的生命周期与启动它的Context无关。
- 要注意的是，whatever，都需要在xml里注册你的Service，就像这样:
```Java
<service
        android:name=".packnameName.youServiceName"
        android:enabled="true" />
```

## 广播的注册方式
- 静态注册：在AndroidManifest.xml文件中进行注册，当App退出后，Receiver仍然可以接收到广播并且进行相应的处理
- 动态注册：在代码中动态注册，当App退出后，也就没办法再接受广播了

## 目前能否保证service不被杀死
Service设置成START_STICKY
- kill 后会被重启（等待5秒左右），重传Intent，保持与重启前一样

提升service优先级
- 在AndroidManifest.xml文件中对于intent-filter可以通过android:priority = "1000"这个属性设置最高优先级，1000是最高值，如果数字越小则优先级越低，同时适用于广播。
- 【结论】目前看来，priority这个属性貌似只适用于broadcast，对于Service来说可能无效

提升service进程优先级
- Android中的进程是托管的，当系统进程空间紧张的时候，会依照优先级自动进行进程的回收
- 当service运行在低内存的环境时，将会kill掉一些存在的进程。因此进程的优先级将会很重要，可以在startForeground()使用startForeground()将service放到前台状态。这样在低内存时被kill的几率会低一些。
【结论】如果在极度极度低内存的压力下，该service还是会被kill掉，并且不一定会restart()

onDestroy方法里重启service
- service +broadcast 方式，就是当service走onDestory()的时候，发送一个自定义的广播，当收到广播的时候，重新启动service
也可以直接在onDestroy()里startService
- 【结论】当使用类似口口管家等第三方应用或是在setting里-应用-强制停止时，APP进程可能就直接被干掉了，onDestroy方法都进不来，所以还是无法保证

监听系统广播判断Service状态
- 通过系统的一些广播，比如：手机重启、界面唤醒、应用状态改变等等监听并捕获到，然后判断我们的Service是否还存活，别忘记加权限
- 【结论】这也能算是一种措施，不过感觉监听多了会导致Service很混乱，带来诸多不便

在JNI层,用C代码fork一个进程出来
- 这样产生的进程,会被系统认为是两个不同的进程.但是Android5.0之后可能不行
- root之后放到system/app变成系统级应用

## Android数据存储形式
- SQLite：SQLite是一个轻量级的数据库，支持基本的SQL语法，是常被采用的一种数据存储方式。 Android为此数据库提供了一个名为SQLiteDatabase的类，封装了一些操作数据库的api

- SharedPreference： 除SQLite数据库外，另一种常用的数据存储方式，其本质就是一个xml文件，常用于存储较简单的参数设置。

- File： 即常说的文件（I/O）存储方法，常用语存储大数量的数据，但是缺点是更新数据将是一件困难的事情。

- ContentProvider: Android系统中能实现所有应用程序共享的一种数据存储方式，由于数据通常在各应用间的是互相私密的，所以此存储方式较少使用，但是其又是必不可少的一种存储方式。例如音频，视频，图片和通讯录，一般都可以采用此种方式进行存储。每个Content Provider都会对外提供一个公共的URI（包装成Uri对象），如果应用程序有数据需要共享时，就需要使用Content Provider为这些数据定义一个URI，然后其他的应用程序就通过Content Provider传入这个URI来对数据进行操作。

## 自定义View
自定义View过程：onMeasure()、onLayout()、onDraw()。

如何自定义控件：
- 自定义属性的声明和获取
  - 分析需要的自定义属性
  - 在res/values/attrs.xml定义声明
  - 在layout文件中进行使用
  - 在View的构造方法中进行获取

- 测量onMeasure

- 布局onLayout(ViewGroup)

- 绘制onDraw

- onTouchEvent

- onInterceptTouchEvent(ViewGroup)

- 状态的恢复与保存

## 怎样退出终止App
- System.exit(0);

## Android5.0、6.0、7.0新特性。
Android5.0新特性：
- MaterialDesign设计风格
- 支持多种设备
- 支持64位ART虚拟机

Android6.0新特性
- 大量漂亮流畅的动画
- 支持快速充电的切换
- 支持文件夹拖拽应用
- 相机新增专业模式

Android7.0新特性
- 分屏多任务
- 增强的Java8语言模式
- 夜间模式

## Context区别
- Activity和Service以及Application的Context是不一样的,Activity继承自ContextThemeWraper.其他的继承自ContextWrapper
- 每一个Activity和Service以及Application的Context都是一个新的ContextImpl对象
- getApplication()用来获取Application实例的，但是这个方法只有在Activity和Service中才能调用的到。那么也许在绝大多数情况下我们都是在Activity或者Service中使用Application的，但是如果在一些其它的场景，比如BroadcastReceiver中也想获得Application的实例，这时就可以借助getApplicationContext()方法，getApplicationContext()比getApplication()方法的作用域会更广一些，任何一个Context的实例，只要调用getApplicationContext()方法都可以拿到我们的Application对象。
- Activity在创建的时候会new一个ContextImpl对象并在attach方法中关联它，Application和Service也差不多。ContextWrapper的方法内部都是转调ContextImpl的方法
- 创建对话框传入Application的Context是不可以的
- 尽管Application、Activity、Service都有自己的ContextImpl，并且每个ContextImpl都有自己的mResources成员，但是由于它们的mResources成员都来自于唯一的ResourcesManager实例，所以它们看似不同的mResources其实都指向的是同一块内存
- Context的数量等于Activity的个数 + Service的个数 + 1，这个1为Application

## IntentService的使用场景与特点。
`IntentService是Service的子类，是一个异步的，会自动停止的服务，很好解决了传统的Service中处理完耗时操作忘记停止并销毁Service的问题`

优点：
- 一方面不需要自己去new Thread
- 另一方面不需要考虑在什么时候关闭该Service

onStartCommand中回调了onStart，onStart中通过mServiceHandler发送消息到该handler的handleMessage中去。最后handleMessage中回调onHandleIntent(intent)。

## 你是怎么学习Android的
首先是看书和看视频敲代码，然后看大牛的博客，做一些项目，向github提交代码，觉得自己API掌握的不错之后，开始看进阶的书，以及看源码，看完源码学习到一些思想，开始自己造轮子，开始想代码的提升，比如设计模式，架构，重构等。
