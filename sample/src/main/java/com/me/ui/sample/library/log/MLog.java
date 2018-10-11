package com.me.ui.sample.library.log;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.me.ui.sample.BuildConfig;
import com.me.ui.sample.library.config.MePath;
import com.me.ui.sample.library.log.config.LogConfigurator;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @author kylingo on 18/10/11
 */
public class MLog {

    /**
     * 远程开关默认值，便于默认打开上报日志
     */
    private static boolean LOG_REMOTE_DEFAULT = true;

    /**
     * 日志本地设置开关
     */
    private static final boolean LOG_LOCAL = BuildConfig.DEBUG || getLogProp();

    /**
     * 日志远程设置开关（可动态变化）
     */
    private static boolean LOG_REMOTE = LOG_REMOTE_DEFAULT;

    /**
     * 方便测试时打开日志快关
     */
    private static final String LOG_PROP = "ro.log.meui";

    private static LogConfigurator sLogConfigurator = null;

    private static Level getLevel() {
        if (LOG_LOCAL == true) {
            return Level.DEBUG;
        } else if (LOG_REMOTE == true) {
            return Level.INFO;
        } else {
            return Level.OFF;
        }
    }

    public static void i(String tag, String message) {
        log(tag, message, Level.INFO, null);
    }

    public static void d(String tag, String message) {
        if (Level.DEBUG.toInt() >= getLevel().toInt()) {
            Log.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (Level.DEBUG.toInt() >= getLevel().toInt()) {
            Log.e(tag, message);
        }
    }

    private static void log(String tag, String message, Level level, Throwable t) {
        if (!TextUtils.isEmpty(tag)) {
            final Logger logger = Logger.getLogger(tag);
            final int local = getLevel().toInt();
            if (local <= level.toInt() && logger != null) {
                config(logger);
                if (LOG_LOCAL == true) {
                    final String value = addMessage(message, 5);
                    if (!TextUtils.isEmpty(value)) {
                        message = value;
                    }
                }
                switch (level.toInt()) {
                    case 5000:
                        if (t == null) {
                            logger.trace(message);
                        } else {
                            logger.trace(message, t);
                        }
                        break;
                    case 10000:
                        if (t == null) {
                            logger.debug(message);
                        } else {
                            logger.debug(message, t);
                        }
                        break;
                    case 20000:
                        if (t == null) {
                            logger.info(message);
                        } else {
                            logger.info(message, t);
                        }
                        break;
                    case 30000:
                        if (t == null) {
                            logger.warn(message);
                        } else {
                            logger.warn(message, t);
                        }
                        break;
                    case 40000:
                        if (t == null) {
                            logger.error(message);
                        } else {
                            logger.error(message, t);
                        }
                        break;
                    case 50000:
                        if (t == null) {
                            logger.fatal(message);
                        } else {
                            logger.fatal(message, t);
                        }
                        break;
                }
            }
        }
    }

    public static void config() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final File defaultLog = new File(MePath.PATH_LOG, "meui.log");
                final String pattern = LogConfigurator.LOG_FILE_PATTERN_MULTI;
                sLogConfigurator = new LogConfigurator(defaultLog.getAbsolutePath(), getLevel(), pattern);
                sLogConfigurator.setResetConfiguration(true); //清除掉旧配置
                sLogConfigurator.setUseRootLogger(false);
                sLogConfigurator.configure();
            }
        }).start();
    }

    private static void config(Logger logger) {
        final String tag = logger.getName();
        final Appender appender = logger.getAppender(tag);
        if (sLogConfigurator != null && appender == null) {
            final File defaultLog = new File(MePath.PATH_LOG, tag + ".log");
            sLogConfigurator.setFileName(defaultLog.getAbsolutePath());
            sLogConfigurator.setResetConfiguration(false);
            sLogConfigurator.configure(logger);
        }
    }

    private static String addMessage(String msg, int deep) {
        Thread currentThread = Thread.currentThread();
        StackTraceElement[] stackTraceElements = currentThread.getStackTrace();
        if (stackTraceElements.length > deep) {
            StackTraceElement caller = stackTraceElements[deep];

            if (caller != null) {
                String fileName = caller.getFileName();
                // 去掉文件名后缀
                int pos = fileName == null ? -1 : fileName.lastIndexOf('.');
                if (pos >= 0) {
                    String fileBaseName = fileName.substring(0, pos);
                    try {
                        return fileBaseName + "." + caller.getMethodName() + "(): " + msg;
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return msg;
    }

    private static boolean getLogProp() {
        int value;
        try {
            @SuppressLint("PrivateApi") Class<?> classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("getInt", String.class, int.class);
            value = (int) getMethod.invoke(classType, new Object[]{LOG_PROP, 0});
        } catch (Exception e) {
            e.printStackTrace();
            value = 0;
        }
        return value > 0;
    }
}
