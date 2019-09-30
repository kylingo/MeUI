# Java集合
Java集合位于java.util包下，大致可分为Set、List和Map三种体系，Java 5之后，增加了Queue体系集合，代表一种队列集合实现。
- Set代表无序、不可重复的集合；
- List代表有序、重复的集合；
- Map则代表具有映射关系的集合。

Java集合框架还包括迭代器（Iterator, Enumeration）、工具类（Arrays, Collections）。

## Collection
Collection是对象集合， Collection 子接口List，Set，Queue，遍历可采用iterator和foreach
![](./image/java_collection.png)

Collection接口的操作
- add(Object o)：增加元素
- addAll(Collection c)：...
- clear()：...
- contains(Object o)：是否包含指定元素
- containsAll(Collection c)：是否包含集合c中的所有元素
- iterator()：返回Iterator对象，用于遍历集合中的元素
- remove(Object o)：移除元素
- removeAll(Collection c)：相当于减集合c
- retainAll(Collection c)：相当于求与c的交集
- size()：返回元素个数
- toArray()：把集合转换为一个数组


## List
List子接口是有序集合，所以与Set相比，增加了与索引位置相关的操作，元素可以重复。
- add(int index, Object o)：在指定位置插入元素
- addAll(int index, Collection c)：...
- get(int index)：取得指定位置元素
- indexOf(Obejct o)：返回对象o在集合中第一次出现的位置
- lastIndexOf(Object o)：...
- remove(int index)：删除并返回指定位置的元素
- set(int index, Object o)：替换指定位置元素
- subList(int fromIndex, int endIndex)：返回子集合

ArrayList，Vector，LinkedList是List的实现类
- ArrayList和Vector都是基于数组实现的List。
  - ArrayList是线程不安全
  - Vector线程安全，不推荐使用，可以用其他的方案实现线程安全。
  - Vector提供一个子类Stack，不推荐使用，可以通过ArrayDeque和LinkedList实现栈的功能。建议将Vector和Stack当成历史遗留产物。
- LinkedList，线程不安全，底层是由链表实现，也实现了Queue接口，作为队列使用。

## Set
Set只能通过游标来取值，并且值是不能重复的，判断原则是equals方法。
- HashSet，不能保证顺序，加入元素需注意hashCode()方法实现； 不同步，多线程需手工同步； 集合元素可以为null。
  - LinkedHashSet，按增加顺序返回元素； 性能略低于HashSet，因为要维护元素的插入顺序，链表结构利于迭代访问。
- TreeSet，是SortedSet接口的实现类，可以定义Comparator进行排序，默认升序排列。
- EnumSet，专为枚举类设计的集合类，EnumSet中的所有元素都必须是指定枚举类型的枚举值。

## Queue
Queue用于模拟队列这种数据结构，实现“FIFO”等数据结构。通常，队列不允许随机访问队列中的元素。提供了几个基本方法，offer、poll、peek等。
- boolean add(E e) : 将元素加入到队尾，不建议使用
- boolean offer(E e): 将指定的元素插入此队列（如果立即可行且不会违反容量限制），当使用有容量限制的队列时，此方法通常要优于 add(E)，后者可能无法插入元素，而只是抛出一个异常。推荐使用此方法取代add
- E remove(): 获取头部元素并且删除元素，不建议使用
- E poll(): 获取头部元素并且删除元素，队列为空返回null；推荐使用此方法取代remove
- E element(): 获取但是不移除此队列的头
- E peek(): 获取队列头部元素却不删除元素，队列为空返回null

已知实现类有LinkedList、PriorityQueue等。
- LinkedList，既实现Queue和Deque，可以做列表，队列，栈使用。随机访问集合中的元素时性能较差，但插入与删除操作性能非常出色。
- PriorityQueue 保存队列元素的顺序并不是按照加入队列的顺序，而是按队列元素的大小重新排序。当调用peek()或者是poll()方法时，返回的是队列中最小的元素。当然你可以与TreeSet一样，可以自定义排序。
- Deque，一个双端队列，可以当作一个双端队列使用，也可以当作“栈”来使用，因为它包含出栈pop()与入栈push()方法。
- ArrayDeque，是Deque的实现类，数组方式实现。方法有：
  - addFirst(Object o)：元素增加至队列开头
  - addLast(Object o)：元素增加至队列末尾
  - poolFirst()：获取并删除队列第一个元素，队列为空返回null
  - poolLast()：获取并删除队列最后一个元素，队列为空返回null
  - pop()：“栈”方法，出栈，相当于removeFirst()
  - push(Object o)：“栈”方法，入栈，相当于addFirst()
  - removeFirst()：获取并删除队列第一个元素
  - removeLast()：获取并删除队列最后一个元素

## Map
Map是键值对集合
- HashTable 和 HashMap 是 Map 的实现类  
- HashTable 是线程安全的，不能存储 null 值  
- HashMap 不是线程安全的，可以存储 null 值  
- TreeMap，继承SortedMap，有序。

HashMap实现原理
- 数组：连续的，占用内存严重，故空间复杂的很大。但数组的二分查找时间复杂度小，为O(1)；数组的特点是：寻址容易，插入和删除困难；  
- 链表：链表存储区间离散，占用内存比较宽松，故空间复杂度很小，但时间复杂度很大，达O（N）。链表的特点是：寻址困难，插入和删除容易。
- 哈希表：那么我们能不能综合两者的特性，做出一种寻址容易，插入删除也容易的数据结构？答案是肯定的，这就是我们要提起的哈希表。哈希表（(Hash table）既满足了数据的查找方便，同时不占用太多的内容空间，使用也十分方便。最常用的一种方法—— 拉链法，我们可以理解为“链表的数组”。

**如何取值**
首先HashMap里面实现一个静态内部类Entry，其重要的属性有 key , value, next，从属性key,value我们就能很明显的看出来Entry就是HashMap键值对实现的一个基础bean，我们上面说到HashMap的基础就是一个线性数组，这个数组就是Entry[]，Map里面的内容都保存在Entry[]里面。数据的index，是用key的hashcode取余数组的长度。

**自定义key的注意事项**
注意HashCode值，不要重复，==和equals不能相同

**hashcode和equals**
如果类没有重写equals，就是比较实例的内存地址是否相同
hashcode也可以重写，使用hash算法

## 各种集合选择策略
- 数组：是以一段连续内存保存数据的；随机访问是最快的，但不支持插入、删除、迭代等操作。
- ArrayList与ArrayDeque：以数组实现；随机访问速度还行，插入、删除、迭代操作速度一般；线程不安全。
- Vector：以数组实现；随机访问速度一般，插入、删除、迭代速度不太好；线程安全的。
- LinkedList：以链表实现；随机访问速度不太好，插入、删除、迭代速度非常快。

## 集合扩容
- 以散列表实现的（例如HashSet, HashMap, HashTable等），扩容方式为当链表数组的非空元素除以数组大小冲过加载因子时，链表长度变大(乘2 + 1)，然后重新散列。
- 以数组实现的（例如ArrayList），当数组满了，数组长度变大（乘3/2 + 1），然后将原来的数组复制到新数组中。
- 以链表实现的(例如TreeSet，TreeMap), 动态增加元素，每次增加1。

## 参考
- http://www.cnblogs.com/yumo/p/4908718.html
- http://www.cnblogs.com/nayitian/p/3266090.html
