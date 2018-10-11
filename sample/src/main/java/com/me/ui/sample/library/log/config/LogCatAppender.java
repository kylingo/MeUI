package com.me.ui.sample.library.log.config;


import android.util.Log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class LogCatAppender extends AppenderSkeleton {
    protected Layout tagLayout;

    public LogCatAppender(Layout messageLayout, Layout tagLayout) {
        this.tagLayout = tagLayout;
        this.setLayout(messageLayout);
    }

    public LogCatAppender(Layout messageLayout) {
        this(messageLayout, new PatternLayout("%c"));
    }

    public LogCatAppender() {
        this(new PatternLayout("%m%n"));
    }

    protected void append(LoggingEvent le) {
        switch(le.getLevel().toInt()) {
            case 5000:
                if(le.getThrowableInformation() != null) {
                    Log.v(this.getTagLayout().format(le), this.getLayout().format(le), le.getThrowableInformation().getThrowable());
                } else {
                    Log.v(this.getTagLayout().format(le), this.getLayout().format(le));
                }
                break;
            case 10000:
                if(le.getThrowableInformation() != null) {
                    Log.d(this.getTagLayout().format(le), this.getLayout().format(le), le.getThrowableInformation().getThrowable());
                } else {
                    Log.d(this.getTagLayout().format(le), this.getLayout().format(le));
                }
                break;
            case 20000:
                if(le.getThrowableInformation() != null) {
                    Log.i(this.getTagLayout().format(le), this.getLayout().format(le), le.getThrowableInformation().getThrowable());
                } else {
                    Log.i(this.getTagLayout().format(le), this.getLayout().format(le));
                }
                break;
            case 30000:
                if(le.getThrowableInformation() != null) {
                    Log.w(this.getTagLayout().format(le), this.getLayout().format(le), le.getThrowableInformation().getThrowable());
                } else {
                    Log.w(this.getTagLayout().format(le), this.getLayout().format(le));
                }
                break;
            case 40000:
                if(le.getThrowableInformation() != null) {
                    Log.e(this.getTagLayout().format(le), this.getLayout().format(le), le.getThrowableInformation().getThrowable());
                } else {
                    Log.e(this.getTagLayout().format(le), this.getLayout().format(le));
                }
                break;
            case 50000:
                if(le.getThrowableInformation() != null) {
                    Log.wtf(this.getTagLayout().format(le), this.getLayout().format(le), le.getThrowableInformation().getThrowable());
                } else {
                    Log.wtf(this.getTagLayout().format(le), this.getLayout().format(le));
                }
        }

    }

    public void close() {
    }

    public boolean requiresLayout() {
        return true;
    }

    public Layout getTagLayout() {
        return this.tagLayout;
    }

    public void setTagLayout(Layout tagLayout) {
        this.tagLayout = tagLayout;
    }
}

