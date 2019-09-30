## 编码规范

### 简介
* [Letv编码规范](./Letv编码规范.md)
* [Android编码规范](./Android编码规范.md)
* [阿里巴巴Java开发手册](./Alibaba-Java.pdf)

### Log篇
* 必须用LogUtils输出，不能直接调用android.os.Log输出，也不能用System.out输出
* 不要在getView，bindViewHolder等方法中大量输出log
* release版本中，只有I级别以上的log会输出，可以通过这个命令打开某些tag的DEBUG级别log：`adb shell setprop log.tag.[tag] DEBUG`
* Log的级别：
	* v: 不用
	* d: 调试用
	* I: 关键的状态log
	* w: 异常但是程序可以正常工作的log
	* e: 程序无法正常运行的log

### 性能篇
* 不用使用enum（内存占用是int的10倍）用@IntDef和@StringDef（附录1）
* 不要在顶层使用RelativeLayout
* 尽量使用primary type，不使用Integer等，因为会autoboxing
* 使用ArrayMap或者SimpleArrayMap代替HashMap，除非数量很多（>100）
* Parcelable比Serialize快10倍，可以使用插件自动生成代码：[插件地址](https://github.com/mcharmas/android-parcelable-intellij-plugin)
* 常量使用Static Final (static 15%-20% faster)
* 内部变量直接访问，不使用Getters/Setters (direct field access 3x faster)
* 资源压缩：小图片用svg，大图片用webp，转换工具可以用google家的[cwebp](https://developers.google.com/speed/webp/docs/cwebp)
* 尽量使用JobScheduler，而不是常驻Service

```
// 附录1
@IntDef({DISPLAY_MODE_PURE, DISPLAY_MODE_COMPLEX})
@Retention(RetentionPolicy.SOURCE)
@interface DisplayMode {
	int DISPLAY_MODE_PURE = 1;     //  只展示图片
	int DISPLAY_MODE_COMPLEX = 2;  //  展示信息
}
```


### 体验篇
* 点击区域，特别是大的点击区域，需要有触摸反馈
* 对于图片，使用ForegroundImageView，加上属性
	`android:foreground="@drawable/foreground_ripple"`
* 做每个需求的时候考虑，不要打扰用户，不要做不必要的网络请求，IO请求，常驻Service，开机自启等

### 开发篇
* 通过安装另外一个apk的方式来切换测试和线上服务器
* 发送消息用[EventBus](http://greenrobot.org/eventbus/)，不要再单独写callback，节省时间。EventBus的好处：
	* 不需要拿到对象的实例来注册callback
	* 避免忘记反注册callback导致内存问题
	* 可以使用sticky message，注册后能拿到之前的消息

### 格式篇
* 消除警告，即一个类的右边不应该有黄色的警告。
	* 根据IDE提示修改
	* 添加SuppressWarnings注解，类型可以是unused，unchecked, ResultOfMethodCallIgnored等。
	* 修改默认的File Header样式，在Setting->Editor->File and Code Templates->Includes->File Header中修改，建议统一格式。
* 一行代码不要超过100个字符，AS有一条线作为参照，超过的另起一行，换行标准可参考Android源码。
```
// 类注释模板1
/**
 * @author jack on 17-01-01
 */

// 类注释模板2
/**
 * @author jack
 * @since 17-01-01
 */
```
