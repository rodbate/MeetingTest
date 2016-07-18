package com.comtop.controller;

import com.comtop.dao.EmployeeRepository;
import com.comtop.service.MeetingService;
import com.comtop.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;


import static org.springframework.data.domain.Sort.Direction.*;

import static com.comtop.common.CommonUtil.*;

@RestController
@RequestMapping(value = "/api")
public class MeetingController {


    @Autowired
    EmployeeRepository employeeRepository;


    @Autowired
    MeetingService meetingService;

    @RequestMapping(value = "/meeting", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object list(@RequestParam(name = "meetingName", required = false) String meetingName,
                       @RequestParam(name = "hostName", required = false) String hostName,
                       @RequestParam(name = "pageSize") int pageSize,
                       @RequestParam(name = "pageNo") int pageNo,
                       @RequestParam(name = "order", required = false) int order /** 0 --> asc, 1--> desc*/,
                       @RequestParam(name = "orderBy", required = false) String orderBy){


        return meetingService.getMeetings(meetingName, hostName, pageSize, pageNo, order, orderBy);
    }

    @RequestMapping(value = "/meetingInfo/{timestamp}", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object getMeetingRoomDetailInfo(@PathVariable("timestamp") long timestamp){

        return meetingService.getMeetingRoomDetailInfo(timestamp);
    }


    @RequestMapping(value = "/meeting/{id}", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object getMeetingById(@PathVariable("id") int id){

        return meetingService.findByMeetingId(id);
    }

    @RequestMapping(value = "/meeting/validEndDate/{roomId}/{timestamp}", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object getValidEndDate(@PathVariable("roomId") int roomId,
                                  @PathVariable("timestamp") long timestamp){

        return meetingService.getValidEndDate(roomId, timestamp);
    }


    @RequestMapping(value = "/host/valid", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object getValidHosts(@RequestParam(name = "startTime") long startTime,
                                @RequestParam(name = "endTime") long endTime){

        return employeeRepository.findValidEmployee(startTime / 1000, endTime / 1000);
    }

    @RequestMapping(value = "/employee", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object getEmployees(){

        return employeeRepository.findAll();

    }

    @RequestMapping(value = "/meeting", produces = "application/json; charset=UTF-8",method = RequestMethod.POST)
    public Object createMeeting(@RequestBody MeetingRequest meetingRequest){

        return meetingService.save(meetingRequest);
    }

    @RequestMapping(value = "/meeting/{id}", produces = "application/json; charset=UTF-8", method = RequestMethod.DELETE)
    public Object deleteMeetingById(@PathVariable("id") int id){

        return meetingService.deleteById(id);
    }


}
