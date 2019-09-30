# 装饰者模式
也可以称为包装模式（Wrapper Pattern），使用一种透明的方式，动态地扩展对象的功能，继承关系的动态方法。

## 使用场景
需要透明的，动态的扩展类的功能，装饰类可以轻松实现组件的扩展。

## UML图
- Component
- ConcreteComponent extends Component
- Decorator extends Component, and incoming component instance
- ConCreteDecorator extends Decorator

## Android实例
Android的Context和四大组件

## 实践
英雄的抽象类
```Java
public abstract class Role {

    // 绘制
    abstract void draw();

    // 攻击
    abstract void attack();

    // 逃跑
    abstract void run();

    // 发呆
    abstract void daze();
}
```

英雄-白虎
```Java
public class Pom extends Role {

    @Override
    void draw() {
        LogUtils.d(Pom.class, "draw");
    }

    @Override
    void attack() {
        LogUtils.d(Pom.class, "attack");
    }

    @Override
    void run() {
        LogUtils.d(Pom.class, "run");
    }

    @Override
    void daze() {
        LogUtils.d(Pom.class, "daze");
    }
}
```

英雄装饰抽象类
```Java
public abstract class RoleDecorator extends Role {
    private Role role;

    public RoleDecorator(Role role) {
        this.role = role;
    }

    @Override
    void draw() {
        role.draw();
    }

    @Override
    void attack() {
        role.attack();
    }

    @Override
    void run() {
        role.run();
    }

    @Override
    void daze() {
        role.daze();
    }
}
```

英雄-白虎-黑色
```Java
public class PomBlack extends RoleDecorator {

    public PomBlack(Role role) {
        super(role);
    }

    @Override
    void draw() {
        super.draw();
        LogUtils.d(PomBlack.class, "I'm black pom");
    }
}
```

英雄-白虎-白色
```Java
public class PomWhite extends RoleDecorator {

    public PomWhite(Role role) {
        super(role);
    }

    @Override
    void draw() {
        super.draw();
        LogUtils.d(PomWhite.class, "I'm white pom");
    }
}
```

英雄-白虎-无敌
```Java
public class PomInvincible extends RoleDecorator {
    public PomInvincible(Role role) {
        super(role);
    }

    @Override
    void draw() {
        super.draw();
        LogUtils.d(PomInvincible.class, "I'm pom invincible");
    }
}
```

// 测试类
```Java
public class DecoratorTest {

    public static void execute() {
        Pom pom = new Pom();
        PomBlack pomBlack = new PomBlack(pom);
        PomWhite pomWhite = new PomWhite(pom);
        PomInvincible pomInvincible = new PomInvincible(pomBlack);
        pomBlack.draw();
        pomWhite.draw();
        pomInvincible.draw();
    }
}

日志输出：
// 黑白虎
05-12 15:43:22.661 24363-24363/? D/PomBlack: I'm black pom

// 白白虎
05-12 15:43:22.661 24363-24363/? D/PomWhite: I'm white pom

// 无敌黑白虎
05-12 15:43:22.661 24363-24363/? D/PomBlack: I'm black pom
05-12 15:43:22.661 24363-24363/? D/PomInvincible: I'm pom invincible
```
