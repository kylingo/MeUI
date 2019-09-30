# 原型模式
用于拷贝创建新的实例对象，属于内存拷贝，不会执行构造方法。

## 优点
- 性能好，直接进行内存拷贝，对于复制大对象时，性能差别明显。
- 简化创建对象流程，和复制粘贴一样简单。

## 注意
- 单例模式与原型模式冲突，不能执行构造方法。
- 浅拷贝，只拷贝基本数据，对于数组，容器对象，引用对象都不会拷贝。如果想实现深拷贝，必须另行拷贝。
- 如果实例中有其他对象，如果只是浅拷贝，那么只是拷贝这个对象的引用。

## 实例
```Java
public class Prototype implements Cloneable {

    private ArrayList<String> list = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Prototype prototype = null;
        try {
            prototype = (Prototype) super.clone();

            // 深拷贝
            prototype.list = (ArrayList<String>) this.list.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return prototype;
    }
}
```
