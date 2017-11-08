package com.a0mpurdy.mse.log;

import android.util.Log;

import com.a0mpurdy.mse_core.log.ILogger;
import com.a0mpurdy.mse_core.log.LogLevel;
import com.a0mpurdy.mse_core.log.LogRow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mj_pu_000 on 28/09/2015.
 */
public class Logger implements ILogger {

    public static final String DEFAULT_LOG = "log.txt";

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    LogLevel logLevel;

    public Logger(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public synchronized void log(LogLevel logLevel, String message) {
        if (logLevel.getValue() <= this.logLevel.getValue()) {
            Date date = new Date();
            String tag = logLevel.getTag();
            Log.d(tag, String.format("%s - %s", dateFormat.format(date), message));
        }
    }

    public synchronized void log(LogRow logRow) {
        log(logRow.logLevel, logRow.message);
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void closeLog() {
    }

    @Override
    public void logException(Exception e) {
        log(LogLevel.HIGH, e.getMessage());
        e.printStackTrace();
    }

}
