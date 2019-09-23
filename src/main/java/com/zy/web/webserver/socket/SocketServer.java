package com.zy.web.webserver.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.config.ScheduledTask;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 此类用长连接不断监听传感器传输过来的数据
 */
public class SocketServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);

    public void startSocketServer() {

        try {
            //1、创建一个服务器端Socket,即ServerSocket, 指定绑定的端口，并监听此端口
            ServerSocket serverSocket = new ServerSocket(21);
            LOGGER.info("***服务器即将启动，等待client的链接***");
            //循环监听等待client的链接
            while (true){
                //调用accept()方法開始监听，等待client的链接
                Socket socket = serverSocket.accept();
                //创建一个新的线程
                ServerThread serverThread = new ServerThread(socket);
                //启动线程
                serverThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("socket异常"+e.getMessage());
        }
    }
}

