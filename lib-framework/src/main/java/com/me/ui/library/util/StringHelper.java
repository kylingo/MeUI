package com.me.ui.library.util;

import android.text.TextUtils;

/**
 * @author tangqi
 * @since 2020/03/23 22:06
 */
public class StringHelper {

    /**
     * 裁剪字符串
     * @param str 输入字符串
     * @param maxLen 最大字符数，例如20，代表10个汉字或20个字符
     * @return 裁剪后的字符串
     */
    public static String cropText(String str, int maxLen) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int count = 0;
        int endIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            char item = str.charAt(i);
            if (item < 128) {
                // 字符或字母，+1
                count = count + 1;
            } else {
                // 汉字或其他语言，+2
                count = count + 2;
            }

            if (maxLen == count) {
                endIndex = i;
            } else if (item >= 128 && maxLen + 1 == count) {
                // 最后一个字符超出时，减1
                endIndex = i - 1;
            }
        }

        if (count <= maxLen) {
            return str;
        } else {
            // endIndex+1最大为str长度
            return str.substring(0, endIndex + 1) + "...";
        }
    }
}
