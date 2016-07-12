package com.comtop.controller;

import com.comtop.common.CommonUtil;
import com.comtop.dao.MeetingRepository;
import com.comtop.entity.Meeting;
import com.comtop.vo.MeetingVO;
import com.comtop.vo.PageObject;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.List;

import static org.springframework.data.domain.Sort.Direction.*;

@RestController
@RequestMapping(value = "/api")
public class MeetingController {


    @Autowired
    MeetingRepository repository;

    @RequestMapping(value = "/meeting", produces = "application/json; charset=UTF-8",method = RequestMethod.GET)
    public Object list(@RequestParam(name = "pageSize") int pageSize,
                       @RequestParam(name = "pageNo") int pageNo,
                       @RequestParam(name = "order", required = false) int order /** 0 --> asc, 1--> desc*/,
                       @RequestParam(name = "orderBy", required = false) String orderBy){

        PageRequest pageRequest;

        if (CommonUtil.isNotNull(orderBy)) {
            pageRequest = new PageRequest(pageNo - 1, pageSize,
                    order == 0 ? ASC : DESC, orderBy);
        } else {
            pageRequest = new PageRequest(pageNo - 1, pageSize);
        }

        Page<Meeting> page = repository.findAll(pageRequest);

        return CommonUtil.buildPageObject(page, Meeting.class, MeetingVO.class);
    }



}
