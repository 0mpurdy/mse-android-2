package com.a0mpurdy.mse_core.log;

/**
 * @author Michael Purdy
 */
public enum LogLevel {

    CRITICAL(0, "[CRITICAL]"),
    HIGH(1, "[HIGH    ]"),
    LOW(2, "[LOW     ]"),
    INFO(3, "[INFO    ]"),
    DEBUG(4, "[DEBUG   ]"),
    TRACE(5, "[TRACE   ]");

    int value;
    String tag;

    LogLevel(int value, String tag) {
        this.tag = tag;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getTag() {
        return tag;
    }
}
