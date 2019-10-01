package com.me.ui.sample.pattern.create.builder;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-15.
 */
public class House {
    private int style;
    private int layer;

    public void show() {
        LogUtils.d(House.class, "show");
    }

    public static class Builder {
        private House house = new House();

        Builder setStyle(int style) {
            LogUtils.d(Builder.class, "style:" + style);
            house.style = style;
            return this;
        }

        Builder setLayer(int layer) {
            LogUtils.d(Builder.class, "layer:" + layer);
            house.layer = layer;
            return this;
        }

        House create() {
            LogUtils.d(Builder.class, "create");
            return house;
        }
    }
}
