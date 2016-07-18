package com.comtop.service;


import com.comtop.dao.*;
import com.comtop.entity.Employee;
import com.comtop.entity.Meeting;
import com.comtop.entity.MeetingRoom;
import com.comtop.entity.Participant;
import com.comtop.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


import static com.comtop.common.Constant.*;
import static com.comtop.common.CommonUtil.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

@Service
public class MeetingService {

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    MeetingRoomRepository roomRepository;

    @Autowired
    MeetingDao meetingDao;


    @Transactional
    public Object deleteById(int id){

        int count = meetingRepository.deleteById(id);

        if (count > 0) {
            return participantRepository.deleteByMeetingId(id);
        }
        return 0;
    }

    @Transactional
    public Object save(MeetingRequest meetingRequest){

        Meeting meeting = new Meeting();

        meeting.setName(meetingRequest.getMeetingName());
        meeting.setHostId(meetingRequest.getHostId());
        meeting.setStartTime(stringToUnixTimeStamp(meetingRequest.getStartDate(), TIME_FORMAT));
        meeting.setEndTime(stringToUnixTimeStamp(meetingRequest.getEndDate(), TIME_FORMAT));
        meeting.setMeetingRoomId(meetingRequest.getMeetingRoomId());

        Meeting retMeeting = meetingRepository.save(meeting);

        int[] participantIds = meetingRequest.getParticipants();

        List<Participant> participants = new ArrayList<Participant>();

        for (int i = 0; i < participantIds.length; i++) {

            Employee employee = employeeRepository.findOne(participantIds[i]);

            Participant p = new Participant();
            p.setName(employee.getName());
            p.setMeetingId(retMeeting.getId());
            participants.add(p);
        }

        List<Participant> ret = participantRepository.save(participants);

        return ret == null ? 0 : 1;
    }

    public Object getValidEndDate(int roomId, long timestamp){

        Map<String, Long> map = getStartEndUnixTimeByDay(timestamp / 1000);
        long startUnixTime = map.get("start");
        long endUnixTime = map.get("end");

        List<Meeting> meetings = meetingRepository.findValidEndDates(roomId, startUnixTime, endUnixTime);

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

    public Object findByMeetingId(int id){

        Meeting meeting = meetingRepository.findOne(id);

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




    public Object getMeetingRoomDetailInfo(long timestamp){

        Map<String, Long> map = getStartEndUnixTimeByDay(timestamp / 1000);
        long startUnixTime = map.get("start");
        long endUnixTime = map.get("end");

        List<Meeting> meetings = meetingRepository.findByStartTimeBetween(startUnixTime, endUnixTime);

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


    public Object getMeetings(String meetingName, String hostName, int pageSize,
                              int pageNo, int order, String orderBy) {

        return meetingDao.getMeetingsOnConditions(meetingName, hostName, pageSize, pageNo, order, orderBy);
    }

    public Object testExample(){

        return null;
    }
}
