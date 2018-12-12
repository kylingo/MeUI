package com.me.ui.sample.library.security;

import com.me.ui.library.sample.SampleFragment;
import com.me.ui.library.security.Rc4Utils;
import com.me.ui.util.LogUtils;

import java.util.List;

/**
 * 加密 https://blog.csdn.net/qq_26685493/article/details/51179378
 *
 * @author kylingo on 18/6/25
 */
public class EncryptFragment extends SampleFragment<String> {

    private static final String TAG = "EncryptFragment";
    private static final String CONTENT = "12221iofjdsonoanvaon";
    private static final String KEY = "abcddoj";

    @Override
    protected void addItems(List<String> items) {
        items.add("RC4加密");
        items.add("RC4解密");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "RC4加密": {
                String result = Rc4Utils.encode(CONTENT, KEY);
                LogUtils.i(TAG, "rc4 encode:" + toHexString(result));
                break;
            }

            case "RC4解密": {
                String encode = Rc4Utils.encode(CONTENT, KEY);
                LogUtils.i(TAG, "rc4 encode:" + toHexString(encode));
                String result = Rc4Utils.encode(encode, KEY);
                LogUtils.i(TAG, "rc4 decode:" + result);
                break;
            }
        }
    }

    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch & 0xFF);
            if (s4.length() == 1) {
                s4 = '0' + s4;
            }
            str = str + s4;
        }
        return str;// 0x表示十六进制
    }
}
