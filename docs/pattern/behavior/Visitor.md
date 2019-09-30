# 访问者模式
将数据操作与数据结构分类，使用频率不高。封装了元素的操作，在不改变数据的结构的情况下，定义作用与这些元素的新操作。

## 使用场景
- 对象比较稳定，需要在对象的结构上定义新的操作
- 新的操作，不会修改原来的类

## UML图
- Vistor，接口或这抽象类，定义了每一个元素的行为，参数就是可访问的元素
- ConcreteVistor，具体访问者
- Element，元素抽象类
- ElementA, ElementB，元素具体类
- ObjectStructure, 管理元素集合，可以迭代这些元素供访问者访问

## 实例
Android的注解

## 实战
```Java
// 员工抽象类
public abstract class Staff {
    public String name;
    public int kpi;

    public Staff(String name) {
        this.name = name;
        kpi = new Random().nextInt(100);
    }

    public abstract void accept(Visitor visitor);
}

// 工程师
public class Engineer extends Staff {

    public Engineer(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getCodeLines() {
        return new Random().nextInt(10 * 10000);
    }
}

// 经理
public class Manager extends Staff {

    private int products;

    public Manager(String name) {
        super(name);
        products = new Random().nextInt(10);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getProducts() {
        return products;
    }
}

// 访问者接口类
public interface Visitor {
    void visit(Engineer engineer);
    void visit(Manager manager);
}

// CEO访问者类
public class CEOVisitor implements Visitor {
    @Override
    public void visit(Engineer engineer) {
        LogUtils.d(CEOVisitor.class, "工程师：" + engineer.name + ", kpi:" + engineer.kpi);
    }

    @Override
    public void visit(Manager manager) {
        LogUtils.d(CEOVisitor.class, "经理：" + manager.name + ", kpi:" + manager.kpi);
    }
}

// CTO访问者类
public class CTOVisitor implements Visitor {
    @Override
    public void visit(Engineer engineer) {
        LogUtils.d(CTOVisitor.class, "工程师：" + engineer.name + ", 代码数:" + engineer.getCodeLines());
    }

    @Override
    public void visit(Manager manager) {
        LogUtils.d(CTOVisitor.class, "经理：" + manager.name + ", 产品数:" + manager.getProducts());
    }
}

// 报表-访问类
public class BusinessReport {
    private List<Staff> staffs = new ArrayList<>();

    public void collect() {
        staffs.add(new Manager("李经理"));
        staffs.add(new Engineer("小马"));
        staffs.add(new Engineer("小张"));
        staffs.add(new Engineer("小刘"));
    }

    public void show(Visitor visitor) {
        for(Staff staff : staffs) {
            staff.accept(visitor);
        }
    }
}

// 测试代码
public class VisitorTest {

    public static void execute() {
        BusinessReport businessReport = new BusinessReport();
        businessReport.collect();

        LogUtils.d(VisitorTest.class, "-------------给CEO看的报表--------------");
        businessReport.show(new CEOVisitor());

        LogUtils.d(VisitorTest.class, "-------------给CTO看的报表--------------");
        businessReport.show(new CTOVisitor());
    }
}

// 测试结果
05-24 14:13:25.370 28999-28999/? D/VisitorTest: -------------给CEO看的报表--------------
05-24 14:13:25.370 28999-28999/? D/CEOVisitor: 经理：李经理, kpi:11
05-24 14:13:25.370 28999-28999/? D/CEOVisitor: 工程师：小马, kpi:36
05-24 14:13:25.370 28999-28999/? D/CEOVisitor: 工程师：小张, kpi:76
05-24 14:13:25.370 28999-28999/? D/CEOVisitor: 工程师：小刘, kpi:24
05-24 14:13:25.370 28999-28999/? D/VisitorTest: -------------给CTO看的报表--------------
05-24 14:13:25.371 28999-28999/? D/CTOVisitor: 经理：李经理, 产品数:7
05-24 14:13:25.371 28999-28999/? D/CTOVisitor: 工程师：小马, 代码数:10851
05-24 14:13:25.371 28999-28999/? D/CTOVisitor: 工程师：小张, 代码数:95216
05-24 14:13:25.371 28999-28999/? D/CTOVisitor: 工程师：小刘, 代码数:76632
```
