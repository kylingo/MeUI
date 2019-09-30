# 外观模式
要求一个子系统的外部和内部的通信，必须通过统一的对象完成。门面模式提供一个高层次的接口，使的子系统更好用。

## 使用场景
- 复杂的系统，包括多个子系统
- 需要对外提供统一的接口

## UML图
- Client
- Facade
- SubSystem

## 实例
各种第三方SDK，Android的Context类
```Java
Glide.with(context).load(url).into(imageView);
```
