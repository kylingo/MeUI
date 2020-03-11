package com.me.ui.sample.widget.list;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author tangqi
 * @since 2020/03/11 10:14
 */
public class GradualLayout extends FrameLayout {

    Paint paint = new Paint();

    public GradualLayout(@NonNull Context context) {
        this(context, null);
    }

    public GradualLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradualLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                         @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        paint.setAlpha(0xffffffff);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        paint.setShader(
                new LinearGradient(
                        0, 0, getWidth(), getHeight(),
                        0xffffffff, 0x00000000,
                        Shader.TileMode.CLAMP));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }
}
