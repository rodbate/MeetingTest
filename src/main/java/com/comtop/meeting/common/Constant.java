package com.comtop.meeting.common;


import org.slf4j.Logger;

/**
 * 常量类
 */

public class Constant {

    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String DISPLAY_NONE = ";display:none";

    public static final int[] HOURS_ARRAY = new int[]{9,10,11,12,14,15,16,17,18};


    public static final Logger LOGGER_FILE = LogUtil.getLogger("meeting");


    public static final Logger LOGGER_STDOUT = LogUtil.getLogger("STDOUT");
}
