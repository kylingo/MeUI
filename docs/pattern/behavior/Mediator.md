# 中介者模式
包含了一系列对象相互作用的方式， 使得这些对象不必相互影响

## 使用场景
当对象之间的交互操作很多，且每个对象的行为操作都彼此依赖时，可采取中介者模式，解决耦合问题。

## UML图
- Mediator, 抽象中介者，定义同事到中介者对象的接口，一般为抽象类。
- ConcreteMediator, 具体中介者，向具体同事发出命令
- Colleague, 抽象同事类，定义了中介者对象接口，形参。
- ConcreteColleague, 具体同事类，每个同事只知道自己的小范围行为，不知道大范围行为。
- 相互持有

## 实例
Android的KeyguardViewMediator，各种页面交互类

## 实战
```Java
// 中介者抽象类
public abstract class Mediator {

    abstract void change(Colleague c);
}

// 主板
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

// 同事类
public abstract class Colleague {

    protected Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }
}

// CPU
public class CPU extends Colleague {
    private String videoData;
    private String audioData;

    public CPU(Mediator mediator) {
        super(mediator);
    }

    public void decodeData(String data) {
        String[] tmp = data.split(",");
        if (tmp.length > 0) {
            videoData = tmp[0].trim();
        }

        if (tmp.length > 1) {
            audioData = tmp[1].trim();
        }

        mediator.change(this);
    }

    public String getVideoData() {
        return videoData;
    }

    public String getAudioData() {
        return audioData;
    }
}

// CD光驱
public class CDDevice extends Colleague {

    private String data;

    public CDDevice(Mediator mediator) {
        super(mediator);
    }

    public void load() {
        data = "大话西游, 一生所爱";
        mediator.change(this);
    }

    public String getData() {
        return data;
    }
}

// 显卡
public class GraphicsCard extends Colleague {

    public GraphicsCard(Mediator mediator) {
        super(mediator);
    }

    public void playVideo(String video) {
        LogUtils.d(GraphicsCard.class, "播放视频:" + video);
    }
}

// 声卡
public class SoundCard extends Colleague {

    public SoundCard(Mediator mediator) {
        super(mediator);
    }

    public void playAudio(String audio) {
        LogUtils.d(SoundCard.class, "播放音频：" + audio);
    }
}

// 测试代码
public class MediatorTest {

    public static void execute() {
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

// 测试结果
05-24 15:03:20.794 9248-9248/com.free.com.free.design D/GraphicsCard: 播放视频:大话西游
05-24 15:03:20.794 9248-9248/com.free.com.free.design D/SoundCard: 播放音频：一生所爱
```
