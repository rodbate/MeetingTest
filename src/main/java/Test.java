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


        Set<Integer> hours = new TreeSet<Integer>();

        hours.add(21);
        hours.add(1);
        hours.add(12);
        hours.add(5);
        hours.add(3);
        hours.add(3);

        for (Integer i : hours) {
            System.out.println(i);
        }
    }
}
