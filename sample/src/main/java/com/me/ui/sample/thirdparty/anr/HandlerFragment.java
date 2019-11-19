package com.me.ui.sample.thirdparty.anr;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.me.ui.sample.base.BaseListFragment;


/**
 * @author kylingo
 * @since 2019/11/19 14:52
 */
public class HandlerFragment extends BaseListFragment {

    Handler mHandler;

    @Override
    protected void initView(View view) {
        super.initView(view);

        mHandler = new Handler();
        sendDelayMessage();
    }

    private void sendDelayMessage() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendSyncMessage();
            }
        }, 1000);
    }

    private void sendSyncMessage() {
        for (int i = 0; i < 10000; i++) {
            final int count = i;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        Log.i("tq", count + " sendSyncMessage : " + i);
                    }
//                    Log.i("tq", "sendSyncMessage : " + count);
                }
            });
        }
    }
}
