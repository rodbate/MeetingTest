package com.comtop.meeting.dao;


import com.comtop.meeting.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * employee repository 接口
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{


    @Query(value = "select * from t_employee where id  not in(select hostId from t_meeting " +
            "where (starttime<=?1 and endtime>?1) or (starttime<?2 and endtime >=?2))", nativeQuery = true)
    List<Employee> findValidEmployee(long startTime, long endTime);
}
