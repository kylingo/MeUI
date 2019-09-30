# 并发

## 进程和线程的区别
- 进程，操作系统进行资源分配的基本单位，每个进程对应一个程序。
  - 进程的出现让操作系统的并发，成为了可能。
  - 并发从宏观上来说有多个任务在执行，事实上对于任一具体时刻，只有一个任务在占用CPU资源。
- 线程，操作系统进行任务调度的基本单位
  - 线程的出现，让进程并发成为可能。
  - 一个进程可以包括多个线程，而这些线程共享进程占有的资源和内存地址。

## 线程的优点
为什么采用多线程，而不是多进程？
- 系统分配进程的成本比分配线程的成本要高
- 系统在进程间切换的成本，比线程间切换的成本要高
- 由于共用内存，多线程之间数据的共享也更加方便

## 多线程问题
### 使用场景
有多个任务需要同时执行，或者后台执行。

### 同步
要跨线程维护正确的可见性，只要在几个线程之间共享非 final 变量，就必须使用 synchronized（或 volatile）以确保一个线程可以看见另一个线程做的更改。

为了在线程之间进行可靠的通信，也为了互斥访问，同步是必须的。这归因于java语言规范的内存模型，它规定了：一个线程所做的变化何时以及如何变成对其它线程可见。

同步和多线程关系：没多线程环境就不需要同步; 有多线程环境也不一定需要同步。

同步的几种方法
- ThreadLocal，解决多线程多统一变量访问冲突的问题。保证不同线程有不同的实例，相同的线程一定具有相同的实例。每个线程都可以改变自己的副本，不与其他的线程产生冲突。
  - 优势，提供了线程安全的共享对象。
  - 与同步的区别，同步机制是为了同步多个线程对相同资源的并发访问，是为了多个线程之间进行通信；而 ThreadLocal 是隔离多个线程的数据共享，从根本上就不在多个线程之间共享资源，这样当然不需要多个线程进行同步了。

- volatile，修饰的成员变量在每次被线程访问时，都强迫从共享内存中重读该成员变量。而且，当成员变量发生变化时，强迫线程将变化回写到共享内存。
  - 优势，这样在任何时刻，两个不同的线程总是看到某个成员变量的同一个。
  - 缘由，Java 语言规范中指出，为了获得最佳速度，允许线程保存共享成员变量的私有拷贝，而且只当线程进入或者离开同步代码块时才与共享成员变量的原始对比。这样当多个线程同时与某个对象交互时，就必须要注意到要让线程及时的得到共享成员变量的变化。而 volatile 关键字就是提示 VM ：对于这个成员变量不能保存它的私有拷贝，而应直接与共享成员变量交互。
  - 只能在有限的一些情形下使用 volatile 变量替代锁。要使 volatile 变量提供理想的线程安全，必须同时满足下面两个条件： 对变量的写操作不依赖于当前值；该变量没有包含在具有其他变量的不变式中。

- synchronized，修饰方法和代码块，不能修饰属性和构造方法。

- 同步锁，Lock，实现类ReentrantLock，提供种特性，互斥和可见性。

- wait和notify，是Java同步不机制重要的部分，结合synchronized关键字使用。
  - wait(),notify(),notifyAll()不属于Thread类,而是属于Object基础类,也就是说每个对像都有wait(),notify(),notifyAll()的功能。因为都个对像都有锁,锁是每个对像的基础,当然操作锁的方法也是最基础了。
  - synchronized和wait()、notify()等的关系:
    - 有synchronized的地方不一定有wait,notify
    - 有wait,notify的地方必有synchronized.这是因为wait和notify不是属于线程类，而是每一个对象都具有的方法，而且，这两个方法都和对象锁有关，有锁的地方，必有synchronized。

### 通信
- 同步
- wait和nofity
- 轮询
- 管道通信

### 死锁
死锁，多个线程同时被阻塞，它们中的一个或者全部都在等待某个资源被释放。由于线程被无限期地阻塞，因此程序不可能正常终止。

### 可重入内置锁
每个Java对象都可以用做一个实现同步的锁，这些锁被称为内置锁或监视器锁。线程在进入同步代码块之前会自动获取锁，并且在退出同步代码块时会自动释放锁。获得内置锁的唯一途径就是进入由这个锁保护的同步代码块或方法。

