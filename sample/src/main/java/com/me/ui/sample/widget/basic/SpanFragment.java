package com.me.ui.sample.widget.basic;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.me.ui.sample.base.BaseFragment;
import com.me.ui.sample.R;

/**
 * @author kylingo on 18/6/21
 */
public class SpanFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_span;
    }

    @Override
    protected void initView(View view) {
        showSpan(view);
        showBulletSpan(view);
        showCustomBulletSpan(view);
        showChangLine(view);
    }

    private void showSpan(View view) {
        TextView textView = view.findViewById(R.id.tv_span_text);
        SpannableString spannable = new SpannableString("[img]Text style scale line");

        Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_launcher);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannable.setSpan(imageSpan, 0, 5, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
        spannable.setSpan(colorSpan, 5, 10, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);
        spannable.setSpan(styleSpan, 10, 16, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        ScaleXSpan scaleXSpan = new ScaleXSpan(1.5f);
        spannable.setSpan(scaleXSpan, 16, 22, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannable.setSpan(strikethroughSpan, 22, 26, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        textView.setText(spannable);
    }

    private void showBulletSpan(View view) {
        TextView textView = view.findViewById(R.id.tv_bullet_span);
        SpannableString spannable = new SpannableString("My text \nbullet one\nbullet two");
        spannable.setSpan(new BulletSpan(), 9, 18,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new BulletSpan(), 20, spannable.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
    }

    private void showCustomBulletSpan(View view) {
        TextView textView = view.findViewById(R.id.tv_bullet_span_custom);
        SpannableString spannable = new SpannableString("My text \nbullet one\nbullet two");
        spannable.setSpan(new BulletSpan(6, Color.RED), 9, 18,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new BulletSpan(6, Color.RED), 20, spannable.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
    }

    private void showChangLine(View view) {
        TextView textView = view.findViewById(R.id.tv_change_line);
        textView.setText("1、新增艺人社区玩法:大众评审、竞演 \\n 2、bug修复及体验优化");
    }
}
