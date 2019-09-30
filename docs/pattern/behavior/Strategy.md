# 策略模式
实现某一个功能有多个算法或者策略，我们根据实际情况选择不同的算法或者策略完成改功能，例如排序算法。

## 使用场景
- 针对同一问题类型的的多种处理方式，仅仅是具体行为有差异。
- 出现同一抽象类有多个子类，需要用if else来判断选择子类时。

## UML图
- Stragety interface
- StragetyA and stragetyB implements StragetyA
- Content setStragety

## 实例
Android的Animation

## 实战

```Java
// 交通策略
public interface TrafficStrategy {
    void run();
}

// 飞机策略
public class AircraftStrategy implements TrafficStrategy {

    @Override
    public void run() {
        LogUtils.d(AircraftStrategy.class, "坐飞机");
    }
}

// 大巴策略
public class BusStrategy implements TrafficStrategy {
    @Override
    public void run() {
        LogUtils.d(BusStrategy.class, "坐大巴");
    }
}

// 火车策略
public class RailwayStrategy implements TrafficStrategy {
    @Override
    public void run() {
        LogUtils.d(RailwayStrategy.class, "坐火车");
    }
}

// 旅行
public class Travel {

    private TrafficStrategy trafficStrategy;

    public Travel() {
        // 设置默认策略
        trafficStrategy = new RailwayStrategy();
    }

    public void run() {
        trafficStrategy.run();
    }

    public void setTrafficStrategy(TrafficStrategy trafficStrategy) {
        this.trafficStrategy = trafficStrategy;
    }
}

// 测试代码
public class StrategyTest {

    public static void execute() {
        Travel travel = new Travel();
        travel.setTrafficStrategy(new AircraftStrategy());
        travel.run();
    }
}


// 测试结果
05-15 16:32:21.943 11328-11328/? D/AircraftStrategy: 坐飞机
```
