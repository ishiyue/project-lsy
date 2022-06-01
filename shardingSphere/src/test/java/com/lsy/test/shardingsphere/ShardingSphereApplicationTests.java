package com.lsy.test.shardingsphere;

import com.lsy.test.shardingsphere.entity.Course;
import com.lsy.test.shardingsphere.mapper.CourseMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ShardingSphereApplicationTests {

    @Resource
    private CourseMapper courseMapper;
    @Test
    void addCourse() {
        for (int i = 0; i < 10; i++) {
            Course c=new Course();
            c.setCname("java");
            c.setUserid(100L);
            c.setCstatus("1");
            courseMapper.insert(c);
        }
    }

    @Test
    public List<Course> addAll(){
        List<Course> list1=new ArrayList<>();
        List<Course> list2=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Course c=new Course();
            c.setCname("java");
            c.setUserid(100L);
            c.setCstatus("1");
            list1.add(c);
        }
        for (int i = 0; i < 10; i++) {
            Course c=new Course();
            c.setCname("java");
            c.setUserid(100L);
            c.setCstatus("1");
            list2.add(c);
        }
        list1.addAll(list2);
        return list1;
    }
}
