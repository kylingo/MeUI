package com.me.ui.sample.pattern.behavior.template;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-18.
 */
public class Cook extends AbstractCook {
    
    @Override
    protected void buyFood() {
        LogUtils.d(Cook.class, "买一斤牛肉，一条鱼，一份蔬菜。");
    }

    @Override
    protected void prepareCook() {
        LogUtils.d(Cook.class, "洗菜，准备做饭。");
    }

    @Override
    protected void cook() {
        LogUtils.d(Cook.class, "开始做饭！");
    }
}
