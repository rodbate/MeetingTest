import com.comtop.common.CommonUtil;
import com.comtop.controller.MeetingController;
import com.comtop.dao.MeetingRepository;
import com.comtop.entity.Meeting;
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

        MeetingController bean = context.getBean(MeetingController.class);

        //Object list = bean.list(5, 1, 0, null);


        //System.out.println(new Gson().toJson(list));

        //Object room = bean.getMeetingRoomDetailInfo(CommonUtil.stringToUnixTimeStamp("2016-07-13 14:00",
                //"yyyy-MM-dd HH:mm") * 1000);

        //Object m = bean.getMeetingById(1);

        Object date = bean.getValidEndDate(1, CommonUtil.stringToUnixTimeStamp("2016-07-13 14:00",
                "yyyy-MM-dd HH:mm") * 1000);

        System.out.println(new Gson().toJson(date));

    }
}
