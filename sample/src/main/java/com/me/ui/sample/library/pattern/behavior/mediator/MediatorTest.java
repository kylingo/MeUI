package com.me.ui.sample.library.pattern.behavior.mediator;

import com.me.ui.sample.library.pattern.PatternTest;

/**
 * @author tangqi on 17-5-24.
 */
public class MediatorTest extends PatternTest {

    public void execute() {
        MainBoard mainBoard = new MainBoard();
        CPU cpu = new CPU(mainBoard);
        CDDevice cdDevice = new CDDevice(mainBoard);
        GraphicsCard graphicsCard = new GraphicsCard(mainBoard);
        SoundCard soundCard = new SoundCard(mainBoard);

        mainBoard.setCpu(cpu);
        mainBoard.setCdDevice(cdDevice);
        mainBoard.setGraphicsCard(graphicsCard);
        mainBoard.setSoundCard(soundCard);

        // 播放CD
        cdDevice.load();
    }
}
