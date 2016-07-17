package com.comtop.controller;

import com.comtop.dao.EmployeeRepository;
import com.comtop.dao.MeetingRepository;
import com.comtop.dao.MeetingRoomRepository;
import com.comtop.dao.ParticipantRepository;
import com.comtop.entity.Employee;
import com.comtop.entity.Meeting;
import com.comtop.entity.MeetingRoom;
import com.comtop.entity.Participant;
import com.comtop.vo.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;


import java.util.*;

import static org.springframework.data.domain.Sort.Direction.*;

import static com.comtop.common.Constant.*;
import static com.comtop.common.CommonUtil.*;

@RestController
@RequestMapping(value = "/api")
public class MeetingController {


    @Autowired
    MeetingRepository repository;

    @Autowired
    MeetingRoomRepository roomRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ParticipantRepository participantRepository;

    @RequestMapping(value = "/meeting", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object list(@RequestParam(name = "pageSize") int pageSize,
                       @RequestParam(name = "pageNo") int pageNo,
                       @RequestParam(name = "order", required = false) int order /** 0 --> asc, 1--> desc*/,
                       @RequestParam(name = "orderBy", required = false) String orderBy){

        PageRequest pageRequest;

        if (isNotNull(orderBy)) {
            pageRequest = new PageRequest(pageNo - 1, pageSize,
                    order == 0 ? ASC : DESC, orderBy);
        } else {
            pageRequest = new PageRequest(pageNo - 1, pageSize);
        }

        Page<Meeting> page = repository.findAll(pageRequest);

        return buildPageObject(page, Meeting.class, MeetingVO.class);
    }

    @RequestMapping(value = "/meetingInfo/{timestamp}", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object getMeetingRoomDetailInfo(@PathVariable("timestamp") long timestamp){

        Map<String, Long> map = getStartEndUnixTimeByDay(timestamp / 1000);
        long startUnixTime = map.get("start");
        long endUnixTime = map.get("end");

        List<Meeting> meetings = repository.findByStartTimeBetween(startUnixTime, endUnixTime);

        List<MeetingRoomVO> roomVOs = new ArrayList<MeetingRoomVO>();

        List<MeetingRoom> rooms = roomRepository.findAll();

        Map<Integer, MeetingRoomVO> roomVOMap = new HashMap<Integer, MeetingRoomVO>();

        for(MeetingRoom room : rooms){
            MeetingRoomVO roomVO = new MeetingRoomVO();
            roomVO.setId(room.getId());
            roomVO.setName(room.getName());
            roomVOMap.put(room.getId(), roomVO);
        }


        for(Meeting meeting : meetings){


            int roomId = meeting.getMeetingRoomId();

            int hostId = meeting.getHostId();

            MeetingRoom meetingRoom = roomRepository.findOne(roomId);

            Employee host = employeeRepository.findOne(hostId);

            meeting.setHost(host);

            meeting.setMeetingRoom(meetingRoom);

            int startHour = Integer.valueOf(unixTimeToString(meeting.getStartTime(), "HH"));
            int endHour = Integer.valueOf(unixTimeToString(meeting.getEndTime(), "HH"));

            MeetingRoomVO roomVO = roomVOMap.get(roomId);

            if (endHour - startHour == 1) {
                setTableTDForMeetingRoom(roomVO, meeting, startHour);
            } else {
                setTableTDForMeetingRoom(roomVO, meeting, startHour, endHour);
            }

        }

        for ( Map.Entry<Integer, MeetingRoomVO> entry : roomVOMap.entrySet()) {

            roomVOs.add(entry.getValue());
        }

        return roomVOs;
    }


    @RequestMapping(value = "/meeting/{id}", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object getMeetingById(@PathVariable("id") int id){

        Meeting meeting = repository.findOne(id);

        int roomId = meeting.getMeetingRoomId();

        int hostId = meeting.getHostId();

        MeetingRoom meetingRoom = roomRepository.findOne(roomId);

        Employee host = employeeRepository.findOne(hostId);

        List<Participant> participants = participantRepository.findByMeetingId(meeting.getId());

        meeting.setHost(host);

        meeting.setMeetingRoom(meetingRoom);

        meeting.setParticipants(listToSet(participants));

        return convertObject(meeting, Meeting.class, MeetingDetailInfoVO.class);
    }

    @RequestMapping(value = "/meeting/validEndDate/{roomId}/{timestamp}", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object getValidEndDate(@PathVariable("roomId") int roomId,
                                  @PathVariable("timestamp") long timestamp){

        Map<String, Long> map = getStartEndUnixTimeByDay(timestamp / 1000);
        long startUnixTime = map.get("start");
        long endUnixTime = map.get("end");

        List<Meeting> meetings = repository.findValidEndDates(roomId, startUnixTime, endUnixTime);

        Set<Integer> hours = new TreeSet<Integer>();
        Set<Integer> retHours = new TreeSet<Integer>();


        for(Meeting meeting : meetings){
            int startHour = Integer.valueOf(unixTimeToString(meeting.getStartTime(), "HH"));
            int endHour = Integer.valueOf(unixTimeToString(meeting.getEndTime(), "HH"));

            for (int i = startHour; i < endHour; i++) {
                hours.add(i);
            }
        }
        hours.add(12);
        hours.add(18);

        for (Integer i : hours) {
            System.out.println(i);
        }
        int currentHour = Integer.valueOf(unixTimeToString(timestamp / 1000, "HH"));

        for(Integer h : hours) {
            if (currentHour < h) {
                for (int i = currentHour + 1; i <= h ; i++) {
                    retHours.add(i);
                }
                break;
            }
        }

        List<ValidEndDateVO> dates = new ArrayList<ValidEndDateVO>();
        String date = unixTimeToString(timestamp / 1000, "yyyy-MM-dd");
        for (Integer i : retHours){
            ValidEndDateVO vo = new ValidEndDateVO();
            vo.setHour(i + ":00");
            vo.setTime(date + " " + i + ":00");
            dates.add(vo);
        }

        return dates;
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

        Meeting meeting = new Meeting();

        meeting.setName(meetingRequest.getMeetingName());
        meeting.setHostId(meetingRequest.getHostId());
        meeting.setStartTime(stringToUnixTimeStamp(meetingRequest.getStartDate(), TIME_FORMAT));
        meeting.setEndTime(stringToUnixTimeStamp(meetingRequest.getEndDate(), TIME_FORMAT));
        meeting.setMeetingRoomId(meetingRequest.getMeetingRoomId());

        Meeting retMeeting = repository.save(meeting);

        int[] participantIds = meetingRequest.getParticipants();

        List<Participant> participants = new ArrayList<Participant>();

        for (int i = 0; i < participantIds.length; i++) {

            Employee employee = employeeRepository.findOne(participantIds[i]);

            Participant p = new Participant();
            p.setName(employee.getName());
            p.setMeetingId(retMeeting.getId());
            participants.add(p);
        }

        participantRepository.save(participants);

        return 1;

    }


}
