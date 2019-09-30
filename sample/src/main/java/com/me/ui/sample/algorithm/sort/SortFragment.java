package com.me.ui.sample.algorithm.sort;

import android.util.Log;

import com.me.ui.library.sample.SampleFragment;

import java.util.Arrays;
import java.util.List;

/**
 * @author tangqi on 17-6-16.
 */
public class SortFragment extends SampleFragment<String> {

    public static final String TAG = SortFragment.class.getSimpleName();

    @Override
    protected void addItems(List<String> items) {
        items.add("冒泡排序");
        items.add("选择排序");
        items.add("插入排序");
        items.add("快速排序");
    }

    @Override
    protected void onClickItem(String item) {
        Log.d(TAG, item);
        int[] a = {6, 8, 3, 9, 7, 0, 5, 1, 4, 2};
        Log.d(TAG, "Before sorted:" + Arrays.toString(a));

        switch (item) {
            case "冒泡排序":
                bubbleSort(a);
                break;

            case "选择排序":
                selectSort(a);
                break;

            case "插入排序":
                insertSort(a);
                break;

            case "快速排序":
                quickSort(a, 0, a.length - 1);
                break;

            default:
                break;
        }
        Log.d(TAG, "After  sorted:" + Arrays.toString(a));
    }

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

    public static void selectSort(int[] a) {
        if (a == null || a.length == 0) {
            return;
        }

        int min;
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
}
