## 二分法查找
```java
public static int search(int[] arr, int key) {
    if (arr == null) {
        return -1;
    }

    int low = 0;
    int high = arr.length - 1;
    while (low <= high) {
        int middle = low + (high - low) / 2;
        if (key == arr[middle]) {
            return middle;
        } else if (key < arr[middle]) {
            high = middle - 1;
        } else {
            low = middle + 1;
        }
    }

    return -1;
}
```

## 二分法查找（递归）
```java
public static int search(int[] arr, int key, int start, int end) {
    int middle = start + (end - start) / 2;
    if (arr == null || key > arr[end] || key < arr[start] || start > end) {
        return -1;
    }

    if (key < arr[middle]) {
        return search(arr, key, start, middle - 1);
    } else if (key > arr[middle]) {
        return search(arr, key, middle + 1, end);
    } else {
        return middle;
    }
}
```
