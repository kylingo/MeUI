package com.me.ui.domain.config;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 全局常量
 *
 * @author tangqi
 * @date 2015/10/14 14:04
 */
public class Constants {

    /**
     * 知乎日报列表
     */
    public static final String ZHIHU_DAILY_NEWS = "http://news.at.zhihu.com/api/4/news/before/";

    /**
     * 知乎日报内容
     */
    public static final String ZHIHU_DAILY_NEWS_CONTENT = "http://daily.zhihu.com/story/";

    /**
     * 首页显示日期个数
     */
    public static final int NUM_DAILY = 7;

    /**
     * 格式化日期(yyyyMMdd)
     */
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);

    /**
     * 格式化日期（1月1日）
     */
    public static final SimpleDateFormat mmddDateFormat = new SimpleDateFormat("MM月dd日", Locale.US);
}
