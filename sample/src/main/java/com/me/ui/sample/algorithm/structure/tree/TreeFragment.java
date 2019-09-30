package com.me.ui.sample.algorithm.structure.tree;

import android.view.View;

import com.me.ui.library.sample.SampleFragment;

import java.util.List;

/**
 * @author tangqi on 17-7-14.
 *         http://blog.163.com/fly_sky_java/blog/static/14042223420102834937731/
 */
public class TreeFragment extends SampleFragment<String> {

    private Tree mTree;

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTree = new Tree();
        mTree.createTree();
    }

    @Override
    protected void addItems(List<String> items) {
        items.add("递归前序遍历");
        items.add("递归中序遍历");
        items.add("递归后序遍历");
        items.add("非递归前序遍历");
        items.add("非递归前序遍历2");
        items.add("非递归中序遍历");
        items.add("非递归后序遍历");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "递归前序遍历":
                mTree.preOrder(mTree.getRoot());
                break;

            case "递归中序遍历":
                mTree.middleOrder(mTree.getRoot());
                break;

            case "递归后序遍历":
                mTree.lastOrder(mTree.getRoot());
                break;

            case "非递归前序遍历":
                mTree.preOrder2(mTree.getRoot());
                break;

            case "非递归前序遍历2":
                mTree.preOrder3(mTree.getRoot());
                break;

            case "非递归中序遍历":
                mTree.middleOrder2(mTree.getRoot());
                break;

            case "非递归后序遍历":
                mTree.lastOrder2(mTree.getRoot());
                break;
        }
    }
}
