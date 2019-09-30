# Java关键字
Java提供了volatile和synchronized两个关键字来保证线程之间操作的有序性

## volatile
volatile保证线程的的操作具有有序性，本身包含“禁止禁令重排序”的语义。
可见性，是指线程之间的可见性，一个线程修改的状态对另一个线程是可见的。

## synchronized
synchronized 是由“一个变量在同一个时刻只允许一条线程对其进行 lock 操作”这条规则获得的，此规则决定了持有同一个对象锁的两个同步块只能串行执行。
