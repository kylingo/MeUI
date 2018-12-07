package com.me.ui.sample.library.basic;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseActivity;
import com.me.ui.sample.databinding.ActivityDataBindingBinding;
import com.me.ui.sample.library.entity.UserEntity;

/**
 * @author kylingo
 * @since 2018/12/07 16:10
 */
public class DataBindingActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDataBindingBinding dataBindingBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        UserEntity user = new UserEntity();
        user.setAge(34);
        user.setUsername("zhangsan");
        user.setNickname("张三");
        dataBindingBinding.setUser(user);
    }
}