当某个线程请求一个由其他线程持有的锁时，发出请求的线程就会阻塞。然而，由于内置锁是可重入的，因此如果摸个线程试图获得一个已经由它自己持有的锁，那么这个请求就会成功。“重入”意味着获取锁的操作的粒度是“线程”，而不是调用。重入的一种实现方法是，为每个锁关联一个获取计数值和一个所有者线程。当计数值为0时，这个锁就被认为是没有被任何线程所持有，当线程请求一个未被持有的锁时，JVM将记下锁的持有者，并且将获取计数值置为1，如果同一个线程再次获取这个锁，计数值将递增，而当线程退出同步代码块时，计数器会相应地递减。当计数值为0时，这个锁将被释放。

重入进一步提升了加锁行为的封装性，因此简化了面向对象并发代码的开发。分析如下程序：
```Java
public class Father  
{  
    public synchronized void doSomething(){  
        ......  
    }  
}  

public class Child extends Father  
{  
    public synchronized void doSomething(){  
        ......  
        super.doSomething();  
    }  
}  
```
子类覆写了父类的同步方法，然后调用父类中的方法，此时如果没有可重入的锁，那么这段代码件产生死锁。

由于Father和Child中的doSomething方法都是synchronized方法，因此每个doSomething方法在执行前都会获取Child对象实例上的锁。如果内置锁不是可重入的，那么在调用super.doSomething时将无法获得该Child对象上的互斥锁，因为这个锁已经被持有，从而线程会永远阻塞下去，一直在等待一个永远也无法获取的锁。重入则避免了这种死锁情况的发生。

同一个线程在调用本类中其他synchronized方法/块或父类中的synchronized方法/块时，都不会阻碍该线程地执行，因为互斥锁时可重入的。


## 线程池
java.uitl.concurrent.ThreadPoolExecutor类是线程池中最核心的一个类，因此如果要透彻地了解Java中的线程池，必须先了解这个类。下面我们来看一下ThreadPoolExecutor类的具体实现源码。
```Java
public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,
           BlockingQueue<Runnable> workQueue);
```
ThreadPoolExecutor构造方法：
- corePoolSize：核心池的大小，创建后一直保留，除非设置了allowCoreThreadTimeOut。
- maximumPoolSize：线程池最大线程数
- keepAliveTime：表示线程没有任务执行时最多保持多久时间会终止
- unit：参数keepAliveTime的时间单位，有7种取值，在TimeUnit类中有7种静态属性。
- workQueue：一个阻塞队列，用来存储等待执行的任务，这个参数的选择也很重要，会对线程池的运行过程产生重大影响，一般来说，这里的阻塞队列有以下几种选择：
  - ArrayBlockingQueue;
  - LinkedBlockingQueue;
  - SynchronousQueue;
　　ArrayBlockingQueue和PriorityBlockingQueue使用较少，一般使用LinkedBlockingQueue和Synchronous。线程池的排队策略与BlockingQueue有关。

Java通过Executors提供四种线程池，分别为：
- newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
- newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
- newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
- newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)

## 线程中断
线程可以通过interrupt进行中断，通过isInterrupted可以查询是否中断。需要注意的是，线程的中断状态会由该方法清除，中断后第一次返回true，第二次再调用返回false。

- 线程让步，yield() 暂停当前正在执行的线程对象，并执行其他线程。
- 线程睡眠，sleep() 线程睡眠的过程中，如果是在synchronized线程同步内，是持有锁(监视器对象)的，也就是说，线程是关门睡觉的，别的线程进不来。
- 线程合并，join()  线程合并是优先执行调用该方法的线程，再执行当前线程。所谓合并，就是等待其它线程执行完，再执行当前线程，执行起来的效果就好像把其它线程合并到当前线程执行一样。
- 线程阻塞，suspend()和resume() 两个方法配套使用，suspend()使得线程进入阻塞状态，并且不会自动恢复，必须其对应的resume() 被调用，才能使得线程重新进入可执行状态。

## 守护线程
守护线程在没有用户线程可服务时自动离开，在Java中比较特殊的线程是被称为守护（Daemon）线程的低级别线程。这个线程具有最低的优先级，用于为系统中的其它对象和线程提供服务。守护线程在主线程结束时,自动退出,所以你不用去管这个线程的结束问题。

setDaemon(boolean on)方法可以方便的设置线程的Daemon模式，true为Daemon模式，false为User模式。setDaemon(boolean on)方法必须在线程启动之前调用，当线程正在运行时调用会产生异常。


## 参考
- http://www.cnblogs.com/dolphin0520/p/3910667.html
- http://blog.csdn.net/you_off3/article/details/7572704
- http://blog.csdn.net/ghsau/article/details/17560467
