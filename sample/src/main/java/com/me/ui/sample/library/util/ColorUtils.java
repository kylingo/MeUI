package com.me.ui.sample.library.util;

import com.me.ui.sample.base.ColorItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author kylingo
 * @since 2019/09/30 09:56
 */
public class ColorUtils {

    public static List<ColorItem> getData(int count) {
        List<ColorItem> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ColorItem mainItem = new ColorItem();
            mainItem.index = i;
            mainItem.color = getRandColorCode();
            list.add(mainItem);
        }

        return list;
    }

    public static int getRandColorCode() {
        Random random = new Random();
        return 0xff000000 | random.nextInt(0x00ffffff);
    }
}
