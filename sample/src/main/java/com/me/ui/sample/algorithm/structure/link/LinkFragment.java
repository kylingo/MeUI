package com.me.ui.sample.algorithm.structure.link;

import android.view.View;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;
import com.me.ui.util.LogUtils;

import java.util.LinkedList;

/**
 * @author tangqi on 17-7-14.
 */
public class LinkFragment extends BaseFragment {

    LinkedList<String> list;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_common;
    }

    @Override
    protected void initView(View view) {
        list = new LinkedList<>();
        add();

        printList();
        LogUtils.i(LinkFragment.class, "list.peek:" + list.peek());
        printList();
        LogUtils.i(LinkFragment.class, "list.poll:" + list.poll());
        printList();
        LogUtils.i(LinkFragment.class, "list.push A:");
        list.push("A");
        printList();
        LogUtils.i(LinkFragment.class, "list.pop:" + list.pop());
        printList();

    }

    private void printList() {
        LogUtils.i(LinkFragment.class, "list.toString:" + list.toString());
    }

    private void add() {
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
    }
}
