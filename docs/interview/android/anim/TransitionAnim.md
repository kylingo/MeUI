# 过渡动画
Android页面之间的过渡动画。
```java
((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
```
注意：此方法会在startActivity和finish之后立即调用。意思是说，你要在A start B的时候调用一次，还要在B finish的时候调用一次，才有连贯的动画！记得有两次喔！

## 种类
- fade_in
- fade_out
- slide_in_left
- slide_out_right
- explode

## 自定义
当然还可以在anim目录子定义帧动画，类似于这样的
```java
<set xmlns:android="http://schemas.android.com/apk/res/android">
	<translate android:fromXDelta="-50%p" android:toXDelta="0"
            android:duration="@android:integer/config_mediumAnimTime"/>
	<alpha android:fromAlpha="0.0" android:toAlpha="1.0"
            android:duration="@android:integer/config_mediumAnimTime" />
</set>
```

## 系统5.0提供的过渡动画
共享元素
- changeBounds :改变目标视图布局边界
- changeClipBounds:裁剪目标布局边界
- changeTransform:改变视图缩放比例，旋转角度
- changeImageTransform:改变视图大小，缩放比例

要使用过渡动画的多个activity里面添加
```java
// 允许使用transitions  
getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

// 或者在布局样式文件里面添加如下代码
<item name="android:windowContentTransitions">true</item>
```

进入动画
```java
getWindow().setEnterTransition(new Explode());
getWindow().setEnterTransition(new Slide());
getWindow().setEnterTransition(new Fade());
```

退出动画
```java
getWindow().setExitTransition(new Explode());
getWindow().setExitTransition(new Slide());
getWindow().setExitTransition(new Fade());
```

activity跳转启动动画效果
```
startActivity(intent,  
              ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
```
## 参考资料
- http://www.cnblogs.com/androidsuperman/p/4985149.html
