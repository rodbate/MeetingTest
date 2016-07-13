package com.comtop.vo;


public class HourActiveVO {

    //0-->未使用 1-->使用中
    private int active;

    private int meetingId;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }
}
