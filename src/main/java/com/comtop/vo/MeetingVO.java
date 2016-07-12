package com.comtop.vo;


import com.comtop.common.CommonUtil;
import com.comtop.common.Constant;
import com.comtop.entity.Employee;
import com.comtop.entity.Meeting;
import com.comtop.entity.MeetingRoom;
import com.comtop.entity.Participant;

import java.util.HashSet;
import java.util.Set;

public class MeetingVO {

    private Integer id;
    private String name;
    private String startTime; /** format --> yyyy-MM-dd HH:mm*/
    private String endTime; /** format --> yyyy-MM-dd HH:mm*/
    private Employee host;
    private MeetingRoom meetingRoom;
    private Set<Participant> participants = new HashSet<Participant>();

    public MeetingVO() {
    }

    public MeetingVO(Meeting meeting) {
        id = meeting.getId();
        name = meeting.getName();
        startTime = CommonUtil.unixTimeToString(meeting.getStartTime(), Constant.TIME_FORMAT);
        endTime = CommonUtil.unixTimeToString(meeting.getEndTime(), Constant.TIME_FORMAT);
        host = meeting.getHost();
        meetingRoom = meeting.getMeetingRoom();
        participants = meeting.getParticipants();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Employee getHost() {
        return host;
    }

    public void setHost(Employee host) {
        this.host = host;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }
}
