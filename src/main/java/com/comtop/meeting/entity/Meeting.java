package com.comtop.meeting.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * 会议实体类
 */
@Entity
@Table(name = "t_meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STARTTIME")
    private long startTime;

    @Column(name = "ENDTIME")
    private long endTime;

    @Column(name = "HOSTID")
    private int hostId;

    @Column(name = "MEETINGROOMID")
    private int meetingRoomId;

    @Transient
    private Set<Participant> participants = new HashSet<Participant>();

    @Transient
    private Employee host;

    @Transient
    private MeetingRoom meetingRoom;


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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public int getMeetingRoomId() {
        return meetingRoomId;
    }

    public void setMeetingRoomId(int meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
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
}
