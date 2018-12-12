package com.me.ui.sample.thirdparty.arouter.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.me.ui.util.ToastUtils;

/**
 * @author kylingo on 18/10/10
 */
@Route(path = "/service/test", name = "test service")
public class TestServiceImpl implements TestService {

    @Override
    public void test(String name) {
        ToastUtils.showShort("test:" + name);
    }

    @Override
    public void init(Context context) {

    }
}
