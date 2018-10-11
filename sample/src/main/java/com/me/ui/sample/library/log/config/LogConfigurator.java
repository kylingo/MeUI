//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.me.ui.sample.library.log.config;

import android.util.Log;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.helpers.LogLog;

import java.io.IOException;

public class LogConfigurator {
    private Level rootLevel;
    private String filePattern;
    private String logCatPattern;
    private String fileName;
    private int maxBackupSize;
    private long maxFileSize;
    private boolean immediateFlush;
    private boolean useLogCatAppender;
    private boolean useFileAppender;
    private boolean resetConfiguration;
    private boolean internalDebugging;
    private boolean useRootLogger;

    //public static final String LOG_FILE_PATTERN = "%d - [%p::%c::%C] - %m%n";
    public static final String LOG_FILE_PATTERN_SIGNEL = "%d [%p]%c: %m%n";
    public static final String LOG_FILE_PATTERN_MULTI = "%d: %m%n";

    private LogConfigurator() {
        this.rootLevel = Level.DEBUG;
        this.filePattern = LOG_FILE_PATTERN_SIGNEL;
        this.logCatPattern = "%m%n";
        this.fileName = "meui.log";
        this.maxBackupSize = 7;
//        this.maxFileSize = 524288L; //512K
        this.maxFileSize = 1024 * 1024L; //1M
        this.immediateFlush = true;
        this.useLogCatAppender = true;
        this.useFileAppender = true;
        this.resetConfiguration = true;
        this.internalDebugging = false;
        this.useRootLogger = true;
    }

    public LogConfigurator(String fileName) {
        this();
        this.setFileName(fileName);
    }

    public LogConfigurator(String fileName, Level rootLevel) {
        this(fileName);
        this.setRootLevel(rootLevel);
    }

    public LogConfigurator(String fileName, Level rootLevel, String filePattern) {
        this(fileName, rootLevel);
        this.setFilePattern(filePattern);
    }

    public LogConfigurator(String fileName, int maxBackupSize, long maxFileSize, String filePattern, Level rootLevel) {
        this(fileName, rootLevel, filePattern);
        this.setMaxBackupSize(maxBackupSize);
        this.setMaxFileSize(maxFileSize);
    }

    /**
     * config root logger
     */
    public void configure() {
        Logger root = Logger.getRootLogger();

        configure(root);
    }

    /**
     * config logger of root or child
     *
     * @param logger: child logger
     */
    public void configure(Logger logger) {
        if (this.isResetConfiguration()) {
            LogManager.getLoggerRepository().resetConfiguration();
        }

        LogLog.setInternalDebugging(this.isInternalDebugging());
        boolean fileAppend = true;
        if (this.isUseFileAppender()) {
            fileAppend = this.configureFileAppender(logger);
        }
        if (fileAppend == true) {
            if (this.isUseLogCatAppender()) {
                this.configureLogCatAppender(logger);
            }

            logger.setLevel(this.getRootLevel());
        }
    }

    public void setLevel(String loggerName, Level level) {
        Logger.getLogger(loggerName).setLevel(level);
    }

    private boolean configureFileAppender(Logger logger) {
        PatternLayout fileLayout = new PatternLayout(this.getFilePattern());
        RollingFileAppender rollingFileAppender = null;
        try {
            rollingFileAppender = new RollingFileAppender(fileLayout, this.getFileName());
        } catch (IOException var5) {
            var5.printStackTrace();
            //throw new RuntimeException("Exception configuring log system", var5);
            Log.d("DLog", "log4j configureFileAppender Exception configuring log system!!!", var5);
        }

        if (rollingFileAppender != null) {
            rollingFileAppender.setMaxBackupIndex(this.getMaxBackupSize());
            rollingFileAppender.setMaximumFileSize(this.getMaxFileSize());
            rollingFileAppender.setImmediateFlush(this.isImmediateFlush());

            rollingFileAppender.setName(logger.getName());
            if (!this.isUseRootLogger()) {
                logger.setAdditivity(false);
            } else {
                logger.setAdditivity(true);
            }
            logger.addAppender(rollingFileAppender);
            return true;
        }
        return false;
    }

    private void configureLogCatAppender(Logger logger) {
        PatternLayout logCatLayout = new PatternLayout(this.getLogCatPattern());
        LogCatAppender logCatAppender = new LogCatAppender(logCatLayout);
        logger.addAppender(logCatAppender);
    }

    public Level getRootLevel() {
        return this.rootLevel;
    }

    public void setRootLevel(Level level) {
        this.rootLevel = level;
    }

    public String getFilePattern() {
        return this.filePattern;
    }

    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    public String getLogCatPattern() {
        return this.logCatPattern;
    }

    public void setLogCatPattern(String logCatPattern) {
        this.logCatPattern = logCatPattern;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getMaxBackupSize() {
        return this.maxBackupSize;
    }

    public void setMaxBackupSize(int maxBackupSize) {
        this.maxBackupSize = maxBackupSize;
    }

    public long getMaxFileSize() {
        return this.maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public boolean isImmediateFlush() {
        return this.immediateFlush;
    }

    public void setImmediateFlush(boolean immediateFlush) {
        this.immediateFlush = immediateFlush;
    }

    public boolean isUseFileAppender() {
        return this.useFileAppender;
    }

    public void setUseFileAppender(boolean useFileAppender) {
        this.useFileAppender = useFileAppender;
    }

    public boolean isUseLogCatAppender() {
        return this.useLogCatAppender;
    }

    public void setUseLogCatAppender(boolean useLogCatAppender) {
        this.useLogCatAppender = useLogCatAppender;
    }

    public void setResetConfiguration(boolean resetConfiguration) {
        this.resetConfiguration = resetConfiguration;
    }

    public boolean isResetConfiguration() {
        return this.resetConfiguration;
    }

    public void setInternalDebugging(boolean internalDebugging) {
        this.internalDebugging = internalDebugging;
    }

    public boolean isInternalDebugging() {
        return this.internalDebugging;
    }

    public boolean isUseRootLogger() {
        return this.useRootLogger;
    }

    public void setUseRootLogger(boolean useRootLogger) {
        this.useRootLogger = useRootLogger;
    }
}
