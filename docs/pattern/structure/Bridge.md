# 桥接模式
讲抽象部分与实现部分分离，使它们可以独立地进行变化

## 使用场景
一个类存在二个维度变化，而且二个维度都要扩展。

## UML图
- Implementor 实现部分的基类
- ImplementorA or  ImplementorB 部分具体实现
- Abstraction 引用一个实现对象
- RefinedAbstraction 扩展Abstraction

## 与装饰者模式的区别
- 桥接模式将结构和实现分离，或者属性与基于属性的行为分离； 而装饰者基于属性的行为封装成独立的类。
- 桥接模式的行为是横向扩展的，行为之前彼此没有关系； 而装饰者模式的行为具有可叠加性，表现为一个整体。
- 重点：在于找到实现部分的基类

## 实例
Android的View,Button之间的继承关系，定义了基本的属性和行为。  
而绘制到屏幕的部分由View相关的功能实现类DisplayList, HardwareLayer和Canvas完成。以上二者可以看成桥接模式。  

Adapter和AdapterView，Window和WindowManager

## 实战

```Java
// 实现类基类
public interface Work {
    void doSomeThing();
}

// 员工实现类
public class WorkStaff implements Work {

    @Override
    public void doSomeThing() {
        LogUtils.d(WorkStaff.class, "我的工作是加班，加班，加班！");
    }
}

// 老板实现类
public class WorkBoss implements Work {

    @Override
    public void doSomeThing() {
        LogUtils.d(WorkBoss.class, "我的工作是开会，开会，开会！");
    }
}

// 抽象类
public abstract class Person {
    private Work work;

    public Person(Work work) {
        this.work = work;
        createPerson();
    }

    public void work() {
        work.doSomeThing();
    }

    public abstract void createPerson();
}

// 男人
public class Man extends Person {
    public Man(Work work) {
        super(work);
    }

    @Override
    public void createPerson() {
        LogUtils.d(Man.class, "我是一个男人");
    }
}

// 女人
public class Woman extends Person {
    public Woman(Work work) {
        super(work);
    }

    @Override
    public void createPerson() {
        LogUtils.d(Woman.class, "我是一个女人");
    }
}

// 测试类
public class BridgeTest {

    public static void execute() {
        WorkBoss workBoss = new WorkBoss();
        WorkStaff workStaff = new WorkStaff();

        Man manBoss = new Man(workBoss);
        manBoss.work();

        Man manStaff = new Man(workStaff);
        manStaff.work();

        Woman womanBoss = new Woman(workBoss);
        womanBoss.work();

        Woman womanStaff = new Woman(workStaff);
        womanStaff.work();
    }
}

// 运行结果
05-15 15:03:22.311 29054-29054/? D/Man: 我是一个男人
05-15 15:03:22.311 29054-29054/? D/WorkBoss: 我的工作是开会，开会，开会！
05-15 15:03:22.311 29054-29054/? D/Man: 我是一个男人
05-15 15:03:22.311 29054-29054/? D/WorkStaff: 我的工作是加班，加班，加班！
05-15 15:03:22.311 29054-29054/? D/Woman: 我是一个女人
05-15 15:03:22.311 29054-29054/? D/WorkBoss: 我的工作是开会，开会，开会！
05-15 15:03:22.311 29054-29054/? D/Woman: 我是一个女人
05-15 15:03:22.311 29054-29054/? D/WorkStaff: 我的工作是加班，加班，加班！
```
