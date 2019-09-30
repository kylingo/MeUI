# 责任链模式
很多对象都有机会处理请求，避免请求者和接受者之间的耦合关系。

## 使用场景
多个对象可以处理同一请求，但具体有哪个对象处理则在运行时动态决定

## UML图
- Hanlder interface, nextHandler and handleRequest()
- Concrete handler1, handler2

## 实例
Android的事件分发，dispatchToucnEvent； 有序广播

## 实战
```Java
// 抽象领导类
public abstract class Leader {
    protected Leader nextHandler; //上一级

    protected abstract int limit();

    protected abstract void handle(int money);

    public void handleRequest(int money) {
        if (money <= limit()) {
            handle(money);
        } else {
            if (nextHandler != null) {
                nextHandler.handleRequest(money);
            }
        }
    }
}

// 组长
public class GroupLeader extends Leader {

    @Override
    protected int limit() {
        return 1000;
    }

    @Override
    protected void handle(int money) {
        LogUtils.d(GroupLeader.class, "组长批准" + money + "元");
    }
}

// 经理
public class Manager extends Leader{

    @Override
    protected int limit() {
        return 5000;
    }

    @Override
    protected void handle(int money) {
        LogUtils.d(GroupLeader.class, "经理批准" + money + "元");
    }
}

// 老板
public class Boss extends Leader {

    @Override
    protected int limit() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected void handle(int money) {
        LogUtils.d(GroupLeader.class, "老板批准" + money + "元");
    }
}

// 测试代码
public class HandlerTest {

    public static void execute() {
        GroupLeader groupLeader = new GroupLeader();
        Manager manager = new Manager();
        Boss boss = new Boss();

        groupLeader.nextHandler = manager;
        manager.nextHandler = boss;

        groupLeader.handleRequest(800);
        groupLeader.handleRequest(2000);
        groupLeader.handleRequest(10000);
    }
}

// 测试结果
05-23 23:16:10.463 30241-30241/com.free.com.free.design I/GroupLeader: 组长批准800元
05-23 23:16:10.463 30241-30241/com.free.com.free.design I/Manager: 经理批准2000元
05-23 23:16:10.463 30241-30241/com.free.com.free.design I/Boss: 老板批准10000元
```
