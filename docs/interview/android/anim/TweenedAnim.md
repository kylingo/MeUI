# 补间动画
## Xml配置
```
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">

    <alpha
        android:duration="3000"
        android:fromAlpha="0.1"
        android:toAlpha="1.0"/>

    <!--
        accelerate_decelerate_interpolator  加速-减速 动画插入器
        accelerate_interpolator             加速-动画插入器
        decelerate_interpolator             减速- 动画插入器
    -->
    <scale
        android:duration="3000"
        android:fillAfter="false"
        android:fromXScale="0.0"
        android:fromYScale="0.0"
        android:interpolator="@android:anim/accelerate_decelerate_interpolator"
        android:pivotX="50%"
        android:pivotY="50%"
        android:toXScale="1.0"
        android:toYScale="1.0"/>

    <translate
        android:duration="3000"
        android:fromXDelta="-20"
        android:fromYDelta="-20"
        android:toXDelta="20"
        android:toYDelta="20"/>

    <rotate
        android:interpolator="@android:anim/accelerate_decelerate_interpolator"
        android:duration="3000"
        android:fromDegrees="0"
        android:pivotX="50%"
        android:pivotY="50%"
        android:toDegrees="360"/>
</set>
```

```java
// 开始动画
Animation tweenedAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_tweened);
imageView.startAnimation(tweenedAnimation);

// 结束动画
imageView.clearAnimation();
```

## 动态添加
```java
AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
alphaAnimation.setDuration(DURATION_ANIM);

ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 0.5f, 0.5f);
scaleAnimation.setDuration(DURATION_ANIM);

TranslateAnimation translateAnimation = new TranslateAnimation(-20, 20, -20, 20);
translateAnimation.setDuration(DURATION_ANIM);

RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
rotateAnimation.setDuration(DURATION_ANIM);

final AnimationSet animationSet = new AnimationSet(true);
animationSet.setInterpolator(getActivity(), android.R.anim.accelerate_decelerate_interpolator);
animationSet.addAnimation(alphaAnimation);
animationSet.addAnimation(scaleAnimation);
animationSet.addAnimation(translateAnimation);
animationSet.addAnimation(rotateAnimation);

// 开始动画
imageView.startAnimation(animationSet);

// 结束动画
imageView.clearAnimation();
```
