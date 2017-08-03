package com.me.ui.sample.widget.custom;

import android.view.View;
import android.widget.Button;

import com.me.ui.sample.BaseFragment;
import com.me.ui.sample.R;
import com.me.ui.widget.custom.GraffitiView;

/**
 * @author tangqi on 17-8-3.
 */
public class GraffitiFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_graffiti;
    }

    @Override
    protected void initView(View view) {
        final GraffitiView graffitiView = (GraffitiView) view.findViewById(R.id.graffiti_view);

        Button btnClean = (Button) view.findViewById(R.id.btn_clean);
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graffitiView.clean();
            }
        });
    }
}
