package com.comtop.meeting.vo;


import com.comtop.meeting.common.CommonUtil;
import com.comtop.meeting.entity.Meeting;
import com.comtop.meeting.entity.Participant;


import static com.comtop.meeting.common.CommonUtil.*;
import static com.comtop.meeting.common.Constant.*;
import static com.comtop.meeting.common.ReflectionUtil.*;


/**
 *
 *
 * 会议记录详情值对象
 */
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
