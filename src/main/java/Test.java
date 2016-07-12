import com.comtop.dao.MeetingRepository;
import com.comtop.entity.Meeting;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {

    public static void main(String[] args) throws ParseException {

        //new LocalContainerEntityManagerFactoryBean().setJpaProperties();

        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date start = sdf.parse("2016-07-12 11:00");
        Date end = sdf.parse("2016-07-12 16:00");

        System.out.printf("start : " + start.getTime() / 1000 +
                "  end : " + end.getTime() / 1000);*/


        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        MeetingRepository repository = context.getBean(MeetingRepository.class);

        Pageable pageAble = new PageRequest(1, 2);

        Page<Meeting> pager = repository.findAll(pageAble);
        System.out.println("总页数：" + pager.getTotalPages());
        List<Meeting> list =  pager.getContent();
        for(Meeting user : list){
            System.out.println(user.toString());
        }
    }
}
