package com.comtop.controller;

import com.comtop.common.CommonUtil;
import com.comtop.dao.MeetingRepository;
import com.comtop.dao.MeetingRoomRepository;
import com.comtop.entity.Meeting;
import com.comtop.entity.MeetingRoom;
import com.comtop.vo.*;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.*;

import static org.springframework.data.domain.Sort.Direction.*;

@RestController
@RequestMapping(value = "/api")
public class MeetingController {


    @Autowired
    MeetingRepository repository;

    @Autowired
    MeetingRoomRepository roomRepository;

    @RequestMapping(value = "/meeting", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object list(@RequestParam(name = "pageSize") int pageSize,
                       @RequestParam(name = "pageNo") int pageNo,
                       @RequestParam(name = "order", required = false) int order /** 0 --> asc, 1--> desc*/,
                       @RequestParam(name = "orderBy", required = false) String orderBy){

        PageRequest pageRequest;

        if (CommonUtil.isNotNull(orderBy)) {
            pageRequest = new PageRequest(pageNo - 1, pageSize,
                    order == 0 ? ASC : DESC, orderBy);
        } else {
            pageRequest = new PageRequest(pageNo - 1, pageSize);
        }

        Page<Meeting> page = repository.findAll(pageRequest);

        return CommonUtil.buildPageObject(page, Meeting.class, MeetingVO.class);
    }

    @RequestMapping(value = "/meetingInfo/{timestamp}", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object getMeetingRoomDetailInfo(@PathVariable("timestamp") long timestamp){

        Map<String, Long> map = CommonUtil.getStartEndUnixTimeByDay(timestamp / 1000);
        long startUnixTime = map.get("start");
        long endUnixTime = map.get("end");

        List<Meeting> meetings = repository.findByStartTimeBetween(startUnixTime, endUnixTime);

        List<MeetingDetailVO> detailVOs = new ArrayList<MeetingDetailVO>();

        Set<Integer> meetingRoomIds = new HashSet<Integer>();

        for(Meeting meeting : meetings){
            MeetingDetailVO detailVO = new MeetingDetailVO();
            MeetingRoomVO roomVO = new MeetingRoomVO();
            MeetingInfoVO infoVO = new MeetingInfoVO();

            MeetingRoom meetingRoom = meeting.getMeetingRoom();
            roomVO.setId(meetingRoom.getId());
            roomVO.setName(meetingRoom.getName());

            HourActiveVO h9 = new HourActiveVO();
            HourActiveVO h10 = new HourActiveVO();
            HourActiveVO h11 = new HourActiveVO();
            HourActiveVO h12 = new HourActiveVO();
            HourActiveVO h14 = new HourActiveVO();
            HourActiveVO h15 = new HourActiveVO();
            HourActiveVO h16 = new HourActiveVO();
            HourActiveVO h17 = new HourActiveVO();
            HourActiveVO h18 = new HourActiveVO();

            int startHour = Integer.valueOf(CommonUtil.unixTimeToString(meeting.getStartTime(), "HH"));
            int endHour = Integer.valueOf(CommonUtil.unixTimeToString(meeting.getEndTime(), "HH"));
            int meetingId = meeting.getId();
            meetingRoomIds.add(meetingId);
            for (int i = startHour; i <= endHour ; i++) {
                switch (i) {
                    case 9 : {
                       h9.setActive(1);
                       h9.setMeetingId(meetingId);
                    } break;
                    case 10 : {
                        h10.setActive(1);
                        h10.setMeetingId(meetingId);
                    };break;
                    case 11 : {
                        h11.setActive(1);
                        h11.setMeetingId(meetingId);
                    };break;
                    case 12 : {
                        h12.setActive(1);
                        h12.setMeetingId(meetingId);
                    };break;
                    case 14 : {
                        h14.setActive(1);
                        h14.setMeetingId(meetingId);
                    };break;
                    case 15 : {
                        h15.setActive(1);
                        h15.setMeetingId(meetingId);
                    };break;
                    case 16 : {
                        h16.setActive(1);
                        h16.setMeetingId(meetingId);
                    };break;
                    case 17 : {
                        h17.setActive(1);
                        h17.setMeetingId(meetingId);
                    };break;
                    case 18 : {
                        h18.setActive(1);
                        h18.setMeetingId(meetingId);
                    };break;
                }
            }

            infoVO.setH9(h9);
            infoVO.setH10(h10);
            infoVO.setH11(h10);
            infoVO.setH12(h12);
            infoVO.setH14(h14);
            infoVO.setH15(h15);
            infoVO.setH16(h16);
            infoVO.setH17(h17);
            infoVO.setH18(h18);

            detailVO.setRoom(roomVO);
            detailVO.setInfo(infoVO);
            detailVOs.add(detailVO);
        }


        List<MeetingRoom> rooms = roomRepository.findAll();

        for(MeetingRoom room : rooms){
            int id = room.getId();
            String name = room.getName();
            if (!meetingRoomIds.contains(id)){
                MeetingDetailVO detailVO = new MeetingDetailVO();
                MeetingRoomVO roomVO = new MeetingRoomVO();
                roomVO.setId(id);
                roomVO.setName(name);
                detailVO.setRoom(roomVO);
                detailVOs.add(detailVO);
            }
        }

        return detailVOs;
    }


}
