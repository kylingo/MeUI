package com.me.ui.app.common.entrance;

import android.content.Intent;

import com.me.ui.library.sample.SampleFragment;
import com.me.ui.app.zhihu.page.activity.ZhiHuMainActivity;

import java.util.List;

/**
 * @author kylingo
 * @since 2019/04/22 15:01
 */
public class MainFragment extends SampleFragment<String> {

    @Override
    protected void addItems(List<String> items) {
        items.add("知乎日报");
        items.add("玩Android");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "知乎日报": {
                Intent intent = new Intent(getActivity(), ZhiHuMainActivity.class);
                startActivity(intent);
                break;
            }

            case "玩Android": {
                Intent intent = new Intent(getActivity(), ZhiHuMainActivity.class);
                startActivity(intent);
                break;
            }
        }

    }
}
