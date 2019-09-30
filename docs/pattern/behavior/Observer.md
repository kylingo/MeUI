# 观察者模式
将观察者和被观察者解耦

## 使用场景
- 关联行为场景，需要注意的是关联行为是可以拆分的，而不是“组合”关系
- 事件多级触发场景
- 跨系统的消息交换场景，如消息队列、事件总线

## UML图
- Subject，抽象主题，被观察的角色把所有观察者放入一个集合中，可以有任意数量，并提供一个接口，可以增加、删除观察者对象
- ConcreteSubject, 具体主题，将角色有关状态存入具体观察者对象，在具体主题的内部状态改变时，给所有注册过的观察者发出通知。
- Observer，抽象观察者，定义一个更新接口，使得主题更新时，更新自己
- ConcreteObserver，具体观察者

## 实例
Android的Adapter,Broadcast类

## 实战
```Java
// 程序员
public class Coder implements Observer {

    private String name;

    public Coder(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable observable, Object data) {
        LogUtils.d(Coder.class, "Hi, " + name + ", " + data);

    }
}

// 干货订阅
public class Gank extends Observable {

    public void update(String content) {
        setChanged();
        notifyObservers(content);
    }
}

// 测试
public class ObserverTest {
    public static void execute() {
        Gank gank = new Gank();

        Coder coder1 = new Coder("coder1");
        Coder coder2 = new Coder("coder2");
        Coder coder3 = new Coder("coder3");
        gank.addObserver(coder1);
        gank.addObserver(coder2);
        gank.addObserver(coder3);

        gank.update("今日干货很棒，欢迎预览～");
    }
}

// 测试结果
05-23 17:56:26.959 14382-14382/com.free.com.free.design D/Coder: Hi, coder1, 今日干货很棒，欢迎预览～
05-23 17:56:26.959 14382-14382/com.free.com.free.design D/Coder: Hi, coder2, 今日干货很棒，欢迎预览～
05-23 17:56:26.959 14382-14382/com.free.com.free.design D/Coder: Hi, coder3, 今日干货很棒，欢迎预览～
```
