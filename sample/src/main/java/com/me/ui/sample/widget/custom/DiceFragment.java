package com.me.ui.sample.widget.custom;

import android.view.View;
import android.widget.TextView;

import com.me.ui.sample.R;
import com.me.ui.sample.base.BaseFragment;
import com.me.ui.widget.custom.dice.DiceView;

/**
 * @author kylingo on 18/6/29
 */
public class DiceFragment extends BaseFragment {

    private DiceView mDiceView;
    private TextView mDiceText;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_dice;
    }

    @Override
    protected void initView(View view) {
        mDiceView = view.findViewById(R.id.view_dice);
        mDiceView.setDuration(1800);

        mDiceText = view.findViewById(R.id.tv_dice_value);

        view.findViewById(R.id.btn_dice_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiceView.start();
                mDiceText.setText("");
            }
        });

        view.findViewById(R.id.btn_dice_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiceView.reset();
                mDiceText.setText("");
            }
        });

        mDiceView.setStateChangeListener(new DiceView.DiceStateChangeListener() {
            @Override
            public void onDiceAnimFinish(int value) {
                mDiceText.setText(String.valueOf(value));
            }

            @Override
            public void onDiceHide() {

            }
        });
    }
}
