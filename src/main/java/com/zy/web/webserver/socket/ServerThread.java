package com.zy.web.webserver.socket;


import com.zy.web.webserver.bean.Sessor;
import com.zy.web.webserver.bean.SessorData;
import com.zy.web.webserver.mapper.SessorDataMapper;
import com.zy.web.webserver.mapper.SessorMapper;
import com.zy.web.webserver.util.BeanUtil;
import com.zy.web.webserver.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerThread extends Thread {

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerThread.class);

    //和本线程相关的Socket
    Socket socket;

    //有参构造器
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    //线程运行的操作，响应client的请求
    public void run() {

        BufferedInputStream bis = null;
        BufferedWriter bw = null;

        try {
            //获取一个输入流，并读取client的信息
            bis = new BufferedInputStream(socket.getInputStream());//读取网络的数据
            //写入一个文件保存数据
            //bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\SessorData.txt", true)));

            byte[] b = new byte[12];

            while(bis.read(b) != -1) {
                //将数据转换为16进制
                String stringData = StringUtil.bytesToHex1(b);
                LOGGER.info("插入时间 {},写入16进制数据为{}",new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date()),stringData);
                //将数据写入文件中
                //bw.write("插入时间："+new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date()) + "写入数据为"+stringData+"::");
                //bw.newLine();
                //缓冲流刷新才写入
                //bw.flush();
                //对字符串进行匹配
                //获取传感器类型
                String type = stringData.substring(2,4);
                //获取传感器地址
                String addr = stringData.substring(4,8);
                //传感器ip与端口
                String address = socket.getInetAddress().getHostAddress();
                String ip = address + " : " + socket.getPort();
                LOGGER.info("传感器类型:{},地址:{},ip和端口:{}",type,addr,ip);
                //16进制转换为10进制Long型,位移原始数据
                String hex = stringData.substring(8,16);
                Long data = Long.parseLong(hex,16);
                //定义value
                Double value = null;
                //计算value值
                //如果传感器类型为0A且地址为0001
                if("0A".equals(type) && "0001".equals(addr)){
                    if(data == 0){
                        value = 9.6;
                    }else {
                        //计算数据
                        value = (((float)data/8388608.0)-1.0)*2.5/64.0*4.0/2.1/5.0*1000000;
                    }
                }
                //如果传感器类型为06
                if("06".equals(type)){
                    //计算数据
                    if(new Long(4294967295L) == data){
                        value = 9.6;
                    }else {
                        value = data/1000.0;
                    }
                }
                //如果传感器器类型为10
                if("10".equals(type)){
                    value = new Double(data);
                }
                //生成插入时间
                Date insertTime = new Date();
                //将传感器原始数据封装为对象
                Sessor sessor = new Sessor(null, insertTime, ip, stringData);
                //将传感器计算数据封装为对象
                SessorData sessorData = new SessorData(null, insertTime, type, addr, value, ip);

                //获取mapper接口代理类实现对象
                SessorMapper sessorMapper = BeanUtil.getBean(SessorMapper.class);
                SessorDataMapper sessorDataMapper = BeanUtil.getBean(SessorDataMapper.class);
                //插入数据库中
                sessorMapper.insertSessor(sessor);
                LOGGER.info("插入传感器原始数据：{}", sessor);
                //只有以前结果才插入传感器计算数据
                if((("0A".equals(type) && "0001".equals(addr))) || "06".equals(type) || "10".equals(type)){
                    sessorDataMapper.insertSessorData(sessorData);
                    LOGGER.info("插入传感器计算数据：{}", sessorData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("系统异常"+e.getMessage());
        } finally {
            try {
                //关闭资源
              /*  if (bw != null)
                    bw.close();
                if (bis != null)
                    bis.close();*/
                if (socket != null)
                    socket.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("关闭资源异常"+e.getMessage());
            }
        }
    }
}