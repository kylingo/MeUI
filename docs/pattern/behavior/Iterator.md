# 迭代器模式
迭代器模式，又称游标模式，一般用于对容器的访问，比如Java中的List, Map，数组等，提供遍历算法，但是不暴露容器对象的内部实现。

## 使用场景
遍历容器对象

## UML图
- Set interface, add, remove, iterator
- Set concrete
- Iterator interface, next, hasNext
- Iterator concrete

## 实例
Set, List, Map, Cursor

## 实战
ArrayList 迭代器源码
```Java
private class ArrayListIterator implements Iterator<E> {
       /** Number of elements remaining in this iteration */
       private int remaining = size;

       /** Index of element that remove() would remove, or -1 if no such elt */
       private int removalIndex = -1;

       /** The expected modCount value */
       private int expectedModCount = modCount;

       public boolean hasNext() {
           return remaining != 0;
       }

       @SuppressWarnings("unchecked") public E next() {
           ArrayList<E> ourList = ArrayList.this;
           int rem = remaining;
           if (ourList.modCount != expectedModCount) {
               throw new ConcurrentModificationException();
           }
           if (rem == 0) {
               throw new NoSuchElementException();
           }
           remaining = rem - 1;
           return (E) ourList.array[removalIndex = ourList.size - rem];
       }

       public void remove() {
           Object[] a = array;
           int removalIdx = removalIndex;
           if (modCount != expectedModCount) {
               throw new ConcurrentModificationException();
           }
           if (removalIdx < 0) {
               throw new IllegalStateException();
           }
           System.arraycopy(a, removalIdx + 1, a, removalIdx, remaining);
           a[--size] = null;  // Prevent memory leak
           removalIndex = -1;
           expectedModCount = ++modCount;
       }
   }
```

```Java
// 测试代码
public static void execute() {
      List<String> list = new ArrayList<>();
      for (int i = 0; i < 10; i++) {
          list.add("" + i);
      }

      Iterator<String> it = list.iterator();
      while (it.hasNext()) {
          String s = it.next();
          LogUtils.d(IteratorTest.class, "s:" + s);
      }
  }
```
