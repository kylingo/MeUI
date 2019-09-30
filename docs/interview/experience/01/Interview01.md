## 脉脉上的面试题
![](./interview01.jpg)

## 1. 如果将Activity实例放入String列表
- List<String>强转为List，再调用add方法
- 采用反射

## 2. 实时查看APP某个界面的帧率
使用编舞者的回调
```
public interface FrameCallback {  
  //当新的一帧被绘制的时候被调用。  
   public void doFrame(long frameTimeNanos);
}

// 注册回调
Choreographer.getInstance().postFrameCallback(new FrameCallback());
```
参考：http://www.jianshu.com/p/d126640eccb1

## 3. BBinder和BpBinder
```
这3个类，是对Android Binder框架的抽象，其实这个BBinder，改成BnBinder可能更形象一些。
但是要注意的是，一个IXXXService的继承图中，BpBinder并不在这个继承关系之中，也就是说BpBinder并没有子类。
但是BBinder是在这个继承关系当中的，它的子类就是BnInterface。
换句话说，BBinder和BpBinder的功能并不是对称的，以前就是没有理解到这一点，才会一直很糊涂。

BpBinder的是存在于BpRefBase中的mRemote的成员变量中。从Client调用Service的过程中分析，就更清楚了。
假设有一个IXXXService接口：
class IXXXService : public IInterface {
    ....
    public void helloWorld(const char* str);
    ....
}
（1）client调用service
    client得到一个BpXXXService以后
    （a）会调用BpXXXService实现的helloWorld，它会将str参数打包到Parcel中。然后调用remote()->transact(xxx)
    （b）remote()是在BpXXXService的父类BpRefBase中实现的，返回的就是一个BpBinder.实际上调用的就是BpBinder的transact
    （c）BpBinder的transact实现，就是直接调用IPCThreadState::self()->transact()发送数据。
（2）service接收client请求：
    （a）通过IPCThreadState接收到client的请求后，首先会调用BBinder的transact方法。
    （b）BBinder的transact方法又会调用子类实现的虚拟方法onTransact。这个虚拟方法是在BnXXXService中实现的。
    （c）onTransact方法，会通过传递进来的参数来判断，需要调用IXXXService中的那个方法，示例中只有一个helloWorld方法。
    （d）直接调用helloWorld，就会找到它的真正实现，也就是BnXXXService的子类XXXService中的helloWorld方法。
总结一下，从上面的流程当中就可以看出前文说的，BpBinder并不在继承关系当中，它只是一个打包数据，并通过IPCThreadState::self()->transact()方法发送出去。
而BBinder和BnXXXService的作用，就是接收IPCThreadState传递过来的信息，解包数据，并调用XXXService真正的实现。

IPC的数据处理，Binder Driver和ServiceManager学习后会继续分析总结。
```

## 4. Android为什么会有65535的限制  
搬上Dalvik工程师在SF上的回答，因为在Dalvik指令集里，调用方法的invoke-kind指令中，method reference index只给了16bits，最多能调用65535个方法，所以在生成dex文件的过程中，当方法数超过65535就会报错。细看指令集，除了method，field和class的index也是16bits，所以也存在65535的问题。一般来说，method的数目会比field和class多，所以method数会首先遇到65535问题，你可能都没机会见到field过65535的情况。

## 5. Lint的工作原理  
参考：https://juejin.im/entry/576b60f479bc44005bdb08ce
```
private List runLint(
         AndroidProject modelProject,
         Variant variant,
        boolean report) {
    IssueRegistry registry = createIssueRegistry()
    LintCliFlags flags = new LintCliFlags()
    LintGradleClient client = new LintGradleClient(registry, flags, project, modelProject,
            mSdkHome, variant, getBuildTools())
    warnings = client.run(registry)
    }
```

这里的BuiltinIssueRegistry我们刚才也提到了，用户平时在执行gradle lint时默认会执行200多项检查，这些默认检查项目都是Android SDK通过BuiltinIssueRegistry定义的。

继续执行上面的run（）方法，new出来的LintGradleClient实际上是com.android.tools.lint.LintCliClient的子类，这个类的作用是提供执行lint任务的环境信息（比如控制台、IDE的信息），执行IssueRegistry中定义的各种ISSUE检查，以及以多种形式输出lint报告等。

继续执行run（）方法，也就是warnings = client.run(registry)。看到这里终于知道BuiltinIssueRegistry中定义的200多项ISSUE是如何被gradle的lint任务引入检查了。

