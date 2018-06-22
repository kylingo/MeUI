package com.me.ui.sample.widget.custom;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.me.ui.sample.base.BaseFragment;
import com.me.ui.sample.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tangqi on 17-3-6.
 */
public class CustomBasicFragment extends BaseFragment {

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_custom_basic;
    }

    @Override
    protected void initView(View view) {
        TextView tvHtml = (TextView) view.findViewById(R.id.tv_html);
        String text = "深圳奥比中光科技有限公司成立于2013年，是一家集研发、生产、销售为一体的高科技企业。"
                + "<img src = \"R.mipmap.ic_launcher\" />";
        CharSequence html = Html.fromHtml(text, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable = getActivity().getDrawable(R.drawable.ic_launcher);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                }
                return drawable;
            }
        }, null);
        tvHtml.setText(html);


        String regString = "ad_link";
        text = "深圳奥比中光科技有限公司成立于2013年，是一家集研发、生产、销售为一体的高科技企业。" + regString;
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        Pattern pattern = Pattern.compile(regString);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            builder.setSpan(new ImageSpan(getActivity(), R.drawable.ic_launcher),
                    matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        TextView tvSpan = (TextView) view.findViewById(R.id.tv_span);
        tvSpan.setText(builder);
    }
}
