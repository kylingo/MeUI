# 享元模式
对象池的实现，代表轻量级的意思，尽可能减少内存的使用量。

## 使用场景
- 系统存在大量相似的对象
- 对象没有特定的身份
- 需要缓冲池的场景

## UML图
- Flyweight 享元对象的抽象类或者接口
- ConcreteFlyweight 具体的享元对象
- FlyweightFactory 享元工厂，负责创建和管理享元对象，里面有个Map

## 实例
Android的Message.obtain();

## 实战
例如Map的使用
