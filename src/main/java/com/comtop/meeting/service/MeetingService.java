package com.comtop.meeting.service;


import com.comtop.meeting.dao.*;
import com.comtop.meeting.entity.Employee;
import com.comtop.meeting.entity.Meeting;
import com.comtop.meeting.entity.MeetingRoom;
import com.comtop.meeting.entity.Participant;
import com.comtop.meeting.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


import static com.comtop.meeting.common.CommonUtil.*;
import static com.comtop.meeting.common.Constant.*;
import static com.comtop.meeting.common.ReflectionUtil.*;

/**
 * 会议管理业务类
 */
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


    /**
     * 根据会议id删除会议记录及其相应的参与者
     * @param id 会议id
     * @return 0 --> false or true else
     */
    @Transactional
    public int deleteById(int id){

        int count = meetingRepository.deleteById(id);

        if (count > 0) {
            return participantRepository.deleteByMeetingId(id);
        }
        return 0;
    }


    /**
     * 创建会议记录
     * @param meetingRequest 会议请求对象
     * @return 0 --> true  1--> false
     */
    @Transactional
    public int save(MeetingRequest meetingRequest){

        //服务端校验meeting name 唯一性
        if (meetingRepository.countByName(meetingRequest.getMeetingName()) > 0)
            return 0;

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

    /**
     * 根据起始时间获取某个会议室的有效结束时间
     * @param roomId 会议室id
     * @param timestamp 起始时间戳
     * @return List<ValidEndDateVO>
     */
    public List<ValidEndDateVO> getValidEndDate(int roomId, long timestamp){

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

    /**
     * 根据会议id查询会议记录
     * @param id 会议id
     * @return 会议记录VO对象 MeetingDetailInfoVO
     */
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

        return convertObject(meeting, MeetingDetailInfoVO.class);
    }


    /**
     * 根据时间戳获取某天会议室使用情况
     * @param timestamp 时间戳
     * @return List<MeetingRoomVO>
     */
    public List<MeetingRoomVO> getMeetingRoomDetailInfo(long timestamp){

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

    /**
     * 模糊查询会议记录
     * @param meetingName 会议名称
     * @param hostName 主持人名称
     * @param pageSize 每页条数
     * @param pageNo 当前页码
     * @param order 排序方式 0-->升序   1-->降序
     * @param orderBy 排序字段
     * @return PageObject 分页对象
     */
    public PageObject getMeetings(String meetingName, String hostName, int pageSize,
                                  int pageNo, int order, String orderBy) {

        return meetingDao.getMeetingsOnConditions(meetingName, hostName, pageSize, pageNo, order, orderBy);
    }

    public Object testExample(){

        return null;
    }

    /**
     * 验证会议名称唯一性
     * @param name 会议名称
     * @return 0 --> 不存在    1 --> 存在
     */
    public long existMeeting(String name){
        return meetingRepository.countByName(name);
    }
}
