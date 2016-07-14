package com.comtop.vo;


import com.comtop.common.CommonUtil;
import com.comtop.entity.Meeting;
import com.comtop.entity.Participant;

import static com.comtop.common.Constant.*;

public class MeetingDetailInfoVO {

    private String meetingName;

    private String meetingRoomName;

    private String hostName;

    private String durationTime;

    private String participants;

    public MeetingDetailInfoVO() {
    }

    public MeetingDetailInfoVO(Meeting meeting) {
        meetingName = meeting.getName();
        meetingRoomName = meeting.getMeetingRoom().getName();
        hostName = meeting.getHost().getName();
        durationTime = CommonUtil.unixTimeToString(meeting.getStartTime(), TIME_FORMAT) +
                " 至 " + CommonUtil.unixTimeToString(meeting.getEndTime(), TIME_FORMAT);
        StringBuilder sb = new StringBuilder();
        for (Participant p : meeting.getParticipants()) {
            sb.append(p.getName() + "  ");
        }
        participants = sb.toString();
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getMeetingRoomName() {
        return meetingRoomName;
    }

    public void setMeetingRoomName(String meetingRoomName) {
        this.meetingRoomName = meetingRoomName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }
}
