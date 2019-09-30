# 属性动画
View Animation相当简单，不过只能支持简单的缩放、平移、旋转、透明度基本的动画，且有一定的局限性。比如：你希望View有一个颜色的切换动画；你希望可以使用3D旋转动画；你希望当动画停止时，View的位置就是当前的位置；这些View Animation都无法做到。这就是Property Animation产生的原因。

## 相关API
Property Animation故名思议就是通过动画的方式改变对象的属性了，我们首先需要了解几个属性：
- Duration：动画的持续时间，默认300ms。
- Time interpolation：时间差值，乍一看不知道是什么，但是我说LinearInterpolator、AccelerateDecelerateInterpolator，大家一定知道是干嘛的了，定义动画的变化率。
- Repeat count and behavior：重复次数、以及重复模式；可以定义重复多少次；重复时从头开始，还是反向。
- Animator sets: 动画集合，你可以定义一组动画，一起执行或者顺序执行。
- Frame refresh delay：帧刷新延迟，对于你的动画，多久刷新一次帧；默认为10ms，但最终依赖系统的当前状态；基本不用管。

相关的类
- ObjectAnimator  动画的执行类，后面详细介绍
- ValueAnimator 动画的执行类，后面详细介绍
- AnimatorSet 用于控制一组动画的执行：线性，一起，每个动画的先后执行等。
- AnimatorInflater 用户加载属性动画的xml文件
- TypeEvaluator  类型估值，主要用于设置动画操作属性的值。
- TimeInterpolator 时间插值，上面已经介绍。

总的来说，属性动画就是，动画的执行类来设置动画操作的对象的属性、持续时间，开始和结束的属性值，时间差值等，然后系统会根据设置的参数动态的变化对象的属性。

## ObjectAnimator
基本用法如下
```java
ImageView ivObjectAnim = (ImageView) view.findViewById(R.id.iv_anim_object);
ivObjectAnim.setBackgroundResource(R.mipmap.ic_launcher);
ivObjectAnim.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ObjectAnimator.ofFloat(v, "rotationX", 0.0f, 360.0f)
                .setDuration(500)
                .start();
    }
});

ImageView ivObjectUpdateAnim = (ImageView) view.findViewById(R.id.iv_anim_object_update);
ivObjectUpdateAnim.setBackgroundResource(R.mipmap.ic_launcher);
ivObjectUpdateAnim.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(final View v) {
        boolean result = new Random().nextInt(10) % 2 == 0;
        if (result) {
            // 推荐使用这种
            PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);
            ObjectAnimator.ofPropertyValuesHolder(v, pvhAlpha, pvhX, pvhY)
                    .setDuration(500)
                    .start();
        } else {
            final ObjectAnimator anim = ObjectAnimator
                    .ofFloat(v, "kylingo", 0.0f, 1.0f)
                    .setDuration(500);
            anim.start();
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    v.setAlpha(value);
                    v.setScaleX(value);
                    v.setScaleY(value);
                }
            });
        }
    }
});
```

ObjectAnimatory用法
- 提供了ofInt、ofFloat、ofObject，这几个方法都是设置动画作用的元素、作用的属性、动画开始、结束、以及中间的任意个属性值。
当对于属性值，只设置一个的时候，会认为当然对象该属性的值为开始（getPropName反射获取），然后设置的值为终点。如果设置两个，则一个为开始、一个为结束~~~
动画更新的过程中，会不断调用setPropName更新元素的属性，所有使用ObjectAnimator更新某个属性，必须得有getter（设置一个属性值的时候）和setter方法~

- 如果你操作对象的该属性方法里面，比如上例的setRotationX如果内部没有调用view的重绘，则你需要自己按照下面方式手动调用。
```java
anim.addUpdateListener(new AnimatorUpdateListener()  
        {  
            @Override  
            public void onAnimationUpdate(ValueAnimator animation)  
            {  
//              view.postInvalidate();  
//              view.invalidate();  
            }  
        });  
```

## ValueAnimator
ObjectAnimator的区别之处：ValueAnimator并没有在属性上做操作，需要操作的对象的属性一定要有getter和setter方法，你可以自己根据当前动画的计算值，来操作任何属性。

```java
// 简单的位移
final ImageView ivValueAnim = (ImageView) view.findViewById(R.id.iv_anim_value);
ivValueAnim.setBackgroundResource(R.mipmap.ic_launcher);
ValueAnimator animator = ValueAnimator.ofFloat(0, 400);
animator.setTarget(ivValueAnim);
animator.setDuration(500).start();
animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        ivValueAnim.setTranslationX((Float) animation.getAnimatedValue());
    }
});

// 抛物线
final ImageView ivValuePlusAnim = (ImageView) view.findViewById(R.id.iv_anim_value_plus);
ivValuePlusAnim.setBackgroundResource(R.mipmap.ic_launcher);
ValueAnimator valueAnimator = new ValueAnimator();
valueAnimator.setDuration(DURATION_ANIM);
valueAnimator.setObjectValues(new PointF(0, 0));
valueAnimator.setInterpolator(new LinearInterpolator());
valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        PointF pointF = new PointF();
        pointF.x = 200 * fraction * 3;
        pointF.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
        return pointF;
    }
});

valueAnimator.start();
valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        PointF pointF = (PointF) animation.getAnimatedValue();
        ivValuePlusAnim.setX(pointF.x);
        ivValuePlusAnim.setY(pointF.y);
    }
});

// Just set animation end listener
valueAnimator.addListener(new AnimatorListenerAdapter() {
    @Override
    public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
    }
});
```

## 参考文献
- http://blog.csdn.net/lmj623565791/article/details/38067475
