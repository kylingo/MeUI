package com.me.ui.sample.library.log;

import com.me.ui.library.sample.SampleFragment;

import java.util.List;

/**
 * @author kylingo on 18/10/11
 */
public class LogFragment extends SampleFragment<String> {
    @Override
    protected void addItems(List<String> items) {
        items.add("输出日志");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "输出日志":
                MLog.i("LogFragment", "日志：" + System.currentTimeMillis());
                break;
        }
    }
}
