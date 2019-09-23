package com.zy.web.webserver;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebserverApplicationTests {

    @Test
    public void contextLoads() {
        Long data = Long.parseLong("00001011",16);
        System.out.println(data);

        double v = (8197249/ 8388608.0 - 1.0) * 2.5 / 64.0 * 4.0 / 2.1 / 5.0 * 1000000;
        System.out.println("OA的值："+v);

        double v1 = (((float)8199773/8388608.0)-1.0)*2.5/64.0*4.0/2.1/5.0*1000000;
        System.out.println("加float结果为："+v1);
    }

}
