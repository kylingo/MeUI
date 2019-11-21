package com.me.ui.sample.thirdparty.test;

import android.content.Intent;
import android.widget.Toast;

import com.me.ui.library.sample.SampleFragment;
import com.me.ui.sample.library.log.MLog;
import com.me.ui.sample.thirdparty.aspect.TryCatch;

import java.util.List;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    18/3/20 11:52
 */
public class TestFragment extends SampleFragment<String> {

    private static final String TAG = "TestFragment";

    private final static int FLAG_1 = 1;
    private final static int FLAG_2 = 2;
    private final static int FLAG_3 = 4;

    @Override
    protected void addItems(List<String> items) {
        items.add("Toast");
        items.add("输入法");
        items.add("FlagMask");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "Toast":
                // 部分手机的Toast有问题，显示"应用名称:toast"
                Toast.makeText(getContext(), "这是一个Toast", Toast.LENGTH_SHORT).show();
                break;

            case "输入法": {
                Intent intent = new Intent(getActivity(), InputMethodActivity.class);
                startActivity(intent);
                break;
            }

            case "FlagMask": {
                flagMaskTest();
                break;
            }
        }

    }

    @TryCatch
    private void flagMaskTest() {
        for (int i = 0; i < 8; i++) {
            flagOperation(i, FLAG_1);
            flagOperation(i, FLAG_2);
            flagOperation(i, FLAG_3);
        }
    }

    private void flagOperation(int flag, int mask) {
        MLog.d(TAG, "flag:" + flag + ", mask:" + mask + ", hasFlag:" + hasFlag(flag, mask));
        MLog.d(TAG, "flag:" + flag + ", mask:" + mask + ", hasNotFlag:" + hasNotFlag(flag, mask));
        MLog.d(TAG, "flag:" + flag + ", mask:" + mask + ", addFlag:" + addFlag(flag, mask));
        MLog.d(TAG, "flag:" + flag + ", mask:" + mask + ", removeFlag:" + removeFlag(flag, mask));
    }

    private boolean hasFlag(int flag, int mask) {
        return (flag & mask) != 0;
    }

    private boolean hasNotFlag(int flag, int mask) {
        return (flag & mask) == 0;
    }

    private int addFlag(int flag, int mask) {
        return flag | mask;
    }

    private int removeFlag(int flag, int mask) {
        flag &= ~mask;
        return flag;
    }
}
