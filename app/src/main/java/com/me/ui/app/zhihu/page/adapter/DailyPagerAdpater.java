package com.me.ui.app.zhihu.page.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.me.ui.app.zhihu.domain.config.Constants;
import com.me.ui.app.zhihu.domain.config.ExtraKey;
import com.me.ui.app.zhihu.page.fragment.DailyFragment;

import java.util.Calendar;

/**
 * Created by Administrator on 2015/10/13 0013.
 */
public class DailyPagerAdpater extends FragmentStatePagerAdapter {

    public DailyPagerAdpater(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1 - position);
        int date = Integer.parseInt(Constants.simpleDateFormat.format(calendar.getTime()));
        Bundle bundle = new Bundle();
        bundle.putInt(ExtraKey.DATE, date);

        DailyFragment dailyFragment = DailyFragment.newInstance();
        dailyFragment.setArguments(bundle);

        return dailyFragment;
    }

    @Override
    public int getCount() {
        return Constants.NUM_DAILY;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -position);
        
        return Constants.mmddDateFormat.format(calendar.getTime());
    }
}
