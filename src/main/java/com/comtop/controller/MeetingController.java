package com.comtop.controller;

import com.comtop.dao.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jiangsongsong on 2016/7/7.
 */

@RestController
@RequestMapping(value = "/api")
public class MeetingController {


    public MeetingController() {
        System.out.println(" ================== MeetingController initializing ......................");
    }

    @Autowired
    MeetingRepository repository;

    @RequestMapping(value = "/meeting", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object list(){
        return repository.findAll();
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public Object hello(HttpServletRequest request, HttpServletResponse response){
        return "hello";
    }
}
