package com.comtop.meeting.common;



import java.lang.reflect.Field;


import static com.comtop.meeting.common.CommonUtil.*;
import static com.comtop.meeting.common.Constant.*;


/**
 * 反射工具类
 */
public class ReflectionUtil {


    public static void setField(Class clazz, String fieldName, Object target, Object value){

        Field field = null;

        boolean accessible = false;

        try {
            field = clazz.getDeclaredField(fieldName);

            accessible = field.isAccessible();

            field.setAccessible(true);

            field.set(target, value);

        } catch (NoSuchFieldException e) {
            LOGGER_FILE.info(printExceptionStack(e));
        } catch (IllegalAccessException e) {
            LOGGER_FILE.info(printExceptionStack(e));
        } finally {

            if (field != null) {

                field.setAccessible(accessible);
            }

        }

    }
}
