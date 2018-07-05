package com.me.ui.sample.library.basic;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kylingo.annotation.BindView;
import com.kylingo.annotation.OnClick;
import com.kylingo.annotation.ViewFinder;
import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;

/**
 * @author kylingo on 18/7/6
 */
public class AnnotationFragment extends BaseFragment {

    @BindView(R.id.btn_annotation)
    Button btnAnnotation;

    @BindView(R.id.tv_annotation)
    TextView tvAnnotation;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_annotation;
    }

    @Override
    protected void initView(View view) {
        ViewFinder.inject(this, view);
    }

    @OnClick(R.id.btn_annotation)
    public void onButtonClick() {
        Toast.makeText(getActivity(), "onButtonClick", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_annotation)
    public void onTextClick() {
        Toast.makeText(getActivity(), "onTextClick", Toast.LENGTH_SHORT).show();
    }
}
