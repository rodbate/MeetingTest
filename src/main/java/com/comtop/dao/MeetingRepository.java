package com.comtop.dao;

import com.comtop.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jiangsongsong on 2016/7/7.
 */

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {


}
