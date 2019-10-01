package com.me.ui.sample.pattern.behavior.mediator;

/**
 * @author tangqi on 17-5-24.
 */
public class MainBoard extends Mediator {

    private CPU cpu;
    private CDDevice cdDevice;
    private GraphicsCard graphicsCard;
    private SoundCard soundCard;

    @Override
    void change(Colleague c) {
        if (c == cpu) {
            handleCPU((CPU) c);
        } else if (c == cdDevice) {
            handleCD((CDDevice) c);
        }
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public void setCdDevice(CDDevice cdDevice) {
        this.cdDevice = cdDevice;
    }

    public void setGraphicsCard(GraphicsCard graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public void setSoundCard(SoundCard soundCard) {
        this.soundCard = soundCard;
    }

    public void handleCD(CDDevice cdDevice) {
        cpu.decodeData(cdDevice.getData());
    }

    public void handleCPU(CPU cpu) {
        graphicsCard.playVideo(cpu.getVideoData());
        soundCard.playAudio(cpu.getAudioData());
    }
}
