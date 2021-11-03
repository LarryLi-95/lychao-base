package com.lycaho.basecommon.utils;

import org.slf4j.Logger;

import java.util.Objects;

/**
 * @author lychao
 */
public class LogUtil {

    private LogUtil() {
    }

    private static final String NULL_OBJ = "object is null!! 空对象！";
    private static Logger log = null;

    public static <T> void debug(String msg, T t, Logger logger) {
        log = logger;
        if (Objects.isNull(t)) {
            log.debug(msg, NULL_OBJ);
        } else {
            log.debug(msg, JsonUtil.toJson(t));
        }
    }

    public static void debug(String msg, Logger logger, Object... obj) {
        log = logger;
        if (Objects.isNull(obj) || obj.length == 0) {
            log.debug(msg, NULL_OBJ);
        } else {
            log.debug(msg, obj);
        }
    }

    public static <T> void info(String msg, T t, Logger logger) {
        log = logger;
        if (Objects.isNull(t)) {
            log.info(msg, NULL_OBJ);
        } else {
            log.info(msg, JsonUtil.toJson(t));
        }
    }

    public static void info(String msg, Logger logger, Object... obj) {
        log = logger;
        if (Objects.isNull(obj) || obj.length == 0) {
            log.info(msg, NULL_OBJ);
        } else {
            log.info(msg, obj);
        }
    }

    public static <T> void error(String msg, T t, Logger logger) {
        log = logger;
        if (Objects.isNull(t)) {
            log.error(msg, NULL_OBJ);
        } else {
            log.error(msg, JsonUtil.toJson(t));
        }
    }

    public static void error(String msg, Logger logger, Object... obj) {
        log = logger;
        log.error(msg, obj);
    }

}