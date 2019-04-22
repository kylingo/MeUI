package com.me.ui.zhihu.page.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.me.ui.R;
import com.me.ui.zhihu.domain.config.Constants;
import com.me.ui.zhihu.domain.config.ExtraKey;
import com.me.ui.zhihu.model.entity.Daily;
import com.me.ui.zhihu.model.entity.DailyResult;
import com.me.ui.zhihu.page.adapter.DailyListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * DailyFragment
 */
public class DailyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Unbinder mUnbinder;
    private View rootView;
    private List<Daily> mNewsList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private DailyListAdapter mAdapter;
    private int date;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static DailyFragment newInstance() {
        DailyFragment fragment = new DailyFragment();
        return fragment;
    }

    public DailyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = getArguments().getInt(ExtraKey.DATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(R.layout.fragment_daily, container, false);
            mUnbinder = ButterKnife.bind(this, rootView);
            initView();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(
//                getActivity()
//        ));

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary);
        mAdapter = new DailyListAdapter(getActivity(), mNewsList);
        mRecyclerView.setAdapter(mAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.ZHIHU_DAILY_NEWS + date,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
//                        Log.d("Debug", "response:" + response);
                        DailyResult dailyResult = gson.fromJson(response, DailyResult.class);
                        mNewsList.clear();
                        mNewsList.addAll(dailyResult.stories);
                        mAdapter.notifyDataSetChanged();

                        finishRefresh();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                        finishRefresh();
                    }

                });
        requestQueue.add(stringRequest);

        startRefresh();
    }

    /**
     * 开始刷新-更新UI
     */
    private void startRefresh() {
        // TODO
    }

    /**
     * 结束刷新-更新UI
     */
    private void finishRefresh() {
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

}
