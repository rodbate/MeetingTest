package com.comtop.meeting.vo;


/**
 *
 *
 * 会议室表格会议记录值对象
 */
public class TableTDVO {

    private int active;

    private int meetingId;

    private int colSpan;

    private String display;

    private String host;

    public TableTDVO() {
    }

    public TableTDVO(int meetingId, int active, int colSpan, String display, String host) {
        this.active = active;
        this.meetingId = meetingId;
        this.colSpan = colSpan;
        this.display = display;
        this.host = host;
    }

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

    public int getColSpan() {
        return colSpan;
    }

    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
