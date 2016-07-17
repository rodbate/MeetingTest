package com.comtop.dao;


import com.comtop.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer>{

    List<Participant> findByMeetingId(Integer id);
}
