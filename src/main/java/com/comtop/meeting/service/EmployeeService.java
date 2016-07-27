package com.comtop.meeting.service;


import com.comtop.meeting.dao.EmployeeRepository;
import com.comtop.meeting.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工业务类
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    /**
     * 查询有效员工
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return List<Employee>
     */
    public List<Employee> findValidEmployee(long startTime, long endTime) {
        return employeeRepository.findValidEmployee(startTime, endTime);
    }


    /**
     * 查找全部员工
     * @return List<Employee>
     */
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
