package com.me.ui.sample.algorithm.leetcode;

import com.me.ui.library.sample.SampleFragment;

import java.util.List;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    18/4/24 10:39
 */
public class LeetCode01Fragment extends SampleFragment<String> {

    @Override
    protected void addItems(List<String> items) {
        items.add("二维数组中查找");
        items.add("替换空格");
        items.add("从尾到头打印链表");
        // 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
        items.add("重建二叉树");
        items.add("二个栈实现队列");
        // 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
        items.add("旋转数组最小数字");
        // 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
        items.add("斐波那契数列");
        // 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
        items.add("跳台阶");
        // 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
        items.add("变态跳台阶");
        // 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
        items.add("矩形覆盖");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "二维数组中查找":
                LeetCode01.findTwoDimensionArray(1, null);
                break;

            case "替换空格":
                StringBuffer sb = new StringBuffer();
                LeetCode01.replaceSpace(sb);
                break;

            case "从尾到头打印链表":
                break;

            case "重建二叉树":
                break;

            case "二个栈实现队列":
                TwoStackQueue.test();
                break;

            case "旋转数组最小数字":
                break;

            case "斐波那契数列":
                break;

            case "跳台阶":
                break;

            case "变态跳台阶":
                break;

            case "矩形覆盖":
                break;
        }
    }
}
