## 算法面试
- [合并二个有序数组为一个有序数组](#合并二个有序数组为一个有序数组)
- [N个人随机交换礼物](#随机交换礼物)
- [细胞分裂问题](#细胞分裂问题)
- [汉诺塔](#汉诺塔)
- [翻转单链表](#翻转单链表)
- [最小堆](#最小堆)
- [TopK问题](./TopK.md)
- 翻转二叉树
- [最常用的10大算法](http://www.csdn.net/article/2014-04-10/2819237-Top-10-Algorithms-for-Coding-Interview)


## 合并二个有序数组为一个有序数组
注意是二个有序序列，利用这个特性，每次都获取最小的进行比较，时间复杂度为O(N)
```Java
public static int[] mergeArray(int a[], int b[]) {
    if (a == null || b == null) {
        if (a == null) {
            return b;
        } else {
            return a;
        }
    }

    int c[] = new int[a.length + b.length];
    int indexA = 0;
    int indexB = 0;
    for (int i = 0; i < c.length; i++) {
        if (indexA < a.length && indexB < b.length) {
            if (a[indexA] < b[indexB]) {
                c[i] = a[indexA];
                indexA++;
            } else {
                c[i] = b[indexB];
                indexB++;
            }
        } else if (indexA < a.length) {
            c[i] = a[indexA];
            indexA++;
        } else if (indexB < b.length) {
            c[i] = b[indexB];
            indexB++;
        }
    }

    return c;
}
```

## 随机交换礼物
```java
@SuppressWarnings("unchecked")
   public static SparseArray exchange(int[] a) {
       if (a == null || a.length == 0) {
           return null;
       }

       Random random = new Random();
       int[] b = new int[a.length];
       SparseArray sparseArray = new SparseArray<>(a.length);
       for (int i = 0; i < b.length; i++) {
           int index = getRandom(i, a.length, random, a);
           b[i] = a[index];
           a[index] = a[i];

           sparseArray.put(i, b[i]);
       }

       return sparseArray;
   }

   private static int getRandom(int i, int length, Random random, int[] a) {
       int index = random.nextInt(length);
       if (a[index] == a[i]) {
           return getRandom(i, length, random, a);
       }
       return index;
   }
```

## 细胞分裂问题
问题：细胞每小时分裂成2个，存活时间3小时，N小时后剩余多少个？

方法1【递归】
```java
public static int splitCell(int n) {
        if (n == 0) {
            return 1;
        } else if (n == 1) {
            return 2;
        }

        return splitCell(n - 1) * 2 - died(n);
    }

/**
 * 重点在于计算每次的死亡个数
 */
private static int died(int n) {
    if (n <= 2) {
        return 0;
    } else if (n == 3) {
        return 1;
    }

    return died(n - 1) + died(n - 2) + died(n - 3);
}
```

方法2【动态规划】
```java
/**
 * 通过动态规划，把一个复杂的问题简单化
 */
public static int dynamicSplitCell(int n) {
    int[] result = new int[n + 1];
    result[0] = 1;

    int diedCount1 = 0;
    int diedCount2 = 0;
    int diedCount3 = 1;
    int diedCount;
    for (int i = 1; i <= n; i++) {
        if (i == 1) {
            diedCount = diedCount1;
        } else if (i == 2) {
            diedCount = diedCount2;
        } else if (i == 3) {
            diedCount = diedCount3;
        } else {
            diedCount = diedCount1 + diedCount2 + diedCount3;

            diedCount1 = diedCount2;
            diedCount2 = diedCount3;
            diedCount3 = diedCount;
        }
        result[i] = result[i - 1] * 2 - diedCount;
    }

    return result[n];
}
```

方法3【深度优先遍历】
```java
public static int deepPriorityCell(int age, int level) {
    if (age <= 0) {
        return 0;
    } else if (age >= 4) {
        return 0;
    }

    if (level <= 0) {
        return 0;
    } else if (level == 1) {
        return 1;
    }

    return deepPriorityCell(1, level - 1) + deepPriorityCell(++age, level - 1);
}
```

##  汉诺塔
```java
public static void moveHanNuoTa(int n, char a, char b, char c) {
    if (n == 1) {
        LogUtils.i(InterviewFragment.class, "move " + n + " from " + a + " to " + c);
    } else {
        moveHanNuoTa(n - 1, a, c, b);
        LogUtils.i(InterviewFragment.class, "move " + n + " from " + a + " to " + c);
        moveHanNuoTa(n - 1, b, a, c);
    }
}
```

## 翻转单链表
```java
public static Node turnLinkedList(Node node) {
     if (node == null) {
         return null;
     }

     Node next = node.next;
     node.next = null;
     while (next != null) {
         Node current = next;
         next = current.next;

         current.next = node;
         node = current;
     }

     return node;
 }

private static class Node {
    int value;
    Node next;
}
```