到这里为止，对groovy文件的分析就结束了，由于LintGradleClient是继承自java类LintCliClient，后续真正的lint检查工作都通过client.run(registry)这句话转交给java实现的LintCliClient类来完成。

## 6. DexClassLoader和PathClassLoader区别
### DexClassLoader
`public DexClassLoader (String dexPath, String dexOutputDir, String libPath, ClassLoader parent)`

参数详解：
- dexPath：dex文件路径列表，多个路径使用”:”分隔
- dexOutputDir：经过优化的dex文件（odex）文件输出目录
- libPath：动态库路径（将被添加到app动态库搜索路径列表中）
- parent：这是一个ClassLoader，这个参数的主要作用是保留java中ClassLoader的委托机制（优先父类加载器加载classes，由上而下的加载机制，防止重复加载类字节码）

DexClassLoader是一个可以从包含classes.dex实体的.jar或.apk文件中加载classes的类加载器。可以用于实现dex的动态加载、代码热更新等等。这个类加载器必须要一个app的私有、可写目录来缓存经过优化的classes（odex文件），使用Context.getDir(String, int)方法可以创建一个这样的

### PathClassLoader
```
public PathClassLoader (String path, ClassLoader parent)
public PathClassLoader (String path, String libPath, ClassLoader parent)
```

参数详解：
- path：文件或者目录的列表
- libPath：包含lib库的目录列表
- parent：父类加载器

PathClassLoader提供一个简单的ClassLoader实现，可以操作在本地文件系统的文件列表或目录中的classes，但不可以从网络中加载classes。

### 二者区别
- DexClassLoader：能够加载未安装的jar/apk/dex
- PathClassLoader：只能加载系统中已经安装过的apk

参考：http://blog.csdn.net/mynameishuangshuai/article/details/52737581

## 7. Dalvik和ART区别
- Dalvik:  
 Dalvik是Google公司自己设计用于Android平台的Java虚拟机。Dalvik虚拟机是Google等厂商合作开发的Android移动设备平台的核心组成部分之一。它可以支持已转换为 .dex（即Dalvik Executable）格式的Java应用程序的运行，.dex格式是专为Dalvik设计的一种压缩格式，适合内存和处理器速度有限的系统。Dalvik 经过优化，允许在有限的内存中同时运行多个虚拟机的实例，并且每一个Dalvik 应用作为一个独立的Linux 进程执行。独立的进程可以防止在虚拟机崩溃的时候所有程序都被关闭。

- 什么是ART:  
 Android操作系统已经成熟，Google的Android团队开始将注意力转向一些底层组件，其中之一是负责应用程序运行的Dalvik运行时。Google开发者已经花了两年时间开发更快执行效率更高更省电的替代ART运行时。 ART代表Android Runtime，其处理应用程序执行的方式完全不同于Dalvik，Dalvik是依靠一个Just-In-Time (JIT)编译器去解释字节码。开发者编译后的应用代码需要通过一个解释器在用户的设备上运行，这一机制并不高效，但让应用能更容易在不同硬件和架构上运 行。ART则完全改变了这套做法，在应用安装时就预编译字节码到机器语言，这一机制叫Ahead-Of-Time (AOT）编译。在移除解释代码这一过程后，应用程序执行将更有效率，启动更快。L版本正式推出。

  ART优点：
  - 系统性能的显著提升。
  - 应用启动更快、运行更快、体验更流畅、触感反馈更及时。
  - 更长的电池续航能力。
  - 支持更低的硬件。

  ART缺点：
  - 更大的存储空间占用，可能会增加10%-20%。
  - 更长的应用安装时间。

  总的来说ART的功效就是“空间换时间”。

dex，odex和oat区别
- dex，为Dalvik虚拟机设置的一种压缩格式，解压在data目录下
- odex, odex 是经过优化的dex文件，且独立存在于apk文件。odex 多用于系统预制应用或服务。通过将apk中的dex文件进行 odex，可以加载 apk 的启动速度，同时减小空间的占用，在/data/app/package_name目录下有该文件
- oat, 一种Android私有ELF文件格式，它不仅包含有从DEX文件翻译而来的本地机器指令，还包含有原来的DEX文件内容。

## 8. 页面布局绘制流程
解释通过R.layout.main这个整数索引找到这个对应的资源文件，并显示在手机界面的流程
- 这个资源ID存在apk的arsc目录下，这个资源ID保存了资源文件的相对路径，这个路劲是存储在res/layout/xxx.xml目录下
- 找到这个xml，并加载布局到手机页面

## 9. 算法实现activity中view数的深度
