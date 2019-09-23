package com.zy.web.webserver;


import com.zy.web.webserver.socket.SocketServer;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
//开启声明式事务
@EnableTransactionManagement
//mapper接口扫描包
@MapperScan("com.zy.web.webserver.mapper")
public class WebserverApplication {

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(WebserverApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebserverApplication.class, args);
        try {
            //启动服务
            new SocketServer().startSocketServer();
            LOGGER.info("启动程序成功，时间为 {}",new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
