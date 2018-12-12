package com.me.ui.sample.widget.basic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.me.ui.library.sample.SampleFragment;
import com.me.ui.sample.R;
import com.me.ui.util.SizeUtils;

import java.util.List;

/**
 * 全站消息
 *
 * @author kylingo on 18/4/11
 */
public class GlobalMessageFragment extends SampleFragment<String> {

    // 消息默认展示时长
    private static final int TIME_SHOW_MESSAGE = 4000;
    // 动画默认展示时长
    private static final int TIME_SHOW_ANIM = 300;

    // 距离状态栏高度
    protected int mMarginTop;

    // 消息视图高度
    protected int mViewHeight;

    @Override
    protected void initView(View view) {
        super.initView(view);

        mMarginTop = SizeUtils.dp2px(0);
        mViewHeight = SizeUtils.dp2px(50);
    }

    @Override
    protected void addItems(List<String> items) {
        items.add("Show");
        items.add("Hide");
    }

    @Override
    protected void onClickItem(String item) {
        switch (item) {
            case "Show":
                showMessageView();
                break;

            case "Hide":
                hideMessageView();
                break;
        }
    }

    private void showMessageView() {
        if (getActivity() != null) {
            Window window = getActivity().getWindow();
            if (window != null) {
                try {
                    FrameLayout decorView = (FrameLayout) window.getDecorView();
                    removeMessageView(decorView);

                    View messageView = getMessageView();
                    decorView.addView(messageView);
                    decorView.setTag(messageView);

                    showAppearAnim(messageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private View getMessageView() {
        LinearLayout messageLayout = (LinearLayout) LayoutInflater.from(getActivity())
                .inflate(R.layout.item_global_message, null);
        messageLayout.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(50)));
        return messageLayout;
    }

    private void hideMessageView() {
        if (getActivity() != null) {
            Window window = getActivity().getWindow();
            if (window != null) {
                try {
                    FrameLayout decorView = (FrameLayout) window.getDecorView();
                    removeMessageView(decorView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void removeMessageView(FrameLayout decorView) {
        View lastMessageView = (View) decorView.getTag();
        if (lastMessageView != null) {
            showDisappearAnim(lastMessageView, decorView);
        }
    }

    /**
     * 展示出现动画
     */
    public void showAppearAnim(View target) {
        // 视图没加载时，无法获取高度，暂时估算为70dp
        int translationY = -(mMarginTop + SizeUtils.dp2px(70));

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(target, "translationY", translationY, 0);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(target, "alpha", 0.0f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(TIME_SHOW_ANIM);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
    }

    /**
     * 展示消失动画
     */
    public void showDisappearAnim(final View target, final FrameLayout decorView) {
        int translationY = -(mMarginTop + mViewHeight);

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(target, "translationY", 0, translationY);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(target, "alpha", 1.0f, 0.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(TIME_SHOW_ANIM);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                decorView.removeView(target);
                decorView.setTag(null);
            }
        });
    }
}
