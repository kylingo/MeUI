package com.me.ui.sample.pattern;

import android.support.annotation.NonNull;

/**
 * @author kylingo
 * @since 2019/09/30 16:08
 */
public abstract class PatternTest {

    public abstract void execute();

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
