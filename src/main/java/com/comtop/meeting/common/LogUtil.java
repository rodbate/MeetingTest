package com.comtop.meeting.common;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.comtop.meeting.common.Constant.*;
import static com.comtop.meeting.common.CommonUtil.*;
/**
 * logback日志工具类
 */
public class LogUtil {


    private final static LoggerContext context;

    static {

        context = (LoggerContext) LoggerFactory.getILoggerFactory();

        JoranConfigurator configurator = new JoranConfigurator();

        configurator.setContext(context);

        context.reset();

        try {
            configurator.doConfigure(LogUtil.class.getResourceAsStream("/logback.xml"));
        } catch (JoranException e) {
            LOGGER_FILE.info(printExceptionStack(e));
        }

    }

    public static Logger getLogger(String name){
        return context.getLogger(name);
    }

}
