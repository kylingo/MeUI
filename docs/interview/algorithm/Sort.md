## 排序
简单排序
- [冒泡排序](#冒泡排序)
- [选择排序](#选择排序)
- [插入排序](#插入排序)

高效排序
- [快速排序](#快速排序)
- [堆排序](#堆排序)
- [希尔排序](#堆排序)

线性排序
- [归并排序](#归并排序)
- [计数排序](#计数排序)
- [桶排序]((#桶排序))
- [基数排序](#基数排序)

## 冒泡排序
顾名思义，像水泡向上升，每次先选出一个最大或者最小的，然后依次冒泡，时间复杂度（n^2）。
```Java
public static void bubbleSort(int[] a) {
    if (a == null || a.length == 0) {
        return;
    }

    for (int i = 0; i < a.length; i++) {
        for (int j = a.length - 1; j > i; j--) {
            if (a[j] < a[j - 1]) {
                int temp = a[j];
                a[j] = a[j - 1];
                a[j - 1] = temp;
            }
        }
    }
}
```

## 选择排序
冒泡法的优化，但是只有确认了最大或者最小数在进行交换，大大减少了交换次数。
```Java
public static void selectSort(int[] a) {
      if (a == null || a.length == 0) {
          return;
      }

      int min = 0;
      for (int i = 0; i < a.length - 1; i++) {
          min = i;
          for (int j = i + 1; j < a.length; j++) {
              if (a[j] < a[min]) {
                  min = j;
              }
          }

          if (min != i) {
              int temp = a[i];
              a[i] = a[min];
              a[min] = temp;
          }
      }
  }
```

## 插入排序
和扑克插牌的原理一样，以第一张牌为准，依次插入，小的插前面。
```Java
public static void insertSort(int[] a) {
    if (a == null || a.length == 0) {
        return;
    }

    for (int i = 1; i < a.length; i++) {
        if (a[i] < a[i - 1]) {
            int j;
            int target = a[i];
            for (j = i; j > 0 && target < a[j - 1]; j--) {
                a[j] = a[j - 1];
            }
            a[j] = target;
        }

    }
}
```

## 快速排序
通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都小，然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
```Java
public static void quickSort(int[] a, int start, int end) {
     if (a == null || a.length == 0) {
         return;
     }

     if (end <= start) {
         return;
     }

     // Get middle
     int middle = start;
     for (int i = start + 1; i <= end; i++) {
         if (a[i] < a[start]) {
             middle++;
             int temp = a[middle];
             a[middle] = a[i];
             a[i] = temp;
         }
     }
     int temp = a[middle];
     a[middle] = a[start];
     a[start] = temp;

     quickSort(a, start, middle - 1);
     quickSort(a, middle + 1, end);
 }
```

## 堆排序
选择排序的优化，一种树形选择排序。
```Java
// TODO
```

## 希尔排序
插入排序的优化
```Java
// TODO
```

## 归并排序
归并（Merge）排序法是将两个（或两个以上）有序表合并成一个新的有序表，即把待排序序列分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并为整体有序序列。
```Java
// TODO
```

## 计数排序
当输入的元素是 n 个 0 到 k 之间的整数时，它的运行时间是 Θ(n + k)。计数排序不是比较排序，排序的速度快于任何比较排序算法。
```Java
// TODO
```

## 桶排序
假设有一组长度为N的待排关键字序列K[1....n]。首先将这个序列划分成M个的子区间(桶) 。然后基于某种映射函数 ，将待排序列的关键字k映射到第i个桶中(即桶数组B的下标 i) ，那么该关键字k就作为B[i]中的元素(每个桶B[i]都是一组大小为N/M的序列)。接着对每个桶B[i]中的所有元素进行比较排序(可以使用快排)。然后依次枚举输出B[0]....B[M]中的全部内容即是一个有序序列。
```Java
// TODO
```

## 基数排序
基数排序的思想就是将待排数据中的每组关键字依次进行桶分配。
```Java
// TODO
```

## 排序复杂度和空间对比

## 附录
- 树
- 图
