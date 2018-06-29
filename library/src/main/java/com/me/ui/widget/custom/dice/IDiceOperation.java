package com.me.ui.widget.custom.dice;

/**
 * Description
 * Author:  Kevin.Tang
 * Date:    17/9/19 上午11:35
 */
public interface IDiceOperation {

    void start();

    void start(int value);

    void stop();

    void delayDismiss();

    void reset();

    void setDuration(long duration);

    int getValue();

    void setStateChangeListener(DiceView.DiceStateChangeListener listener);
}