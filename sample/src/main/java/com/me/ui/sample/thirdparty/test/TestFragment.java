package com.me.ui.sample.thirdparty.test;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.me.ui.library.sample.SampleFragment;
import com.me.ui.sample.library.log.MLog;
import com.me.ui.sample.thirdparty.aspect.TryCatch;

import java.util.List;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    18/3/20 11:52
 */
public class TestFragment extends SampleFragment<String> {

    private static final String TAG = "TestFragment";

    private final static int FLAG_1 = 1;
    private final static int FLAG_2 = 2;
    private final static int FLAG_3 = 4;

    @Override
    protected void addItems(List<String> items) {
        items.add("Toast");
        items.add("输入法");
        items.add("Scheme");
        items.add("Profile");
        items.add("FlagMask");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "Toast":
                // 部分手机的Toast有问题，显示"应用名称:toast"
                Toast.makeText(getContext(), "这是一个Toast", Toast.LENGTH_SHORT).show();
                break;

            case "输入法": {
                Intent intent = new Intent(getActivity(), InputMethodActivity.class);
                startActivity(intent);
                break;
            }

            case "Scheme": {
                schemeTest();
                break;
            }

            case "Profile": {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("imgotv://noahprofile?uid=6c7b934382814507b45df4cb8c99988d"));
                startActivity(intent);
                break;
            }

            case "FlagMask": {
                flagMaskTest();
                break;
            }
        }

    }

    @TryCatch
    private void schemeTest() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 底层页
//        String scheme = "imgotv://minivideo?vid=1d10db2c9dc44756a59e00312ab9144d";

        // 微剧
//        String scheme = "imgotv://minivideo?vid=4c4681ab572d476c9a3df452a9eeb5cb&episodeType=1";

        // h5
//        String scheme = "mgqiezi://web?url=https://nm.mgtv.com/artist/dist/red-packet.html";

        // 妄想少女MOMO
//        String scheme = "imgotv://minivideo?vid=cbc1a6c76d4d41b8a5546e93acd7e502&episodeType=1";
//        String scheme = "mgqiezi://player?vid=cbc1a6c76d4d41b8a5546e93acd7e502&episodeType=1";

        // 大芒
        String scheme = "mgqiezi://player?vid=acf6559506dc4869b0f74dc16a0bbeab&mangoType=1";

        intent.setData(Uri.parse(scheme));
        startActivity(intent);
    }

    private void flagMaskTest() {
        for (int i = 0; i < 8; i++) {
            flagOperation(i, FLAG_1);
            flagOperation(i, FLAG_2);
            flagOperation(i, FLAG_3);
        }
    }

    private void flagOperation(int flag, int mask) {
        MLog.d(TAG, "flag:" + flag + ", mask:" + mask + ", hasFlag:" + hasFlag(flag, mask));
        MLog.d(TAG, "flag:" + flag + ", mask:" + mask + ", hasNotFlag:" + hasNotFlag(flag, mask));
        MLog.d(TAG, "flag:" + flag + ", mask:" + mask + ", addFlag:" + addFlag(flag, mask));
        MLog.d(TAG, "flag:" + flag + ", mask:" + mask + ", removeFlag:" + removeFlag(flag, mask));
    }

    private boolean hasFlag(int flag, int mask) {
        return (flag & mask) != 0;
    }

    private boolean hasNotFlag(int flag, int mask) {
        return (flag & mask) == 0;
    }

    private int addFlag(int flag, int mask) {
        return flag | mask;
    }

    private int removeFlag(int flag, int mask) {
        flag &= ~mask;
        return flag;
    }
}
