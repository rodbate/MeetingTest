package com.comtop.meeting.common;


import com.comtop.meeting.entity.Meeting;
import com.comtop.meeting.vo.PageObject;
import com.comtop.meeting.vo.TableTDVO;
import com.comtop.meeting.vo.MeetingRoomVO;
import org.springframework.data.domain.Page;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import static com.comtop.meeting.common.Constant.*;
import static com.comtop.meeting.common.ReflectionUtil.*;


/**
 * 通用工具类
 */
@SuppressWarnings("unchecked")
public class CommonUtil {


    /**
     * 构建页面返回对象
     * @param page page 对象
     * @param src 原对象类
     * @param dest 目标对象
     * @return PageObject
     */

    public static PageObject buildPageObject(Page page, Class src, Class dest){
        PageObject object = new PageObject();

        object.setTotalPage(page.getTotalPages());
        object.setPageNo(page.getNumber() + 1);
        object.setPageSize(page.getSize());

        if (src != null) {

            try {
                Constructor constructor = dest.getConstructor(Meeting.class);
                List content = page.getContent();
                List newList = new ArrayList();

                for (Object o : content) {
                    newList.add(constructor.newInstance(o));
                }

                object.setContent(newList);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {

            object.setContent(page.getContent());
        }

        return object;
    }

    /**
     * 转化对象
     * @param obj 所要转化的对象
     * @param target 需要转化目标Class对象
     * @return Object
     */
    public static Object convertObject(Object obj, Class target){

        Object o = null;

        try {
            Constructor constructor = target.getConstructor(obj.getClass());
            o = constructor.newInstance(obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return o;
    }

    public static Object buildPageObject(Page page){
        return buildPageObject(page, null, null);
    }

    /**
     * 字符串不为空判断
     * @param str string
     * @return true if str is not null or false else
     */
    public static boolean isNotNull(String str){
        return str != null && !"".equals(str.trim());
    }


    /**
     * unix时间戳转化为相对应格式的字符串
     * @param unixTime unix时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String unixTimeToString(long unixTime, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(unixTime * 1000);
        return sdf.format(date);
    }

    /**
     * 获取某天 00:00 23:59 时间字符串
     * @param unixTime 某天某时的时间戳
     * @return Map<String, Long>
     */
    public static Map<String, Long> getStartEndUnixTimeByDay(long unixTime){
        Map<String, Long> map = new HashMap<String, Long>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String d = unixTimeToString(unixTime, "yyyy-MM-dd");
        String start = d + " 00:00";
        String end = d + " 23:59";
        try {
            long startUnix = sdf.parse(start).getTime() / 1000;
            long endUnix = sdf.parse(end).getTime() / 1000;
            map.put("start", startUnix);
            map.put("end", endUnix);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Object getValidEndDates(){

        return null;
    }


    /**
     * 设置某个会议室的会议记录信息
     * @param roomVO 会议室对象
     * @param meeting 会议记录对象
     * @param startHour 开始时间 小时
     * @param endHour 结束时间 小时
     */
    public static void setTableTDForMeetingRoom(MeetingRoomVO roomVO, Meeting meeting, int startHour, int endHour){

        int meetingId = meeting.getId();
        String host = meeting.getHost().getName();

        if (endHour == 0) {

            for (int i = 0; i < HOURS_ARRAY.length; i++) {
                TableTDVO v;
                if (HOURS_ARRAY[i] == startHour) {
                    v = new TableTDVO(meetingId, 1, 1, "", host);
                    setField(MeetingRoomVO.class, "h" + HOURS_ARRAY[i], roomVO, v);
                }

            }
        } else {

            if (endHour - startHour == 2) {

                for (int i = 0; i < HOURS_ARRAY.length; i++) {
                    TableTDVO v;
                    if (HOURS_ARRAY[i] == startHour) {
                        v = new TableTDVO(meetingId, 1, 2, "", host);
                        setField(MeetingRoomVO.class, "h" + HOURS_ARRAY[i], roomVO, v);
                    } else if (HOURS_ARRAY[i] == startHour + 1) {
                        v = new TableTDVO(meetingId, 1, 1, DISPLAY_NONE, host);
                        setField(MeetingRoomVO.class, "h" + HOURS_ARRAY[i], roomVO, v);
                    }

                }
            }

            if (endHour - startHour == 3) {

                for (int i = 0; i < HOURS_ARRAY.length; i++) {
                    TableTDVO v;
                    if (HOURS_ARRAY[i] == startHour) {
                        v = new TableTDVO(meetingId, 1, 3, "", host);
                        setField(MeetingRoomVO.class, "h" + HOURS_ARRAY[i], roomVO, v);
                    } else if (HOURS_ARRAY[i] == startHour + 1) {
                        v = new TableTDVO(meetingId, 1, 1, DISPLAY_NONE, host);
                        setField(MeetingRoomVO.class, "h" + HOURS_ARRAY[i], roomVO, v);
                    } else if (HOURS_ARRAY[i] == startHour + 2) {
                        v = new TableTDVO(meetingId, 1, 1, DISPLAY_NONE, host);
                        setField(MeetingRoomVO.class, "h" + HOURS_ARRAY[i], roomVO, v);
                    }

                }
            }

            if (endHour - startHour == 4) {

                for (int i = 0; i < HOURS_ARRAY.length; i++) {
                    TableTDVO v;
                    if (HOURS_ARRAY[i] == startHour) {
                        v = new TableTDVO(meetingId, 1, 4, "", host);
                        setField(MeetingRoomVO.class, "h" + HOURS_ARRAY[i], roomVO, v);
                    } else if (HOURS_ARRAY[i] == startHour + 1) {
                        v = new TableTDVO(meetingId, 1, 1, DISPLAY_NONE, host);
                        setField(MeetingRoomVO.class, "h" + HOURS_ARRAY[i], roomVO, v);
                    } else if (HOURS_ARRAY[i] == startHour + 2) {
                        v = new TableTDVO(meetingId, 1, 1, DISPLAY_NONE, host);
                        setField(MeetingRoomVO.class, "h" + HOURS_ARRAY[i], roomVO, v);
                    } else if (HOURS_ARRAY[i] == startHour + 3) {
                        v = new TableTDVO(meetingId, 1, 1, DISPLAY_NONE, host);
                        setField(MeetingRoomVO.class, "h" + HOURS_ARRAY[i], roomVO, v);
                    }

                }
            }

        }

    }

    /**
     *
     * 设置某个会议室的会议记录信息
     * @param roomVO 会议室对象
     * @param meeting 会议记录对象
     * @param startHour 开始时间 小时
     */
    public static void setTableTDForMeetingRoom(MeetingRoomVO roomVO, Meeting meeting, int startHour) {
        setTableTDForMeetingRoom(roomVO, meeting, startHour, 0);
    }


    /**
     * 时间字符串赚unix时间戳
     * @param time 时间字符串
     * @param format 时间格式
     * @return unix时间戳
     */
    public static long stringToUnixTimeStamp(String time, String format){

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            return sdf.parse(time).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * list 转 set集合
     * @param src list 集合
     * @return set 集合
     */
    public static Set listToSet(List src){
        Set set = new HashSet();

        for (Object o : src) {
            set.add(o);
        }

        return set;
    }

    /**
     * 打印异常信息
     * @param throwable 异常
     * @return 异常信息
     */
    public static String printExceptionStack(Throwable throwable){

        StringWriter sw = new StringWriter();

        PrintWriter writer = new PrintWriter(sw, true);
        throwable.printStackTrace(writer);

        return sw.toString();
    }

}
