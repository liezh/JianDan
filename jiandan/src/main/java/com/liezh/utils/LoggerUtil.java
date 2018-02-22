package com.liezh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2018/2/17.
 */
public class LoggerUtil {

    private static Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    public static void info(String loggerFormat, Object... params) {
        logger.info(loggerFormat, params);
    }

    public static void info(String loggerFormat, Throwable ex) {
        logger.info(loggerFormat, ex);
    }

    public static void info(String loggerFormat) {
        logger.info(loggerFormat);
    }



    public static void debug(String loggerFormat, Object... params) {
        logger.debug(loggerFormat, params);
    }

    public static void debug(String loggerFormat, Throwable ex) {
        logger.debug(loggerFormat, ex);
    }

    public static void debug(String loggerFormat) {
        logger.debug(loggerFormat);
    }



    public static void warn(String loggerFormat, Object... params) {
        logger.warn(loggerFormat, params);
    }

    public static void warn(String loggerFormat, Throwable ex) {
        logger.warn(loggerFormat, ex);
    }

    public static void warn(String loggerFormat) {
        logger.warn(loggerFormat);
    }



    public static void error(String loggerFormat, Object... params) {
        logger.error(loggerFormat, params);
    }

    public static void error(String loggerFormat, Throwable ex) {
        logger.error(loggerFormat, ex);
    }

    public static void error(String loggerFormat) {
        logger.error(loggerFormat);
    }


}
