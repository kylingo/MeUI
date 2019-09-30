# 工厂模式
* 静态工厂模式
* 简单工厂模式
* 工厂方法模式
* 抽象工厂模式

## 静态工厂模式
类+静态方法，例如：TextUtils.isEmpty。

## 简单工厂模式
通过简单工厂类获取相应的类实例。
```Java
public class SimpleTrafficFactory {

    public String createTraffic(String type) {
        if ("car".equals(type)) {
            return "car";
        } else if ("bus".equals(type)) {
            return "bus";
        } else if ("plane".equals(type)) {
            return "plane";
        }

        return null;
    }
}

public class SimpleTrafficStore {

    private SimpleTrafficFactory simpleTrafficFactory;

    public SimpleTrafficStore(SimpleTrafficFactory simpleTrafficFactory) {
        this.simpleTrafficFactory = simpleTrafficFactory;
    }

    public String sellTraffic(String type) {
        return simpleTrafficFactory.createTraffic(type);
    }
}
```

## 工厂方法模式
定义一个创建对象的借口，由子类决定要实例化的是哪个。工厂方法模式把类实例化的过程推迟到子类。
```Java
public abstract class AbstractTrafficStore {

    public abstract String createTraffic(String type);

    public String sellTraffic(String type) {
        return createTraffic(type);
    }
}
```
