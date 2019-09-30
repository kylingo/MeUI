package com.me.ui.sample.algorithm.leetcode;

import com.me.ui.library.sample.SampleFragment;

import java.util.List;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    18/4/24 10:39
 * LeetCode: https://leetcode.com/problemset/algorithms/
 * NowCoder: https://www.nowcoder.com/ta/coding-interviews
 */
public class LeetCodeFragment extends SampleFragment<String> {

    @Override
    protected void addItems(List<String> items) {
        items.add("替换空格");
        items.add("从尾到头打印链表");
        items.add("二个栈实现队列");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "替换空格":
                StringBuffer sb = new StringBuffer();
                Solution.replaceSpace(sb);
                break;
            case "从尾到头打印链表":
                break;
            case "二个栈实现队列":
                TwoStackQueue.test();
                break;
        }
    }
}
