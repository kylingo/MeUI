package com.me.ui.sample.widget.basic;

import android.view.View;

/**
 * @author kylingo
 * @since 2019/09/25 17:32
 */
public abstract class DebounceOnClickListener implements View.OnClickListener {

    private static boolean mEnabled = true;
    private static final int DELAY_TIME = 200;

    private static final Runnable ENABLE_RUNNABLE = new Runnable() {
        @Override
        public void run() {
            mEnabled = true;
        }
    };

    @Override
    public void onClick(View v) {
        if (mEnabled) {
            mEnabled = false;
            doClick(v);
            v.postDelayed(ENABLE_RUNNABLE, DELAY_TIME);
        }
    }

    public abstract void doClick(View v);
}
