package com.me.ui.sample.library.pattern.behavior.state;

import android.support.annotation.Nullable;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-24.
 */
public class ComputeController implements IPowerController, ComputeState {

    private ComputeState computeState;

    public ComputeController() {

    }

    public void setComputeState(@Nullable ComputeState computeState) {
        this.computeState = computeState;
    }


    @Override
    public void powerOn() {
        LogUtils.d(ComputeController.class, "开机啦！");
        setComputeState(new PowerOnState());
    }

    @Override
    public void powerOff() {
        LogUtils.d(ComputeController.class, "关机啦！");
        setComputeState(new PowerOffState());
    }

    @Override
    public void launch() {
        computeState.launch();
    }

    @Override
    public void run() {
        computeState.run();
    }

    @Override
    public String showState() {
        return computeState.showState();
    }
}
