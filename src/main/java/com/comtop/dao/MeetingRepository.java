package com.comtop.dao;

import com.comtop.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {


    List<Meeting> findByStartTimeBetween(long startTime, long endTime);

    @Query(value = "select * from t_meeting where meetingRoomId=?1 and startTime between ?2 and ?3", nativeQuery = true)
    List<Meeting> findValidEndDates(int meetingRoomId, long startTime, long endTime);

    int deleteById(Integer id);
}
