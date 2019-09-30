package com.me.ui.sample.library.pattern.behavior.state;

import com.me.ui.sample.library.pattern.PatternTest;

/**
 * @author tangqi on 17-5-24.
 */
public class StateTest extends PatternTest {

    public void execute() {
        ComputeController computeController = new ComputeController();
        computeController.powerOn();
        computeController.showState();
        computeController.launch();
        computeController.run();

        computeController.powerOff();
        computeController.showState();
        computeController.launch();
        computeController.run();
    }
}
