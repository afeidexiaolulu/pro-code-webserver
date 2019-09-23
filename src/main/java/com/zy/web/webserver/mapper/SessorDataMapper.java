package com.zy.web.webserver.mapper;

import com.zy.web.webserver.bean.SessorData;
import org.springframework.stereotype.Component;

/**
 * 插入传感器数据的接口
 */
@Component
public interface SessorDataMapper {

    Integer insertSessorData(SessorData sessorData);
}
