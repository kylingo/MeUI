package com.me.ui.sample.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.me.ui.sample.library.util.ThemeUtils;

/**
 * @author kylingo on 18/6/22
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        ThemeUtils.setTheme(this);
        super.onCreate(savedInstanceState, persistentState);
    }
}
