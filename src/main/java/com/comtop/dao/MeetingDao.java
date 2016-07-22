package com.comtop.dao;


import com.comtop.entity.Meeting;
import com.comtop.vo.MeetingVOEnhancer;
import com.comtop.vo.PageObject;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;

import static com.comtop.common.CommonUtil.*;
import static com.comtop.common.Constant.*;

@Repository
public class MeetingDao {

    @PersistenceContext
    EntityManager entityManager;


    public Object test(){

        Query query = entityManager.
                createNativeQuery("select * from t_meeting where id = ?", Meeting.class);

        return query.setParameter(1, 3).getResultList();
    }

    public Object getMeetingsOnConditions(String meetingName, String hostName, int pageSize,
                                          int pageNo, int order, String orderBy){

        StringBuilder sql = new StringBuilder();

        sql.append("select a.id id,a.name meetingName,a.startTime startTime,a.endTime " +
                "endTime ,b.name hostName from t_meeting a left join t_employee b " +
                "on a.hostId=b.id where 1=1");

        if (isNotNull(meetingName)) {
            sql.append(" and a.name like '%").append(meetingName.trim()).append("%'");
        } else if (isNotNull(hostName)) {
            sql.append(" and b.name like '%").append(hostName.trim()).append("%'");
        }

        String sqlCount = "select count(1) from (" + sql.toString() + ") c";


        long count = Long.valueOf(entityManager.createNativeQuery(sqlCount).getSingleResult().toString());

        if (isNotNull(orderBy)) {
            sql.append(" order by ").append(orderBy.trim()).append(" ")
                .append(order == 0 ? "ASC" : "DESC");
        }

        sql.append(" limit ")
                .append((pageNo - 1) * pageSize).append(",")
                .append(pageSize);

        List resultList = entityManager.createNativeQuery(sql.toString()).getResultList();

        List<MeetingVOEnhancer> results = new ArrayList<MeetingVOEnhancer>();

        for (int i = 0; i < resultList.size(); i++) {
            MeetingVOEnhancer vo = new MeetingVOEnhancer();
            String json = new Gson().toJson(resultList.get(i));
            String[] props = json.substring(1, json.lastIndexOf("]")).split(",");
            if (props.length == 5) {
                vo.setId(Integer.valueOf(props[0]));
                vo.setMeetingName(props[1].substring(props[1].indexOf("\"") + 1, props[1].lastIndexOf("\"")));
                vo.setStartTime(unixTimeToString(Long.valueOf(props[2]), TIME_FORMAT));
                vo.setEndTime(unixTimeToString(Long.valueOf(props[3]), TIME_FORMAT));
                vo.setHostName(props[4].substring(props[4].indexOf("\"") + 1, props[4].lastIndexOf("\"")));
            }

            results.add(vo);
        }

        PageObject page = new PageObject();

        int totalPage = ((int)count + pageSize -1) / pageSize;

        page.setTotalPage(totalPage);
        page.setPageSize(pageSize);
        page.setPageNo(pageNo);
        page.setContent(results);

        return page;
    }


}
