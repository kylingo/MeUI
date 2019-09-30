package com.me.ui.sample.widget.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @author tangqi on 17-6-26.
 */
public class CustomTextView extends TextView {
    private static String TAG = CustomTextView.class.getSimpleName();

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "dispatchTouchEvent:" + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent:" + event.getAction());
        return super.onTouchEvent(event);
    }
}
