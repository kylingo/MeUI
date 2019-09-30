# Java基础

## 问题点
- [基本数据类型](#基本数据类型)
- [switch参数](#switch参数)
- [equal和等于的区别](#equal和等于的区别)
- [Java的四种引用](#java的四种引用)
- [Hashcode的作用](#hashcode的作用)
- [String和StringBuffer和StringBuilder的区别](string和stringbuffer和stringbuilder的区别)
- [try和catch和finally](#try和catch和finally)
- [Excption与Error区别](#excption与error区别)
- [Java面向对象的三个特征与含义](#java面向对象的三个特征与含义)
- [多态的原理](#多态的原理)
- [interface与abstract类的区别](#interface与abstract类的区别)
- [内部静态类](#内部静态类)
- [循环的效率比较](#循环的效率比较)
- [反射机制](#反射机制)
- [类型擦除](#类型擦除)

## 基本数据类型
8种基本数据类型，int, byte, short, long, float, double, character, boolean  
8种基本类型的封装，Integer, Byte, Short, Long, Float, Double, Character, Boolean

## switch参数
在Java 5以前，switch(expr)中，expr只能是byte、short、char、int。  
从Java 5开始，Java中引入了枚举类型，expr也可以是enum类型。   
从Java 7开始，expr还可以是字符串（String），但是长整型（long）在目前所有的版本中都是不可以的。

## equal和等于的区别
- ==使用场景
  - 比较基本数据类型，值是否相等。
  - 引用间比较，比较内存值的引用对象是不是同一个。
- equal使用场景
  - Object方法，比较二个对象的内存值是否相同。
  - 在某些子类会重写equal方法，例如String、Integer。

## Java的四种引用
- 强引用（Strong Reference） 最常用的引用类型，如Object obj = new Object(); 。只要强引用存在则GC时则必定不被回收。

- 软引用（Soft Reference） 用于描述还有用但非必须的对象，当堆将发生OOM（Out Of Memory）时则会回收软引用所指向的内存空间，若回收后依然空间不足才会抛出OOM。一般用于实现内存敏感的高速缓存。 当真正对象被标记finalizable以及的finalize()方法调用之后并且内存已经清理, 那么如果SoftReference object还存在就被加入到它的 ReferenceQueue.只有前面几步完成后,Soft Reference和Weak Reference的get方法才会返回null

- 弱引用（Weak Reference） 发生GC时必定回收弱引用指向的内存空间。 和软引用加入队列的时机相同

- 虚引用（Phantom Reference) 又称为幽灵引用或幻影引用，虚引用既不会影响对象的生命周期，也无法通过虚引用来获取对象实例，仅用于在发生GC时接收一个系统通知。

## Hashcode的作用
http://c610367182.iteye.com/blog/1930676

以Java.lang.Object来理解,JVM每new一个Object,它都会将这个Object丢到一个Hash哈希表中去,这样的话,下次做Object的比较或者取这个对象的时候,它会根据对象的hashcode再从Hash表中取这个对象。这样做的目的是提高取对象的效率。具体过程是这样:

new Object(),JVM根据这个对象的Hashcode值,放入到对应的Hash表对应的Key上,如果不同的对象确产生了相同的hash值,也就是发生了Hash key相同导致冲突的情况,那么就在这个Hash key的地方产生一个链表,将所有产生相同hashcode的对象放到这个单链表上去,串在一起。

比较两个对象的时候,首先根据他们的hashcode去hash表中找他的对象,当两个对象的hashcode相同,那么就是说他们这两个对象放在Hash表中的同一个key上,那么他们一定在这个key上的链表上。那么此时就只能根据Object的equal方法来比较这个对象是否equal。当两个对象的hashcode不同的话，肯定他们不能equal.

## String和StringBuffer和StringBuilder的区别
Java 平台提供了两种类型的字符串：String和StringBuffer / StringBuilder，它们可以储存和操作字符串。
- String, 是只读字符串，也就意味着String引用的字符串内容是不能被改变的。
- StringBuffer和StringBulder类表示的字符串对象可以直接进行修改。
  - StringBuffer是线程安全的。
  - StringBuilder是JDK1.5引入的，它和StringBuffer的方法完全相同，区别在于它是单线程环境下使用的，因为它的所有方面都没有被synchronized修饰，因此它的效率也比StringBuffer略高。

## try和catch和finally
try catch finally，try里有return，finally还执行么？

会执行，在方法 返回调用者前执行。Java允许在finally中改变返回值的做法是不好的，因为如果存在finally代码块，try中的return语句不会立马返回调用者，而是纪录下返回值待finally代码块执行完毕之后再向调用者返回其值，然后如果在finally中修改了返回值，这会对程序造成很大的困扰，C#中就从语法规定不能做这样的事。

## Excption与Error区别
Error, 表示系统级的错误和程序不必处理的异常，是恢复不是不可能但很困难的情况下的一种严重问题；比如内存溢出，不可能指望程序能处理这样的状况；

Exception, 表示需要捕捉或者需要程序进行处理的异常，是一种设计或实现问题；也就是说，它表示如果程序运行正常，从不会发生的情况。

Java将可抛出(Throwable)的结构分为三种类型，都继承Throwable：
- 被检查的异常(Checked Exception)
- 运行时异常(RuntimeException)
- 错误(Error)。

## Java面向对象的三个特征与含义
- 继承：继承是从已有类得到继承信息创建新类的过程。提供继承信息的类被称为父类（超类、基类）；得到继承信息的类被称为子类（派生类）。继承让变化中的软件系统有了一定的延续性，同时继承也是封装程序中可变因素的重要手段。

- 封装：通常认为封装是把数据和操作数据的方法绑定起来，对数据的访问只能通过已定义的接口。面向对象的本质就是将现实世界描绘成一系列完全自治、封闭的对象。我们在类中编写的方法就是对实现细节的一种封装；我们编写一个类就是对数据和数据操作的封装。可以说，封装就是隐藏一切可隐藏的东西，只向外界提供最简单的编程接口（可以想想普通洗衣机和全自动洗衣机的差别，明显全自动洗衣机封装更好因此操作起来更简单；我们现在使用的智能手机也是封装得足够好的，因为几个按键就搞定了所有的事情）。

- 多态性：是指允许不同子类型的对象对同一消息作出不同的响应。简单的说就是用同样的对象引用调用同样的方法但是做了不同的事情。多态性分为编译时的多态性和运行时的多态性。
  - 方法重载（overload）实现的是编译时的多态性（也称为前绑定），而方法重写（override）实现的是运行时的多态性（也称为后绑定）。
  - 运行时的多态是面向对象最精髓的东西，要实现多态需要做两件事：1. 方法重写（子类继承父类并重写父类中已有的或抽象的方法）；2. 对象造型（用父类型引用引用子类型对象，这样同样的引用调用同样的方法就会根据子类对象的不同而表现出不同的行为）。

## 多态的原理
当JVM执行Java字节码时，类型信息会存储在方法区中，为了优化对象的调用方法的速度，方法区的类型信息会增加一个指针，该指针指向一个记录该类方法的方法表，方法表中的每一个项都是对应方法的指针。

## interface与abstract类的区别
- 结构
  - 抽象类和接口都不能够实例化，但可以定义抽象类和接口类型的引用。一个类如果继承了某个抽象类或者实现了某个接口都需要对其中的抽象方法全部进行实现，否则该类仍然需要被声明为抽象类。
  - 接口比抽象类更加抽象，因为抽象类中可以定义构造器，可以有抽象方法和具体方法，而接口中不能定义构造器而且其中的方法全部都是抽象方法。

- 变量类型  
  - 抽象类中的成员可以是private、默认、protected、public的，而接口中的成员全都是public的。  
  - 抽象类中可以定义成员变量，而接口中定义的成员变量实际上都是常量。有抽象方法的类必须被声明为抽象类，而抽象类未必要有抽象方法。

## 内部静态类
- 内部静态类不需要有指向外部类的引用。
- 非静态内部类需要持有对外部类的引用。非静态内部类能够访问外部类的静态和非静态成员。静态类不能访问外部类的非静态成员。他只能访问外部类的静态成员。一个非静态内部类不能脱离外部类实体被创建，一个非静态内部类可以访问外部类的数据和方法，因为他就在外部类里面。


## 循环的效率比较
直接for循环效率最高，其次是迭代器和 ForEach操作。 作为语法糖，其实 ForEach 编译成 字节码之后，使用的是迭代器实现的，反编译后，testForEach方法如下：
```Java
public static void testForEach(List list) {  
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {  
        Object t = iterator.next();  
        Object obj = t;  
    }  
}  
```
可以看到，只比迭代器遍历多了生成中间变量这一步，因为性能也略微下降了一些。

## 反射机制
JAVA反射机制是在运行状态中, 对于任意一个类, 都能够知道这个类的所有属性和方法; 对于任意一个对象, 都能够调用它的任意一个方法和属性; 这种动态获取的信息以及动态调用对象的方法的功能称为java语言的反射机制.

主要作用有三个：
- 运行时取得类的方法和字段的相关信息。
- 创建某个类的新实例(.newInstance())
- 取得字段引用直接获取和设置对象字段，无论访问修饰符是什么。
```
try {
    // 找到包名
    Class<?> clazz = Class.forName("com.letv.leui.app.LeLoadingRemoteView");

    // 获取类构造器
    Constructor constructor = clazz.getConstructor(String.class, boolean.class);

    // 实例话一个类
    RemoteViews remoteViews = (RemoteViews) constructor.newInstance(context.getPackageName(), next);

    // 调用方法
    clazz.getMethod("toggle", boolean.class).invoke(remoteViews, start);
    return remoteViews;
} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | NoSuchMethodException | InvocationTargetException e) {
    e.printStackTrace();
}
```

## 类型擦除
List<String> list = new ArrayList<>()；如何把Activity对象放进list集合中去。
- List<String>强转为List
- 采用反射

```java
List<String> list = new ArrayList<>();
list.add("a");
list.add("b");
Activity activity = new Activity();

// 方法1，List<String>强转为List
((List) list).add(activity);
try {
   // 方法2，采用反射
   list.getClass().getMethod("add", Object.class).invoke(list, activity);

   for (int i = 0; i < list.size(); i++) {
       Object o = list.get(i);
       if (o!= null && o instanceof String) {
           LogUtils.e(TAG, "list " + i +":" + o);
       } else if (o instanceof Activity) {
           LogUtils.e(TAG, "list " + i +":" + o.toString());
       }
   }
} catch (IllegalAccessException e) {
   e.printStackTrace();
} catch (InvocationTargetException e) {
   e.printStackTrace();
} catch (NoSuchMethodException e) {
   e.printStackTrace();
}
```
参考：http://blog.csdn.net/lonelyroamer/article/details/7868820#t1
