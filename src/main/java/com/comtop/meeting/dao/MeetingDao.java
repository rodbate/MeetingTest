package com.comtop.meeting.dao;


import com.comtop.meeting.common.LogUtil;
import com.comtop.meeting.entity.Meeting;
import com.comtop.meeting.common.CommonUtil;
import com.comtop.meeting.common.Constant;
import com.comtop.meeting.vo.MeetingVOEnhancer;
import com.comtop.meeting.vo.PageObject;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.comtop.meeting.common.CommonUtil.*;

/**
 * 会议dao类
 */
@Repository
public class MeetingDao {

    @PersistenceContext
    EntityManager entityManager;

    private final Logger LOGGER = LogUtil.getLogger("meeting");

    private final Logger LOGGER1 = LoggerFactory.getLogger(MeetingDao.class);


    public Object test(){

        Query query = entityManager.
                createNativeQuery("select * from t_meeting where id = ?", Meeting.class);

        return query.setParameter(1, 3).getResultList();
    }


    /**
     * 会议分页模糊查询
     * @param meetingName 会议名称
     * @param hostName 主持人名称
     * @param pageSize 每页条数
     * @param pageNo 当前页号
     * @param order 0 --> 升序  1 --> 降序
     * @param orderBy 排序字段
     * @return PageObject
     */
    public PageObject getMeetingsOnConditions(String meetingName, String hostName, int pageSize,
                                          int pageNo, int order, String orderBy){

        StringBuilder sql = new StringBuilder();

        List<Object> params = new ArrayList<Object>();

        sql.append("select a.id id,a.name meetingName,a.startTime startTime,a.endTime " +
                "endTime ,b.name hostName from t_meeting a left join t_employee b " +
                "on a.hostId=b.id where 1=1");

        if (isNotNull(meetingName)) {
            sql.append(" and a.name like ?");
            params.add("%" + meetingName + "%");
        }

        if (isNotNull(hostName)) {
            sql.append(" and b.name like ?");
            params.add("%" + hostName + "%");
        }

        String sqlCount = "select count(1) from (" + sql.toString() + ") c";

        Query queryCount = entityManager.createNativeQuery(sqlCount);

        LOGGER.info(sqlCount);
        LOGGER1.debug(sqlCount);

        int index = 1;

        for (Object o : params){
            queryCount.setParameter(index, o);
            index++;
        }


        long count = Long.valueOf(queryCount.getResultList().get(0).toString());

        String orderStr = order == 0 ? "ASC" : "DESC";

        if (CommonUtil.isNotNull(orderBy)) {
            sql.append(" order by ").append(orderBy.trim()).append(" ").append(orderStr);
            //params.add(orderBy.trim());
            //params.add(orderStr);
        }

        sql.append(" limit ?,?");

        params.add((pageNo - 1) * pageSize);
        params.add(pageSize);


        Query queryList = entityManager.createNativeQuery(sql.toString());

        int ind = 1;

        for (Object o : params) {

            queryList.setParameter(ind, o);
            ind++;
        }

        List resultList = queryList.getResultList();

        List<MeetingVOEnhancer> results = new ArrayList<MeetingVOEnhancer>();

        for (int i = 0; i < resultList.size(); i++) {
            MeetingVOEnhancer vo = new MeetingVOEnhancer();
            String json = new Gson().toJson(resultList.get(i));
            String[] props = json.substring(1, json.lastIndexOf("]")).split(",");
            if (props.length == 5) {
                vo.setId(Integer.valueOf(props[0]));
                vo.setMeetingName(props[1].substring(props[1].indexOf("\"") + 1, props[1].lastIndexOf("\"")));
                vo.setStartTime(CommonUtil.unixTimeToString(Long.valueOf(props[2]), Constant.TIME_FORMAT));
                vo.setEndTime(CommonUtil.unixTimeToString(Long.valueOf(props[3]), Constant.TIME_FORMAT));
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
