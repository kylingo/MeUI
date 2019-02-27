package com.me.ui.sample.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.me.ui.sample.R;

/**
 * @author kylingo
 * @since 2018/11/26 12:19
 */
public abstract class BaseDialogFragment extends AppCompatDialogFragment {

    protected abstract int getContentViewId();

    protected abstract void initView(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, getStyleTheme());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getContentViewId(), container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getDialog() == null) {
            setShowsDialog(false);
        }

        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                if (getGravity() != -1) {
                    window.setGravity(getGravity());
                }
//                window.setWindowAnimations(getWindowAnimations());
                window.setLayout(getWindowWidth(), getWindowHeight());
                window.setDimAmount(getDimAmount());
            }
        }
    }

//    protected int getWindowAnimations() {
//        return R.style.live_LibraryAnimFade;
//    }

    protected int getWindowWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    protected int getWindowHeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    protected float getDimAmount() {
        return 0.5f;
    }

    /**
     * @see android.view.Gravity
     */
    protected int getGravity() {
        return -1;
    }

    protected int getStyleTheme() {
        return R.style.BaseDialogTheme;
    }
}
