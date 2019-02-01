package com.me.ui.sample.thirdparty.arouter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.android.component.library.router.RouterManager;
import com.me.ui.library.sample.FragmentBean;
import com.me.ui.library.sample.SampleFragment;
import com.me.ui.sample.thirdparty.arouter.service.TestService;
import com.me.ui.util.ToastUtils;

import java.util.List;

/**
 * @author kylingo on 18/10/10
 */
public class ARouterFragment extends SampleFragment<String> {

    @Autowired(name = "/service/test")
    TestService mService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void addItems(List<String> items) {
        items.add("路由");
        items.add("自定义协议");
        items.add("暴露服务");
        items.add("ARouter打包aar");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "路由":
                Bundle bundle = new Bundle();
                bundle.putBoolean("flag", true);
                bundle.putLong("id", 123L);
                bundle.putSerializable("obj", new FragmentBean(ARouterFragment.class));

                ARouter.getInstance().build("/com/test/activity").with(bundle).navigation(getActivity(),
                        new NavigationCallback() {
                            @Override
                            public void onFound(Postcard postcard) {
                                ToastUtils.showShort("onFound");
                            }

                            @Override
                            public void onLost(Postcard postcard) {
                                ToastUtils.showShort("onLost");
                            }

                            @Override
                            public void onArrival(Postcard postcard) {
                                ToastUtils.showShort("onArrival");
                            }

                            @Override
                            public void onInterrupt(Postcard postcard) {
                                ToastUtils.showShort("onInterrupt");
                            }
                        });
                break;

            case "自定义协议":
                Intent intent = new Intent();
                intent.setData(Uri.parse("arouter://kylingo.com/com/scheme/activity"));
                startActivity(intent);
                break;

            case "暴露服务":
                if (mService != null) {
                    mService.test("kylingo");
                }
                break;

            case "ARouter打包aar":
                RouterManager.navigationFeatureA();
                break;
        }
    }
}
