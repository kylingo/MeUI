package com.me.ui.sample.thirdparty.audio;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telecom.Call;
import android.telecom.CallAudioState;
import android.telecom.InCallService;

/**
 * @author kylingo
 * @since 2019/10/18 16:54
 */
@SuppressLint("Registered")
@RequiresApi(api = Build.VERSION_CODES.M)
public class PhoneCallService extends InCallService {

    @Override
    public void onCallAdded(Call call) {
        super.onCallAdded(call);

        // 打开扬声器
        setAudioRoute(CallAudioState.ROUTE_SPEAKER);

        // 关闭扬声器
        setAudioRoute(CallAudioState.ROUTE_EARPIECE);
    }

    @Override
    public void onCallRemoved(Call call) {
        super.onCallRemoved(call);
    }
}
