# 单例模式
常见的单例模式有8种写法，推荐的有以下3种：
* 饿汉式
* 双重检查
* 静态内部类

## 饿汉式-静态常量【可用】
```Java
public class Singleton {
    // 类加载时初始化
    private final static Singleton instance = new Singleton();

    private Singleton() {

    }

    public static Singleton getInstance() {
        return instance;
    }
}
```

## 饿汉式-静态代码块【可用】
```Java
public class Singleton {
    private static Singleton instance;

    static {
        // 类加载静态代码块
        instance = new Singleton();
    }

    private Singleton() {

    }

    public static Singleton getInstance() {
        return instance;
    }
}
```

## 懒汉式-线程不安全【不可用】
```Java
public class Singleton {
    private static Singleton instance;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

## 懒汉式-线程同步【不推荐】
```Java
public class Singleton {
    private static Singleton instance;

    private Singleton() {

    }

    // 添加同步，效率很低
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

## 懒汉式-同步代码块【不可用】
```Java
public class Singleton {
    private static Singleton instance;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if (instance == null) {
            // 添加同步代码块，效率很低
            synchronized (Singleton.class) {
                instance = new Singleton();
            }
        }
        return instance;
    }
}
```

## 懒汉式-双重检查【推荐用】
```Java
public class Singleton {
    private static Singleton instance;

    private Singleton() {

    }

    public static Singleton getInstance() {
        // 双重检查
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

## 懒汉式-静态内部类【推荐用】
```Java
public class Singleton {
    private Singleton() {

    }

    private static class SingletonInstance {
        // 类实例化时，初始化单例
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonInstance.INSTANCE;
    }
}
```

## 枚举【JDK1.5以上，实际用得少】
```Java
public enum Singleton {
    INSTANCE;
    public void whateverMethod() {

    }
}
```
