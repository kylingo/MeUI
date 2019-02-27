package com.me.ui.sample.thirdparty.test;

import android.view.View;
import android.widget.TextView;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;
import com.me.ui.sample.library.util.FragmentUtils;

/**
 * @author kylingo
 * @since 2019/02/26 09:50
 */
public class InputCommentFragment extends BaseFragment {

    public static final String TAG = "InputCommentFragment";

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_input_comment;
    }

    @Override
    protected void initView(View view) {
        TextView sendComment = view.findViewById(R.id.tv_send_comment);
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final InputDialogFragment inputDialogFragment = new InputDialogFragment();
                FragmentUtils.show(getActivity(), inputDialogFragment, InputDialogFragment.TAG);
            }
        });

        // 点击空白区域，关闭页面
        View spaceView = view.findViewById(R.id.view_input_comment_space);
        spaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.remove(getActivity(), InputCommentFragment.this);
            }
        });
    }
}
