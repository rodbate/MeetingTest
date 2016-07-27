package com.comtop.meeting.controller;

import com.comtop.meeting.entity.Employee;
import com.comtop.meeting.service.EmployeeService;
import com.comtop.meeting.service.MeetingService;
import com.comtop.meeting.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 会议控制器
 */

@RestController
@RequestMapping(value = "/api")
public class MeetingController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    MeetingService meetingService;


    /**
     * 分页模糊查询会议对象
     * @param meetingName 会议名称
     * @param hostName 支持人名称
     * @param pageSize 每页条数
     * @param pageNo 当前页码
     * @param order 排序方式 0 --> 升序   1 --> 降序
     * @param orderBy 排序字段
     * @return PageObject 页面对象
     */
    @RequestMapping(value = "/meeting", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public PageObject list(@RequestParam(name = "meetingName", required = false) String meetingName,
                           @RequestParam(name = "hostName", required = false) String hostName,
                           @RequestParam(name = "pageSize") int pageSize,
                           @RequestParam(name = "pageNo") int pageNo,
                           @RequestParam(name = "order", required = false) int order /** 0 --> asc, 1--> desc*/,
                           @RequestParam(name = "orderBy", required = false) String orderBy){


        return meetingService.getMeetings(meetingName, hostName, pageSize, pageNo, order, orderBy);
    }

    /**
     * 获取某天会议室使用情况
     * @param timestamp 时间戳
     * @return List<MeetingRoomVO>
     */
    @RequestMapping(value = "/meetingInfo/{timestamp}", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public List<MeetingRoomVO> getMeetingRoomDetailInfo(@PathVariable("timestamp") long timestamp){

        return meetingService.getMeetingRoomDetailInfo(timestamp);
    }

    /**
     * 根据会议id获取会议记录
     * @param id 会议id
     * @return MeetingDetailInfoVO
     */
    @RequestMapping(value = "/meeting/{id}", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public MeetingDetailInfoVO getMeetingById(@PathVariable("id") int id){

        return (MeetingDetailInfoVO) meetingService.findByMeetingId(id);
    }


    /**
     * 根据起始时间获取某个会议室的有效结束时间
     * @param roomId 会议室id
     * @param timestamp 起始时间戳
     * @return List<ValidEndDateVO>
     */
    @RequestMapping(value = "/meeting/validEndDate/{roomId}/{timestamp}", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public List<ValidEndDateVO> getValidEndDate(@PathVariable("roomId") int roomId,
                                                @PathVariable("timestamp") long timestamp){

        return meetingService.getValidEndDate(roomId, timestamp);
    }


    /**
     * 获取有效的主持人
     * @param startTime 起始时间戳
     * @param endTime 结束时间戳
     * @return List<Employee>
     */
    @RequestMapping(value = "/host/valid", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public List<Employee> getValidHosts(@RequestParam(name = "startTime") long startTime,
                                        @RequestParam(name = "endTime") long endTime){

        return employeeService.findValidEmployee(startTime / 1000, endTime / 1000);
    }


    /**
     * 查询所有的员工
     * @return List<Employee>
     */
    @RequestMapping(value = "/employee", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public List<Employee> getEmployees(){

        return employeeService.findAll();

    }


    /**
     * 新建会议记录
     * @param meetingRequest 会议请求对象
     * @return 1 --> success 0--> fail
     */
    @RequestMapping(value = "/meeting", produces = "application/json; charset=UTF-8",method = RequestMethod.POST)
    public int createMeeting(@RequestBody MeetingRequest meetingRequest){

        return meetingService.save(meetingRequest);
    }


    /**
     * 根据会议id删除会议记录
     * @param id 会议id
     * @return 0 --> fail  1 --> success
     */
    @RequestMapping(value = "/meeting/{id}", produces = "application/json; charset=UTF-8", method = RequestMethod.DELETE)
    public int deleteMeetingById(@PathVariable("id") int id){

        return meetingService.deleteById(id);
    }


    /**
     * 验证会议名称是否存在
     * @param name 会议名称
     * @return 0 不存在  >0 存在
     */
    @RequestMapping(value = "/meeting/exist/{name}", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    public long checkUniqueMeetingByName(@PathVariable("name") String name){
        return meetingService.existMeeting(name);
    }

}
