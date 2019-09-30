# 命令行模式
将一个请求封装为对象，让用户使用不同的请求将客户端参数化，对请求队列或者记录请求日志，支持撤销操作。

## 使用场景
需要抽象出将要执行的动作，将其用参数的形式提供出来。

## UML图
- Recevier, 接收者， 执行具体逻辑的角色。
- Command, 命令角色，实现execute方法
- Concrete Command， 具体命令角色， 持有Recevier， 在execute方法调用接受者相关方法
- Invoker, 调用者， 调用command的execute方法
- Client， 客户端角色

## 实例
Android的NotifyArgs
```Java
struct NotifyArgs {
  virtual ~NotifyArgs() {}
  virtual void notify(const sp<InputListenerInterface>& lestener) const = 0;
}
```

## 实战
```Java
// 命令类
public interface Command {
    void execute();
}

// 游戏类
public class GameMachine {

    public void toleft() {
        LogUtils.d(GameMachine.class, "向左");
    }

    public void toRight() {
        LogUtils.d(GameMachine.class, "向上");
    }

    public void toUp() {
        LogUtils.d(GameMachine.class, "向上");
    }

    public void toDown() {
        LogUtils.d(GameMachine.class, "向下");
    }
}

// 游戏指令-向左
public class LeftCommand implements Command {

    private GameMachine gameMachine;

    public LeftCommand(GameMachine gameMachine) {
        this.gameMachine = gameMachine;
    }

    @Override
    public void execute() {
        gameMachine.toleft();
    }
}

// 游戏指令-向右
public class RightCommand implements Command {

    private GameMachine gameMachine;

    public RightCommand(GameMachine gameMachine) {
        this.gameMachine = gameMachine;
    }


    @Override
    public void execute() {
        gameMachine.toRight();
    }
}

// 游戏指令-向上
public class UpCommand implements Command {

    private GameMachine gameMachine;

    public UpCommand(GameMachine gameMachine) {
        this.gameMachine = gameMachine;
    }


    @Override
    public void execute() {
        gameMachine.toUp();
    }
}

// 游戏指令-向下
public class DownCommand implements Command {

    private GameMachine gameMachine;

    public DownCommand(GameMachine gameMachine) {
        this.gameMachine = gameMachine;
    }

    @Override
    public void execute() {
        gameMachine.toDown();
    }
}

// 游戏手柄
public class Buttons {
    private LeftCommand leftCommand;
    private RightCommand rightCommand;
    private UpCommand upCommand;
    private DownCommand downCommand;

    public void setLeftCommand(LeftCommand leftCommand) {
        this.leftCommand = leftCommand;
    }

    public void setRightCommand(RightCommand rightCommand) {
        this.rightCommand = rightCommand;
    }

    public void setUpCommand(UpCommand upCommand) {
        this.upCommand = upCommand;
    }

    public void setDownCommand(DownCommand downCommand) {
        this.downCommand = downCommand;
    }

    public void toLeft() {
        leftCommand.execute();
    }

    public void toRight() {
        rightCommand.execute();
    }

    public void toUp() {
        upCommand.execute();
    }

    public void toDown() {
        downCommand.execute();
    }
}

// 测试类
public class CommandTest {

    public static void execute() {
        // 新建一个游戏
        GameMachine gameMachine = new GameMachine();

        // 游戏的四种命令
        LeftCommand leftCommand = new LeftCommand(gameMachine);
        RightCommand rightCommand = new RightCommand(gameMachine);
        UpCommand upCommand = new UpCommand(gameMachine);
        DownCommand downCommand = new DownCommand(gameMachine);

        // 游戏手柄
        Buttons buttons = new Buttons();
        buttons.setLeftCommand(leftCommand);
        buttons.setRightCommand(rightCommand);
        buttons.setUpCommand(upCommand);
        buttons.setDownCommand(downCommand);

        // 执行手柄动作
        buttons.toLeft();
        buttons.toRight();
        buttons.toDown();
        buttons.toUp();
    }
}


// 测试结果
05-24 10:10:28.075 23370-23370/com.free.com.free.design D/GameMachine: 向左
05-24 10:10:28.075 23370-23370/com.free.com.free.design D/GameMachine: 向上
05-24 10:10:28.075 23370-23370/com.free.com.free.design D/GameMachine: 向下
05-24 10:10:28.075 23370-23370/com.free.com.free.design D/GameMachine: 向上
```
