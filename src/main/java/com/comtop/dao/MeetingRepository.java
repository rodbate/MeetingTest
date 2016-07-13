package com.comtop.dao;

import com.comtop.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {


    List<Meeting> findByStartTimeBetween(long startTime, long endTime);

}
