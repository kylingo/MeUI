package com.me.ui.sample.algorithm.search;

import com.me.ui.library.sample.SampleFragment;
import com.me.ui.util.LogUtils;

import java.util.List;

/**
 * @author tangqi on 17-7-17.
 */
public class SearchFragment extends SampleFragment<String> {

    @Override
    protected void addItems(List<String> items) {
        items.add("折半查找");
        items.add("折半递归查找");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "折半查找":
                halfSearch();
                break;

            case "折半递归查找":
                halfSearch2();
                break;

        }
    }

    private void halfSearch() {
        int[] arr = {1, 2, 3, 5, 7, 8, 9, 10, 15, 20};

        int index = HalfSearch.search(arr, 4);
        LogUtils.i(SearchFragment.class, "search 4 index:" + index);

        index = HalfSearch.search(arr, 15);
        LogUtils.i(SearchFragment.class, "search 15 index:" + index);
    }

    private void halfSearch2() {
        int[] arr = {1, 2, 3, 5, 7, 8, 9, 10, 15, 20};

        int index = HalfSearch.search(arr, 2, 0, arr.length - 1);
        LogUtils.i(SearchFragment.class, "search 2 index:" + index);

        index = HalfSearch.search(arr, 11, 0, arr.length - 1);
        LogUtils.i(SearchFragment.class, "search 11 index:" + index);
    }
}
