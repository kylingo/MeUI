package com.me.ui.sample.widget.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;
import com.me.ui.sample.base.TitleFragment;
import com.me.ui.widget.navigation.BottomNavigationView;
import com.me.ui.widget.navigation.NavigationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kylingo on 18/6/25
 */
public class BottomNavigationFragment extends BaseFragment implements
        BottomNavigationView.OnTabChangeListener {

    private TitleFragment mFirstFragment;
    private TitleFragment mSecondFragment;
    private TitleFragment mThirdFragment;
    private TitleFragment mFourthFragment;

    private String mFormerTag;
    private final static String FIRST_TAG = "FirstFragment";
    private final static String SECOND_TAG = "SecondFragment";
    private final static String THIRD_TAG = "ThirdFragment";
    private final static String FOURTH_TAG = "FourthFragment";

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_navigation_bottom;
    }

    @Override
    protected void initView(View view) {
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.view_bottom_navigation);
        bottomNavigationView.setOnTabChangeListener(this);
        bottomNavigationView.addNavigationItem(getNavigationItems());

        mFirstFragment = TitleFragment.newInstance("tab1");
        mSecondFragment = TitleFragment.newInstance("tab2");
        mThirdFragment = TitleFragment.newInstance("tab3");
        mFourthFragment = TitleFragment.newInstance("tab4");
        mFormerTag = FIRST_TAG;
        getFragmentManager().beginTransaction()
                .add(R.id.fl_bottom_navigation, mFirstFragment, FIRST_TAG).commit();
    }

    private List<NavigationItem> getNavigationItems() {
        List<NavigationItem> items = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            NavigationItem item = new NavigationItem();
            item.setContent("tab" + i);
            item.setResId(R.drawable.ic_launcher);

            items.add(item);
        }
        return items;
    }

    @Override
    public void onTabChange(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction mTransaction = fragmentManager.beginTransaction();
        mTransaction.hide(fragmentManager.findFragmentByTag(mFormerTag));

        Fragment selectFragment = null;
        switch (position) {
            case 0:
                mFormerTag = FIRST_TAG;
                selectFragment = mFirstFragment;
                break;

            case 1:
                mFormerTag = SECOND_TAG;
                selectFragment = mSecondFragment;
                break;

            case 2:
                mFormerTag = THIRD_TAG;
                selectFragment = mThirdFragment;
                break;

            case 3:
                mFormerTag = FOURTH_TAG;
                selectFragment = mFourthFragment;
                break;
        }

        if (selectFragment != null) {
            if (selectFragment.isAdded()) {
                mTransaction.show(selectFragment).commit();
            } else {
                mTransaction.add(R.id.fl_bottom_navigation, selectFragment, mFormerTag).commit();
            }
        }
    }
}
