# 抽象工厂模式
提供一个接口，创建相关的或依赖对象的家族，而不需要明确具体类，将Factory实例传入实体类中。
```Java
public interface TrafficFactory {
    void prepare();

    void create();

    void pack();
}

public class Traffic {
    protected String name;

    public Traffic(String name) {
        this.name = name;
    }

    public void create(TrafficFactory trafficFactory) {
        trafficFactory.prepare();
        trafficFactory.create();
        trafficFactory.pack();
    }
}

public class BaoMaStore extends AbstractTrafficStore {

    @Override
    public Traffic createTraffic(String type) {
        Traffic traffic = new Traffic(type);
        if (type.equals("baoma")) {
            traffic.create(new BaoMaFactory());
        }
        return traffic;
    }
}

public class BaoMaFactory implements TrafficFactory {
    @Override
    public void prepare() {

    }

    @Override
    public void create() {

    }

    @Override
    public void pack() {

    }
}

public class TrafficTest {
    public static void test() {
        BaoMaStore carStore = new BaoMaStore();
        Traffic traffic = carStore.createTraffic("baoma");
        System.out.println("traffic:" + traffic.name);
    }
}
```
