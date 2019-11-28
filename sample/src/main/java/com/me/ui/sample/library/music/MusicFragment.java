package com.me.ui.sample.library.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.me.ui.library.sample.SampleFragment;

import java.util.List;

/**
 * @author tangqi
 * @since 2019/11/29 00:48
 */
public class MusicFragment extends SampleFragment<String> {

    @Override
    protected void addItems(List<String> items) {
        items.add("start");
        items.add("pause");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "start":
                PlaybackUtils.startPlay(getActivity());
                break;

            case "pause":
                PlaybackUtils.pause(getActivity());
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        PlaybackUtils.bindToService(getActivity(), mServiceConnection);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        PlaybackUtils.unbindFromService(getActivity(), mServiceConnection);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
