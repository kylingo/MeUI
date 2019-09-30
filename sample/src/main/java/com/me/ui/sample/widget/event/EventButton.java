package com.me.ui.sample.widget.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 用例子总结一下onInterceptTouchEvent和onTouchEvent的调用顺序：
 * 假设最高层View叫OuterLayout，中间层View叫InnerLayout，最底层View叫MyVIew。调用顺序是这样的（假设各个函数返回的都是false）
 * OuterLayout.onInterceptTouchEvent->InnerLayout.onInterceptTouchEvent
 * ->MyView.onTouchEvent->InnerLayout.onTouchEvent->OuterLayout.onTouchEvent
 *
 * @author kylingo
 * @since 2019/09/30 11:44
 */
public class EventButton extends android.support.v7.widget.AppCompatButton {

    public EventButton(Context context) {
        super(context);
    }

    public EventButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onTouchEvent(event);
    }
}
