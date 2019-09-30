package com.me.ui.sample.algorithm.interview;

import android.util.SparseArray;

import com.me.ui.library.sample.SampleFragment;
import com.me.ui.sample.algorithm.structure.tree.MinHeap;
import com.me.ui.util.LogUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * @author tangqi on 17-6-22.
 */
public class InterviewFragment extends SampleFragment<String> {

    @Override
    protected void addItems(List<String> items) {
        items.add("合并二个有序数组为一个有序数组");
        items.add("N个人随机交换礼物");
        items.add("细胞每小时分裂成2个，存活时间3小时，N小时后剩余多少个");
        items.add("细胞分裂问题采用动态规划");
        items.add("细胞分裂深度遍历");
        items.add("汉诺塔");
        items.add("翻转单链表");
        items.add("N个数的Top K问题");
        items.add("寻找最大子串");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "合并二个有序数组为一个有序数组":
                int[] a = {1, 3, 4, 7, 8};
                int[] b = {2, 5, 6, 9, 10};
                int[] c = mergeArray(a, b);
                LogUtils.i(InterviewFragment.class, "mergeArray:" + Arrays.toString(c));
                break;

            case "N个人随机交换礼物":
                int[] exchangeArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                SparseArray sa = exchange(exchangeArray);
                LogUtils.i(InterviewFragment.class, "sa:" + sa.toString());
                break;

            case "细胞每小时分裂成2个，存活时间3小时，N小时后剩余多少个":
                for (int i = 0; i < 30; i++) {
                    int splitCount = splitCell(i);
                    LogUtils.i(InterviewFragment.class, "splitCount " + i + ":" + splitCount);
                }
                break;

            case "细胞分裂问题采用动态规划":
                for (int i = 0; i < 30; i++) {
                    int splitCount = dynamicSplitCell(i);
                    LogUtils.i(InterviewFragment.class, "dynamicSplitCell " + i + ":" + splitCount);
                }
                break;

            case "细胞分裂深度遍历":
                for (int i = 0; i < 10; i++) {
                    int splitCount = deepPriorityCell(1, i);
                    LogUtils.i(InterviewFragment.class, "deepPriorityCell " + i + ":" + splitCount);
                }
                break;

            case "汉诺塔":
                moveHanNuoTa(10, 'a', 'b', 'c');
                break;

            case "翻转单链表":
                Node node = new Node();
                node.value = 0;
                Node lastNode = node;
                for (int i = 1; i < 10; i++) {
                    Node newNode = new Node();
                    newNode.value = i;
                    lastNode.next = newNode;

                    lastNode = newNode;
                }

                printNode(node);
                node = turnLinkedList(node);
                printNode(node);
                break;

            case "N个数的Top K问题":
                int[] arrayN = new int[10000];
                for (int i = 10000; i > 0; i--) {
                    arrayN[i - 1] = i;
                }

                int[] topK = topK(arrayN, 10);
                LogUtils.d(InterviewFragment.class, "topK:" + Arrays.toString(topK));
                break;
        }
    }

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

    public static void moveHanNuoTa(int n, char a, char b, char c) {
        if (n == 1) {
            LogUtils.i(InterviewFragment.class, "move " + n + " from " + a + " to " + c);
        } else {
            moveHanNuoTa(n - 1, a, c, b);
            LogUtils.i(InterviewFragment.class, "move " + n + " from " + a + " to " + c);
            moveHanNuoTa(n - 1, b, a, c);
        }
    }

    private static class Node {
        int value;
        Node next;
    }

    public static Node turnLinkedList(Node node) {
        Node prev = null;
        Node next;
        while (node != null) {
            next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }

        return prev;
    }

    private void printNode(Node node) {
        if (node == null) {
            return;
        }

        LogUtils.d(InterviewFragment.class, "printNode node:" + node.value);
        Node next = node.next;
        while (next != null) {
            LogUtils.d(InterviewFragment.class, "printNode node:" + next.value);
            next = next.next;
        }
    }

    /**
     * http://blog.csdn.net/xiao__gui/article/details/8687982
     */
    public static int[] topK(int[] a, int k) {
        if (a == null || k >= a.length) {
            return a;
        }

        // 先取K个元素放入一个数组topK中
        int[] topK = new int[k];
        for (int i = 0; i < k; i++) {
            topK[i] = a[i];
        }

        // 转换成最小堆
        MinHeap heap = new MinHeap(topK);

        // 从k开始，遍历data
        for (int i = k; i < a.length; i++) {
            int root = heap.getRoot();

            // 当数据小于堆中最小的数（根节点）时，替换堆中的根节点，再转换成堆
            if (a[i] < root) {
                heap.setRoot(a[i]);
            }
        }

        return topK;
    }


}
