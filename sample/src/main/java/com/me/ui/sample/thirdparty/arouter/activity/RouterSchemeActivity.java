package com.me.ui.sample.thirdparty.arouter.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.me.ui.library.sample.FragmentBean;
import com.me.ui.sample.base.BaseActivity;

/**
 * @author kylingo on 18/10/10
 */
@Route(path = "/com/scheme/activity")
public class RouterSchemeActivity extends BaseActivity {

    // Declare a field for each parameter and annotate it with @Autowired
    @Autowired
    public String name;

    @Autowired
    int age;

    @Autowired(name = "girl") // Map different parameters in the URL by name
            boolean boy;

    @Autowired
    FragmentBean obj;    // Support for parsing custom objects, using json pass in URL

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("ARouter scheme");
        ARouter.getInstance().inject(this);
    }
}
