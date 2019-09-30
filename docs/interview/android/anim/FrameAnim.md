# 逐帧动画
通过设定每一帧的图像和显示时间，形成动画效果。

## Xml配置
```java
// 配置动画
<?xml version="1.0" encoding="utf-8"?>
<animation-list xmlns:android="http://schemas.android.com/apk/res/android"
                android:oneshot="false">
    <item
        android:drawable="@mipmap/anim_frame_01"
        android:duration="500"/>
    <item
        android:drawable="@mipmap/anim_frame_02"
        android:duration="500"/>
    <item
        android:drawable="@mipmap/anim_frame_03"
        android:duration="500"/>
    <item
        android:drawable="@mipmap/anim_frame_04"
        android:duration="500"/>
</animation-list>
```

```java
// 开始动画
ImageView ivFrame = (ImageView) view.findViewById(R.id.iv_anim_frame);
mFrameDrawable = (AnimationDrawable) getResources()
        .getDrawable(R.drawable.anim_frame, getActivity().getTheme());
ivFrame.setBackground(mFrameDrawable);
mFrameDrawable.start();

// 停止动画
mFrameDrawable.stop();
```

## 动态添加
```java
// 开始动画
ImageView ivDynamicFrame = (ImageView) view.findViewById(R.id.iv_anim_dynamic_frame);
mFrameDynamicDrawable = new AnimationDrawable();
mFrameDynamicDrawable.addFrame(getResources()
        .getDrawable(R.mipmap.anim_frame_03, getActivity().getTheme()), 500);
mFrameDynamicDrawable.addFrame(getResources()
        .getDrawable(R.mipmap.anim_frame_04, getActivity().getTheme()), 500);
mFrameDynamicDrawable.addFrame(getResources()
        .getDrawable(R.mipmap.anim_frame_01, getActivity().getTheme()), 500);
mFrameDynamicDrawable.addFrame(getResources()
        .getDrawable(R.mipmap.anim_frame_02, getActivity().getTheme()), 500);
ivDynamicFrame.setBackground(mFrameDynamicDrawable);
mFrameDynamicDrawable.start();

// 停止动画
mFrameDynamicDrawable.stop();
```
