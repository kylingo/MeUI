# 模板模式
一个算法所需的关键步骤顺序是固定的。

## 使用场景
- 多个子类有共有方法，且逻辑基本相同时。
- 核心的算法可以定义成模板模式，其他的子类实现相关细节
- 重构时，模板方法模式是一个常用的模式，把相同代码抽取到父类，通过钩子函数约束其行为

## UML图
- Abstract Template, A+B+C+D
- ConcreteImplA, ConcreteImplB extends Template

## 优缺点
- 可以通过final修饰符，约束子类不能重写。
- 封装不变部分，拓展可变部分
- 提取公共代码，便于维护
- 提高代码的复用率，更好的扩展性

## 实例
Android的AsyncTask，Activity的生命周期等

## 实战

```Java
// 抽象类
public abstract class AbstractCook {

    protected abstract void buyFood();

    protected abstract void prepareCook();

    protected abstract void cook();

    public final void execute() {
        buyFood();

        prepareCook();

        cook();
    }
}

// 子类实现
public class Cook extends AbstractCook {

    @Override
    protected void buyFood() {
        LogUtils.d(Cook.class, "买一斤牛肉，一条鱼，一份蔬菜。");
    }

    @Override
    protected void prepareCook() {
        LogUtils.d(Cook.class, "洗菜，准备做饭。");
    }

    @Override
    protected void cook() {
        LogUtils.d(Cook.class, "开始做饭！");
    }
}

// 测试代码
public class TemplateTest {
    public static void execute() {
        Cook cook = new Cook();
        cook.execute();
    }
}

// 测试结果
05-18 15:40:27.581 20793-20793/com.free.com.free.design D/Cook: 买一斤牛肉，一条鱼，一份蔬菜。
05-18 15:40:27.581 20793-20793/com.free.com.free.design D/Cook: 洗菜，准备做饭。
05-18 15:40:27.581 20793-20793/com.free.com.free.design D/Cook: 开始做饭！
```
