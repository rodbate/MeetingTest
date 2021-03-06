package com.comtop.meeting.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * 会议室实体类
 */
@Entity
@Table(name = "t_meeting_room")
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;


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

}
