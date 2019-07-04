package com.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/7/4
 * Time: 13:28
 * Desc:
 */
public class LoggerUtils {

    private static final Logger log = LoggerFactory.getLogger(LoggerUtils.class);

    public static Logger getLogger(){
        return log;
    }
}
