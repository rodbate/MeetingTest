package com.comtop.common;


import com.comtop.entity.Meeting;
import com.comtop.vo.PageObject;
import org.springframework.data.domain.Page;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public static void main(String[] args) throws ParseException {
        //System.out.println(new Date().getTime()/1000);
        Map<String, Long> map = getStartEndUnixTimeByDay(1468224055);
        //System.out.println(map.get("start") + "  " + map.get("end"));
        //System.out.println(unixTimeToString(1468224055, "HH"));
    }
}
