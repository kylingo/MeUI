# 建造者模式
Build模式是一步一步创建一个复杂对象的创建型模式。将复杂对象的构建和表示分离。

## 使用场景
- 相同的方法，不同的执行顺序，产生不同的结果。
- 参数比较复杂，很多参数参数有默认值。

## 实例
请参考Android中的AlertDialog。

```Java
public class House {
    private int style;
    private int layer;

    public void show() {
        LogUtils.d(House.class, "show");
    }

    public static class Builder {
        private House house = new House();

        Builder setStyle(int style) {
            LogUtils.d(Builder.class, "style:" + style);
            house.style = style;
            return this;
        }

        Builder setLayer(int layer) {
            LogUtils.d(Builder.class, "layer:" + layer);
            house.layer = layer;
            return this;
        }

        House create() {
            LogUtils.d(Builder.class, "create");
            return house;
        }
    }
}

public class BuilderTest {

    public static void execute() {
        House house = new House.Builder()
                .setLayer(10)
                .setStyle(1)
                .create();
        house.show();
    }
}

05-15 11:33:02.645 5452-5452/? D/Builder: layer:10
05-15 11:33:02.645 5452-5452/? D/Builder: style:1
05-15 11:33:02.645 5452-5452/? D/Builder: create
```
