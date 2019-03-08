package com.me.ui.sample.thirdparty.arouter.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.me.ui.library.sample.FragmentBean;
import com.me.ui.sample.base.BaseActivity;
import com.me.ui.util.LogUtils;

/**
 * @author kylingo on 18/10/10
 */
@Route(path = "/com/test/activity")
public class RouterTestActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("ARouter test");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            boolean flag = bundle.getBoolean("flag");
            long id = bundle.getLong("id");
            FragmentBean obj = (FragmentBean) bundle.getSerializable("obj");

            LogUtils.i(null, "flag:" + flag + " id:" + id + " obj:" + obj);
        }
    }
}
