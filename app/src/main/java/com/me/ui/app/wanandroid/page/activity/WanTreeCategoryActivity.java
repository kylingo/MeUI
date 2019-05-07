package com.me.ui.app.wanandroid.page.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.me.ui.app.R;
import com.me.ui.app.common.base.BaseActivity;
import com.me.ui.app.common.rx.RxHelper;
import com.me.ui.app.common.rx.RxSubscriber;
import com.me.ui.app.wanandroid.api.WanNetEngine;
import com.me.ui.app.wanandroid.data.WanArticleBean;
import com.me.ui.app.wanandroid.data.WanModule;
import com.me.ui.app.wanandroid.data.WanTreeBean;
import com.me.ui.app.wanandroid.page.adapter.WanMainAdapter;
import com.me.ui.app.wanandroid.page.view.WanTitleView;

import java.util.List;

import butterknife.BindView;

/**
 * @author kylingo
 * @since 2019/05/07 14:29
 */
public class WanTreeCategoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TREE_BEAN = "tree_bean";
    private RecyclerView.LayoutManager mLayoutManager;
    private WanMainAdapter mAdapter;
    private WanTreeBean mWanTreeBean;

    @BindView(R.id.view_wan_main_title)
    WanTitleView mWanTitleView;

    @BindView(R.id.srl_wan_main)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_wan_main)
    RecyclerView mRecyclerView;

    public static void launch(Context context, WanTreeBean wanTreeBean) {
        Intent intent = new Intent(context, WanTreeCategoryActivity.class);
        intent.putExtra(TREE_BEAN, wanTreeBean);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_wan_tree_category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();
        initView();
        initData();
    }

    protected void parseIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mWanTreeBean = (WanTreeBean) intent.getSerializableExtra(TREE_BEAN);
        }
    }

    protected void initView() {
        if (mWanTreeBean != null) {
            mWanTitleView.setTitle(mWanTreeBean.getName());
        }

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary);
        mAdapter = new WanMainAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void initData() {
        loadData();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void loadData() {
        int cid = 60;
        if (mWanTreeBean != null) {
            List<WanTreeBean> wanTreeBeans = mWanTreeBean.getChildren();
            if (wanTreeBeans != null && wanTreeBeans.size() > 0) {
                WanTreeBean curWanTreeBean = wanTreeBeans.get(0);
                cid = curWanTreeBean.getId();
            }
        }
        WanNetEngine.getInstance().getTreeCategory(0, cid)
                .compose(RxHelper.getErrAndIOSchedulerTransformer())
                .subscribe(new RxSubscriber<WanModule<WanArticleBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            e.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(WanModule<WanArticleBean> wanModule) {
                        if (wanModule != null) {
                            WanArticleBean wanArticleBean = wanModule.getData();
                            if (wanArticleBean != null) {
                                List<WanArticleBean.DatasBean> datasBeans = wanArticleBean.getDatas();
                                if (datasBeans != null) {
                                    mAdapter.setData(datasBeans);
                                }
                            }
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
