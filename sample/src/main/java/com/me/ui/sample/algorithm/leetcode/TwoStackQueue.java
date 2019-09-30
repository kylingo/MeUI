package com.me.ui.sample.algorithm.leetcode;

import com.me.ui.util.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 二个栈实现队列
 * Author:  Kevin.Tang
 * Date:    18/5/2 14:07
 */
public class TwoStackQueue<T> {
    private static final String TAG = "TwoStackQueue";
    private Stack<T> stack1 = new Stack<>();
    private Stack<T> stack2 = new Stack<>();

    public void push(T t) {
        stack1.push(t);
    }

    public T pop() {
        if (stack2.empty()) {
            while (!stack1.empty()) {
                stack2.push(stack1.pop());
            }
        }

        return stack2.pop();
    }

    public boolean empty() {
        return stack1.empty() && stack2.empty();
    }

    public static void test() {
        TwoStackQueue<Integer> twoStackQueue = new TwoStackQueue<>();
        int[] input = new int[]{1, 2, 3, 4, 5};
        LogUtils.i(TAG, "input:" + Arrays.toString(input));

        List<Integer> output = new ArrayList<>();
        twoStackQueue.push(input[0]);
        twoStackQueue.push(input[1]);
        twoStackQueue.push(input[2]);
        output.add(twoStackQueue.pop());
        output.add(twoStackQueue.pop());
        twoStackQueue.push(input[3]);
        twoStackQueue.push(input[4]);
        output.add(twoStackQueue.pop());
        output.add(twoStackQueue.pop());
        output.add(twoStackQueue.pop());

        LogUtils.i(TAG, "output:" + output.toString());
    }
}
