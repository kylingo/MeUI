package com.me.ui.app.wanandroid.page.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseActivity;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.api.WanNetEngine;
import com.me.ui.app.wanandroid.data.WanHotKeyBean;
import com.me.ui.app.wanandroid.data.WanListModule;
import com.me.ui.util.ToastUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author kylingo
 * @since 2019/05/16 16:27
 */
public class WanSearchActivity extends BaseActivity {

    @BindView(R.id.tag_search_hot)
    TagFlowLayout mTagFlowLayout;

    @BindView(R.id.et_search)
    EditText mEtSearch;

    @BindView(R.id.tv_search_action)
    TextView mTvSearchAction;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wan_search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
    }

    protected void loadData() {
        WanNetEngine.getInstance().getMainHotKey()
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanListModule<WanHotKeyBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(WanListModule<WanHotKeyBean> wanHotKeyBeanWanListModule) {
                        if (wanHotKeyBeanWanListModule != null) {
                            updateHotKeywords(wanHotKeyBeanWanListModule.getData());
                        }
                    }
                });
    }

    private void updateHotKeywords(List<WanHotKeyBean> beans) {
        NavigationTagAdapter adapter = new NavigationTagAdapter(beans);
        mTagFlowLayout.setAdapter(adapter);
        mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                TagFlowLayout tagFlowLayout = (TagFlowLayout) parent;
                WanHotKeyBean bean = (WanHotKeyBean) tagFlowLayout.getAdapter()
                        .getItem(position);
                WanSearchResultActivity.launch(WanSearchActivity.this, bean.getName());
                return true;
            }
        });
    }

    private class NavigationTagAdapter extends TagAdapter<WanHotKeyBean> {

        public NavigationTagAdapter(List<WanHotKeyBean> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, WanHotKeyBean bean) {
            TextView tvTag = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wan_navigation_tag,
                    parent, false);
            tvTag.setText(bean.getName());
            return tvTag;
        }
    }

    @OnClick(R.id.iv_search_back)
    protected void onClickBack() {
        finish();
    }

    @OnClick(R.id.tv_search_action)
    protected void onClickSearch() {
        String inputKeywords = mEtSearch.getText().toString();
        if (TextUtils.isEmpty(inputKeywords)) {
            ToastUtils.showShort("输入为空");
        } else {
            WanSearchResultActivity.launch(this, inputKeywords);
        }
    }
}
