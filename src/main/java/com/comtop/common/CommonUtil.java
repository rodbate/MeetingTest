package com.comtop.common;


import com.comtop.entity.Meeting;
import com.comtop.vo.MeetingRoomVO;
import com.comtop.vo.PageObject;
import com.comtop.vo.TableTDVO;
import org.springframework.data.domain.Page;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.comtop.common.Constant.*;
import static com.comtop.common.ReflectionUtil.*;

public class CommonUtil {


    public static Object buildPageObject(Page page, Class src, Class dest){
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


    public static Object convertObject(Object obj, Class src, Class target){

        Object o = null;

        try {
            Constructor constructor = target.getConstructor(src);
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

    public static boolean isNotNull(String str){
        return str != null && !"".equals(str.trim());
    }

    public static String unixTimeToString(long unixTime, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(unixTime * 1000);
        return sdf.format(date);
    }

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

    public static void setTableTDForMeetingRoom(MeetingRoomVO roomVO, Meeting meeting, int startHour, int endHour){

        int meetingId = meeting.getId();
        String host = meeting.getHost().getName();

        if (endHour == 0) {

            for (int i = 0; i < HOURS_ARRAY.length; i++) {
                TableTDVO v;
                if (HOURS_ARRAY[i] == startHour) {
                    v = new TableTDVO(meetingId, 1, 1, "", host);
                    setField(MeetingRoomVO.class, "h" + HOURS_ARRAY[i], roomVO, v);
                } else {
                    //v = new TableTDVO(0, 0, 1, "", "");
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
                    } else {
                        //v = new TableTDVO(0, 0, 1, "", "");
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
                    } else {
                        //v = new TableTDVO(0, 0, 1, "", "");
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
                    } else {
                       // v = new TableTDVO(0, 0, 1, "", "");
                    }

                }
            }

        }

    }


    public static void setTableTDForMeetingRoom(MeetingRoomVO roomVO, Meeting meeting, int startHour) {
        setTableTDForMeetingRoom(roomVO, meeting, startHour, 0);
    }

    public static long stringToUnixTimeStamp(String time, String format){

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            return sdf.parse(time).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static void main(String[] args) throws ParseException {
        //System.out.println(new Date().getTime()/1000);
        Map<String, Long> map = getStartEndUnixTimeByDay(1468224055);
        //System.out.println(map.get("start") + "  " + map.get("end"));
        System.out.println(unixTimeToString(1468292400, "yyyy-MM-dd HH:mm"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //System.out.println(sdf.parse("2018-3-8 17:00").getTime());

    }
}
