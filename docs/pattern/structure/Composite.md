# 组合模式
也称为部分-整体模式，将一组相似的对象看做一个对象处理，并根据一个树状结构组合对象，并忽略对象之间的差异。

## 使用场景
具有整体-部分的层次结构时，从一个整体能独立出模块和功能的场景。

## UML图
- Component abstract class
- Composite extends Component
- Leaf extends Component
- Client invoke Component

## 实例
Android的文件系统，View和ViewGroup。

## 实战
由于真正的项目中很少有用到组合模式的，一般用于UI或者文件选择器等。
