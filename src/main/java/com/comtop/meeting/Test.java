package com.comtop.meeting;

import com.comtop.meeting.dao.MeetingDao;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class Test {


    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Test.class);

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");


        MeetingDao bean = context.getBean(MeetingDao.class);
        Object o = bean.getMeetingsOnConditions("测试", null, 5, 1, 1, "id");

        System.out.println(new Gson().toJson(o));



    }
}
