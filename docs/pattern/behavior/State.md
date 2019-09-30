# 状态模式
当一个对象的内在状态改变时，允许改变其行为，这个对象看起来像是改变了其类。

## 使用场景
- 一个对象的行为取决为状态
- 一个类有庞大的分支语句，而分支依赖与对象的状态，将每个分支单独放到一个类中，通过多态去除重复的判断。.

## UML图
- State 抽象状态行为
- ConcreteStateA, ConscreteStateB, 具体状态类，实现不同状态的不同行为

## 实例
Android的WiFi状态管理

## 实战
```Java
// 电脑状态抽象类
public interface ComputeState {
    void launch();
    void run();
    String showState();
}

// 电脑开机状态
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

// 电脑关机状态
public class PowerOffState implements ComputeState {
    @Override
    public void launch() {

    }

    @Override
    public void run() {

    }

    @Override
    public String showState() {
        return "关机状态";
    }
}

// 电源接口类
public interface IPowerController {
    void powerOn();
    void powerOff();
}

// 电脑控制类
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

// 测试代码
public class StateTest {

    public static void execute() {
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

// 测试结果
05-24 12:48:14.077 17881-17881/com.free.com.free.design D/ComputeController: 开机啦！
05-24 12:48:14.077 17881-17881/com.free.com.free.design D/PowerOnState: 启动桌面
05-24 12:48:14.077 17881-17881/com.free.com.free.design D/PowerOnState: 电脑正在运行
05-24 12:48:14.077 17881-17881/com.free.com.free.design D/ComputeController: 关机啦！
```
