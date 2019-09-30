package com.me.ui.sample.library.pattern.behavior.state;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-24.
 */
public class PowerOnState implements ComputeState {
    @Override
    public void launch() {
        LogUtils.d(PowerOnState.class, "启动桌面");
    }

    @Override
    public void run() {
        LogUtils.d(PowerOnState.class, "电脑正在运行");
    }

    @Override
    public String showState() {
        return "开机状态";
    }
}
