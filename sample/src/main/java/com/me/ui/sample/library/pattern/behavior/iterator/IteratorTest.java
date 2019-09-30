package com.me.ui.sample.library.pattern.behavior.iterator;

import com.me.ui.util.LogUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author studiotang on 17/5/23
 */
public class IteratorTest {

    public static void execute() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            LogUtils.d(IteratorTest.class, "s:" + s);
        }
    }
}
