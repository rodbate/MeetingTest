package com.comtop.common;


import java.lang.reflect.Field;

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
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {

            if (field != null) {

                field.setAccessible(accessible);
            }

        }

    }
}
