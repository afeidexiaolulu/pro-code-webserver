package com.zy.web.webserver.mapper;

import com.zy.web.webserver.bean.Sessor;
import org.springframework.stereotype.Component;

/**
 * 插入传感器原始数据
 */

@Component
public interface SessorMapper {

    Integer insertSessor(Sessor sessor);
}
