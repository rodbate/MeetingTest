import com.comtop.common.CommonUtil;
import com.comtop.controller.MeetingController;
import com.comtop.dao.MeetingDao;
import com.comtop.dao.MeetingRepository;
import com.comtop.entity.Meeting;
import com.comtop.service.MeetingService;
import com.google.gson.Gson;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Test {

    public static void main(String[] args) throws ParseException {

        //new LocalContainerEntityManagerFactoryBean().setJpaProperties();

        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date start = sdf.parse("2016-07-12 11:00");
        Date end = sdf.parse("2016-07-12 16:00");

        System.out.printf("start : " + start.getTime() / 1000 +
                "  end : " + end.getTime() / 1000);*/


        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");


        MeetingService bean = context.getBean(MeetingService.class);
        Object o = bean.existMeeting("测试会议3");

        System.out.println(new Gson().toJson(o));



    }
}
